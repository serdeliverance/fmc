package com.soundcloud.maze.application.eventbus

import com.soundcloud.maze.domain.Event
import io.circe.{ Decoder, Encoder }

object CirceImplicits {

  // TODO implement
  implicit val eventEncoder: Encoder[Event] = ???
  implicit val eventDecoder: Decoder[Event] = ???
}
