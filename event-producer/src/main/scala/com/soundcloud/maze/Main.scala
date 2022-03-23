package com.soundcloud.maze

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{ Flow, Framing, Tcp }
import akka.util.ByteString
import com.soundcloud.maze.adapter.in.tcp.EventParser
import com.soundcloud.maze.application.EventHandler
import com.soundcloud.maze.application.eventbus.KafkaEventBus
import com.soundcloud.maze.commons.kafka.kafkaProducer
import com.typesafe.config.ConfigFactory

import java.nio.file.FileSystems

/*
object Main {

  private val EventPort  = 9090
  private val ClientPort = 9099

  private var lastSeqNo = 0L

  def main(args: Array[String]): Unit = {

    val clientPool = new TrieMap[Long, Socket]

    val messagesBySeqNo = new mutable.HashMap[Long, List[String]]
    val followRegistry  = new mutable.HashMap[Long, Set[Long]]

    implicit val ec = ExecutionContext.global

    val eventsAsync = Future {

      println(s"Listening for events on $EventPort")
      val eventSocket = new ServerSocket(EventPort).accept()

      Try {
        val reader = new BufferedReader(new InputStreamReader(eventSocket.getInputStream()))

        Try {
          reader.lines().iterator().asScala.foreach { payload =>
            println(s"Message received: $payload")
            val message = payload.split("\\|").toList

            messagesBySeqNo += message(0).toLong -> message

            while (messagesBySeqNo.get(lastSeqNo + 1L).isDefined) {
              val nextMessage = messagesBySeqNo.remove(lastSeqNo + 1).get

              messagesBySeqNo -= lastSeqNo + 1L

              val nextPayload = nextMessage.mkString("|")
              val seqNo       = nextMessage(0).toLong
              val kind        = nextMessage(1)

              kind match {
                case "F" =>
                  val fromUserId   = nextMessage(2).toLong
                  val toUserId     = nextMessage(3).toLong
                  val followers    = followRegistry.getOrElse(toUserId, Set.empty)
                  val newFollowers = followers + fromUserId

                  followRegistry.put(toUserId, newFollowers)

                  clientPool.get(toUserId).foreach { socket =>
                    val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                    writer.write(s"$nextPayload\n")
                    writer.flush()
                  }

                case "U" =>
                  val fromUserId   = nextMessage(2).toLong
                  val toUserId     = nextMessage(3).toLong
                  val followers    = followRegistry.getOrElse(toUserId, Set.empty)
                  val newFollowers = followers - fromUserId

                  followRegistry.put(toUserId, newFollowers)

                case "P" =>
                  val toUserId = nextMessage(3).toLong

                  clientPool.get(toUserId).foreach { socket =>
                    val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                    writer.write(s"$nextPayload\n")
                    writer.flush()
                  }

                case "B" =>
                  clientPool.values.foreach { socket =>
                    val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                    writer.write(s"$nextPayload\n")
                    writer.flush()
                  }

                case "S" =>
                  val fromUserId = nextMessage(2).toLong
                  val followers  = followRegistry.getOrElse(fromUserId, Set.empty)

                  followers.foreach { follower =>
                    clientPool.get(follower).foreach { socket =>
                      val writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                      writer.write(s"$nextPayload\n")
                      writer.flush()
                    }
                  }
              }

              lastSeqNo = seqNo
            }
          }
        }
        if (reader != null) reader.close()
      }
      if (eventSocket != null) eventSocket.close()
    }

    val clientsAsync = Future {

      println(s"Listening for client requests on $ClientPort")
      val serverSocket      = new ServerSocket(ClientPort)
      var maybeClientSocket = Option(serverSocket.accept())

      while (maybeClientSocket.nonEmpty) {
        maybeClientSocket.foreach { clientSocket =>
          val bufferedSource = Source.fromInputStream(clientSocket.getInputStream())
          val userId         = bufferedSource.bufferedReader().readLine()

          if (userId != null) {
            clientPool.put(userId.toLong, clientSocket)
            println(s"User connected: $userId (${clientPool.size} total)")
          }

          maybeClientSocket = Option(serverSocket.accept())
        }
      }
    }

    Await.result(Future.sequence(Seq(eventsAsync, clientsAsync)), Duration.Inf)
  }

} */

// TODO rename when removing previous Main version
object Main extends App {

  val config   = ConfigFactory.load()
  val producer = kafkaProducer(config)

  val topic = config.getString("topics.event")

  // TCP server
  val host = config.getString("host")
  val port = config.getInt("port")

  implicit val system = ActorSystem("actor-system")

  val eventParser  = new EventParser()
  val eventBus     = new KafkaEventBus(producer, topic)
  val eventHandler = new EventHandler(eventBus)

  val connections =
    Tcp().bind(host, port)

  val ackHandler = Flow[Any].map(_ => ByteString("ack"))

  val connectionHandler: Flow[ByteString, ByteString, NotUsed] =
    Flow[ByteString]
      .via(Framing.delimiter(ByteString(FileSystems.getDefault.getSeparator), 256, true))
      .map(_.utf8String.trim)
      .map(eventParser.parseEvent)
      .collect { case Right(event) =>
        event
      }
      .mapAsync(4) { event => eventHandler.handle(event) }
      .via(ackHandler)

  connections.runForeach { connection =>
    connection.handleWith(connectionHandler)
  }
}
