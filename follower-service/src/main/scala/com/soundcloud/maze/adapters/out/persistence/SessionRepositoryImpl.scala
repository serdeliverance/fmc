package com.soundcloud.maze.adapters.out.persistence

import com.soundcloud.maze.application.ports.out.SessionRepository
import com.soundcloud.maze.domain.entities.Session
import cats.effect.IO
import cats.effect.kernel.Resource
import dev.profunktor.redis4cats.RedisCommands
import java.util.UUID

class SessionServiceImpl(redisCommand: Resource[IO, RedisCommands[IO, String, String]]) extends SessionRepository {
  // TODO inmemory session repository using CE data structures
  override def create(session: Session): IO[Session] =
    redisCommand.use { redis =>
      for {
        uuid <- IO.pure(UUID.randomUUID())
        _    <- redis.hSet(uuid, session.toMap)
      } yield (session.copy(id = Some(uuid)))
    }
  override def delete(sessionId: Long): IO[Unit]              = ???
  override def getByUserId(userId: Long): IO[Option[Session]] = ???
}
