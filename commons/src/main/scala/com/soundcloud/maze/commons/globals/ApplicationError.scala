package com.soundcloud.maze.commons.globals

sealed trait ApplicationError

object ApplicationError {
  case class DatabaseError(message: String)                  extends ApplicationError
  case class ParsingError(message: String = "parsing-error") extends ApplicationError
  case class EventBusError(message: String)                  extends ApplicationError
}
