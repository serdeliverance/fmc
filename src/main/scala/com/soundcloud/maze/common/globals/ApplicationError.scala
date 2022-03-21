package com.soundcloud.maze.common.globals

sealed trait ApplicationError

object ApplicationError {
  case class DatabaseError(message: String) extends ApplicationError
  case class EventBusError(message: String) extends ApplicationError
}
