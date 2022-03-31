package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.BroadcastUseCase
import cats.effect.IO
import cats.implicits._

import com.soundcloud.maze.application.ports.out.SessionRepository
import com.soundcloud.maze.application.ports.out.NotificationService

class BroadcastUseCaseService(sessionRepository: SessionRepository, notificationService: NotificationService) extends BroadcastUseCase {

  override def broadcast(seqNo: Long): IO[Unit] = {
    val message = s"$seqNo | Broadcast"
    for {
      sessions <- sessionRepository.getAll()
      _ <- sessions
        .map(session => notificationService.notify(session.address, message))
        .sequence
        .as(())
    } yield ()
  }
}
