package com.soundcloud.maze.adapters.in.messagebroker

import fs2.kafka.ConsumerSettings
import fs2.kafka.KafkaConsumer
import cats.effect.IO

object BrokerConsumer {

  type RecordHandler[T] = String => IO[T]

  def startConsumer(handler: RecordHandler[Unit], settings: ConsumerSettings[IO, String, String]) =
    KafkaConsumer.stream(settings)
}
