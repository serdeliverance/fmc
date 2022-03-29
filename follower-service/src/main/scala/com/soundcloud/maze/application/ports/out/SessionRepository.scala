package com.soundcloud.maze.application.ports.out

import cats.effect.IO
import com.soundcloud.maze.domain.entities.Session

trait SessionRepository {

  def create(session: Session): IO[Unit]
  def getByUserId(userId: Long): IO[Option[Session]]
  def delete(sessionId: Long): IO[Unit]
}
