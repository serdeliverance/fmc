package com.soundcloud.maze.adapters.out.persistence

import com.soundcloud.maze.application.ports.out.SessionRepository
import com.soundcloud.maze.adapters.out.persistence.SessionOps.SessionToMapConverter
import com.soundcloud.maze.domain.entities.Session
import cats.effect.IO
import cats.effect.kernel.Resource
import dev.profunktor.redis4cats.RedisCommands
import java.util.UUID

class SessionServiceImpl(redisCommand: Resource[IO, RedisCommands[IO, String, String]]) extends SessionRepository {

  override def getAll(): IO[List[Session]] = ???

  override def create(session: Session): IO[Session] =
    redisCommand.use { redis =>
      for {
        _ <- redis.hSet(session.id, session.toMap)
      } yield (session)
    }

  override def getByUserId(userId: Long): IO[Option[Session]] = ???

  override def delete(sessionId: UUID): IO[Unit] =
    redisCommand
      .use { redis =>
        redis.del(sessionId.toString)
      }
      .as(())
}
