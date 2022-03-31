package com.soundcloud.maze.application.ports.out

import cats.effect.IO
import com.soundcloud.maze.domain.entities.Session
import java.util.UUID

trait SessionRepository {

  def getAll(): IO[List[Session]]
  def create(session: Session): IO[Session]
  def getByUserId(userId: Long): IO[Option[Session]]
  def delete(sessionId: UUID): IO[Unit]
}
