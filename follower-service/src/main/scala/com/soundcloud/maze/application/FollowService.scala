package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.FollowUseCase
import com.soundcloud.maze.application.ports.out.{ FollowerRepository, NotificationService, SessionRepository }
import FollowService.Operation
import cats.effect.IO
import com.soundcloud.maze.application.FollowService.Follow
import com.soundcloud.maze.application.FollowService.Unfollow

class FollowService(
    followerRepository: FollowerRepository,
    sessionRepository: SessionRepository,
    notificationService: NotificationService
) {

  def followUpdate(fromUserId: Long, toUserId: Long, operation: Operation): IO[Unit] =
    followerRepository
      .isFollowedBy(toUserId, fromUserId)
      .flatMap { isFollowed =>
        if (isFollowed) IO.pure(())
        else doOperation(fromUserId, toUserId, operation)
      }

  private def doOperation(fromUserId: Long, toUserId: Long, operation: Operation): IO[Unit] =
    for {
      _            <- followerRepository.addFollower(toUserId, fromUserId)
      maybeSession <- sessionRepository.getByUserId(toUserId)
      _ <- maybeSession match {
        case Some(session) =>
          notificationService.notify(session.address, message(fromUserId, operation))
        case None => IO.pure(())
      }
    } yield ()

  private def message(followerId: Long, operation: Operation) =
    operation match {
      case Follow   => s"User ${followerId} started following you"
      case Unfollow => s"User ${followerId} stopped following you"
    }
}

object FollowService {

  sealed trait Operation
  case object Follow   extends Operation
  case object Unfollow extends Operation
}
