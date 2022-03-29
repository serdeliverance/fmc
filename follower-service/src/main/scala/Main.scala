object Main extends App {

  /*
  val config   = ConfigFactory.load()
  val producer = kafkaProducer(config)

  val topic = config.getString("topics.event")

  // TCP server
  val host = config.getString("host")
  val port = config.getInt("port")

  implicit val system = ActorSystem("actor-system")

  // TODO extract this stuff on commons to avoid repetition of code
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
   */
}
