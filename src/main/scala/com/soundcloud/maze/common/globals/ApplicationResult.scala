package com.soundcloud.maze.common.globals

import scala.concurrent.Future

object ApplicationResult {

  type ApplicationResult[T] = Future[Either[ApplicationError, T]]

  def apply[T](t: T): ApplicationResult[T] = Future.successful(Right(t))

  def fail[T](error: ApplicationError): ApplicationResult[T] = Future.successful(Left(error))
}
