package com.soundcloud.maze.adapters.out.persistence

import com.soundcloud.maze.application.ports.out.SessionRepository
import com.soundcloud.maze.domain.entities.Session
import cats.effect.IO
import cats.effect.kernel.Resource
import dev.profunktor.redis4cats.RedisCommands

class SessionServiceImpl(redisCommand: Resource[IO, RedisCommands[IO, String, String]]) extends SessionRepository {
  // TODO inmemory session repository using CE data structures
  override def create(session: Session): IO[Unit] =
    redisCommand.use { redis =>
      for {
        // TODO autogenerate session ids
        _ <- redis.hSet("session-id", session.toMap)
      } yield ()
    }
  override def delete(sessionId: Long): IO[Unit]              = ???
  override def getByUserId(userId: Long): IO[Option[Session]] = ???
}
