package com.soundcloud.maze.application.eventbus

import com.soundcloud.maze.commons.globals.ApplicationError
import com.soundcloud.maze.commons.globals.ApplicationError.EventBusError
import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.commons.json.CirceImplicits.eventEncoder
import com.soundcloud.maze.domain.Event
import io.circe.syntax._
import org.apache.kafka.clients.producer.{ Callback, KafkaProducer, ProducerRecord, RecordMetadata }
import org.slf4j.{ Logger, LoggerFactory }

import scala.concurrent.Promise

class KafkaEventBus(producer: KafkaProducer[String, String], topic: String) extends EventBus {

  val logger = LoggerFactory.getLogger(getClass)

  override def publish(event: Event): ApplicationResult[Unit] = {
    val message = event.asJson.toString
    val p       = Promise[Either[ApplicationError, Unit]]()
    logger.info(s"Publishing event to kafka: $event")
    producer.send(
      new ProducerRecord[String, String](topic, "key", message), // TODO add another key
      new Callback {
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
          Option(exception)
            .map(ex => {
              logger.debug(s"Error publishing event: $event. Error: ${ex.getMessage}")
              p.success(Left(EventBusError(ex.getMessage)))
            })
            .getOrElse(p.success(Right(())))
        }
      }
    )
    p.future
  }
}
