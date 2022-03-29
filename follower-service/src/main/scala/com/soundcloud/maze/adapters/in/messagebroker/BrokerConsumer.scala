package com.soundcloud.maze.adapters.in.messagebroker

import fs2.kafka.ConsumerSettings
import fs2.kafka.KafkaConsumer
import cats.effect.IO
import fs2.kafka.ConsumerRecord
import com.soundcloud.maze.domain.entities.Event
import com.soundcloud.maze.commons.json.Serdes._
import io.circe.parser.decode

object BrokerConsumer {

  type RecordHandler[R, T] = R => IO[T]

  def consumer(
      handler: RecordHandler[Event, Unit],
      topic: String,
      settings: ConsumerSettings[IO, String, String]
  ) =
    KafkaConsumer
      .stream(settings)
      .subscribeTo(topic)
      .records
      .map { commitable =>
        decode[Event](commitable.record.value)
      }
      .collect { case Right(event) =>
        event
      }
      .mapAsync(4) { event => handler(event) }
}
