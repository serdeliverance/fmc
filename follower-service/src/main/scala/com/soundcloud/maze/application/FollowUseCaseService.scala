package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.FollowUseCase
import com.soundcloud.maze.application.ports.out.{ FollowerRepository, NotificationService, SessionRepository }

import cats.effect.IO

class FollowUseCaseService(
    followerRepository: FollowerRepository,
    sessionRepository: SessionRepository,
    notificationService: NotificationService
) extends FollowUseCase {

  override def follow(followerId: Long, followedUserId: Long): IO[Unit] =
    followerRepository
      .isFollowedBy(followedUserId, followerId)
      .flatMap { isFollowed =>
        if (isFollowed) IO.pure(())
        else doFollow(followerId, followedUserId)
      }

  // TODO add loggin
  private def doFollow(newFollower: Long, followedUserId: Long): IO[Unit] =
    for {
      _            <- followerRepository.addFollower(followedUserId, newFollower)
      maybeSession <- sessionRepository.getByUserId(followedUserId)
      _ <- maybeSession match {
        case Some(session) =>
          notificationService.notify(session.address, message(followedUserId))
        case None => IO.pure(())
      }
    } yield ()

  private def message(followerId: Long) = s"User ${followerId} started following you"
}
