package com.soundcloud.maze.adapters.out.persistence

import com.soundcloud.maze.application.ports.out.SessionRepository
import com.soundcloud.maze.domain.entities.Session
import cats.effect.IO

class SessionServiceImpl extends SessionRepository {
  // TODO inmemory session repository using CE data structures
  override def create(session: Session): IO[Unit]             = ???
  override def delete(sessionId: Long): IO[Unit]              = ???
  override def getByUserId(userId: Long): IO[Option[Session]] = ???
}
