package com.soundcloud.maze.commons.globals

import scala.concurrent.Future

object ApplicationResult {

  type ApplicationResult[T] = Future[EitherResult[T]]

  type EitherResult[T] = Either[ApplicationError, T]

  def apply[T](t: T): ApplicationResult[T] = Future.successful(Right(t))

  def fail[T](error: ApplicationError): ApplicationResult[T] = Future.successful(Left(error))
}
