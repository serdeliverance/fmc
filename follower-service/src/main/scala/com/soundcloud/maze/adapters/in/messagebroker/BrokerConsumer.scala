package com.soundcloud.maze.adapters.in.messagebroker

import fs2.kafka.ConsumerSettings
import fs2.kafka.KafkaConsumer
import cats.effect.IO
import fs2.kafka.ConsumerRecord
import com.soundcloud.maze.domain.entities.Event

object BrokerConsumer {

  type RecordHandler[R, T] = R => IO[T]

  type EventParser = String => Event

  def consumer(
      handler: RecordHandler[Event, Unit],
      eventParser: EventParser,
      topic: String,
      settings: ConsumerSettings[IO, String, String]
  ) =
    KafkaConsumer
      .stream(settings)
      .subscribeTo(topic)
      .records
      .mapAsync(4) { commitable =>
        val event = eventParser(commitable.record.value)
        handler(event)
      }
}
