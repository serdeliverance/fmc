package com.soundcloud.maze.application.eventbus

import com.soundcloud.maze.common.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.Event
import io.circe.syntax._
import CirceImplicits._
import com.soundcloud.maze.common.globals.ApplicationError
import com.soundcloud.maze.common.globals.ApplicationError.EventBusError
import org.apache.kafka.clients.producer.{ Callback, KafkaProducer, ProducerRecord, RecordMetadata }

import scala.concurrent.Promise

class KafkaEventBusProducer(producer: KafkaProducer[String, String], topic: String) extends EventBusProducer {

  override def publish(event: Event): ApplicationResult[Unit] = {
    val message = event.asJson.toString
    val p       = Promise[Either[ApplicationError, Unit]]()
    producer.send(
      // TODO add a more specific key
      new ProducerRecord[String, String](topic, "key", message),
      new Callback {
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
          Option(exception)
            .map(ex => p.success(Left(EventBusError(ex.getMessage))))
            .getOrElse(p.success(Right(())))
        }
      }
    )
    p.future
  }
}
