package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.FollowUseCase
import com.soundcloud.maze.application.ports.out.{ FollowerRepository, NotificationService, SessionRepository }

import cats.effect.IO

class FollowUseCaseService(
    followerRepository: FollowerRepository,
    sessionRepository: SessionRepository,
    notificationService: NotificationService
) extends FollowUseCase {

  // TODO change firm parameter names (follower vs followed) to be more readable and avoid confusion
  override def follow(seqNo: Long, followerId: Long, followedId: Long): IO[Unit] =
    followerRepository
      .isFollowedBy(followedId, followerId)
      .flatMap { isFollowed =>
        if (isFollowed) IO.pure(())
        else doFollow(followerId, followedId)
      }

  // TODO add loggin
  private def doFollow(followerId: Long, followedId: Long): IO[Unit] =
    for {
      _            <- followerRepository.addFollower(followedId, followerId)
      maybeSession <- sessionRepository.getByUserId(followedId)
      _ <- maybeSession match {
        case Some(session) => notificationService.notify(session.address, "") // TODO define message (maybe in a method)
        case None          => IO.pure(())
      }
    } yield ()
}
