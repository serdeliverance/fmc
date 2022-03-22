package com.soundcloud.maze

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Attributes
import akka.stream.scaladsl.{ Flow, Framing, Tcp }
import akka.util.ByteString
import com.soundcloud.maze.adapter.in.tcp.EventParser.parseEvent
import com.soundcloud.maze.application.EventHandler
import com.soundcloud.maze.application.eventbus.KafkaEventBusProducer
import com.soundcloud.maze.commons.kafka.kafkaProducer
import com.typesafe.config.ConfigFactory

import java.io._
import java.net.{ ServerSocket, Socket }
import java.nio.file.FileSystems
import scala.collection.JavaConverters._
import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.io.Source
import scala.util.Try

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

  val eventBus     = new KafkaEventBusProducer(producer, topic)
  val eventHandler = new EventHandler(eventBus)

  val eventChannelConnections =
    Tcp().bind(host, port)

  val ackHandler = Flow[Any].map(_ => ByteString("ack"))

  val eventConnectionHandler: Flow[ByteString, ByteString, NotUsed] =
    Flow[ByteString]
      .via(Framing.delimiter(ByteString(FileSystems.getDefault.getSeparator), 256, true))
      .map(_.utf8String)
      .log(name = "ut8String")                                                    // TODO fix
      .addAttributes(Attributes.logLevels(onElement = Attributes.LogLevels.Info)) // TODO remove
      .map(parseEvent)
      .mapAsync(4) { event => eventHandler.handle(event) }
      // TODO add error handling and improve logging
      .via(ackHandler)

  eventChannelConnections.runForeach { connection =>
    connection.handleWith(eventConnectionHandler)
  }
}
