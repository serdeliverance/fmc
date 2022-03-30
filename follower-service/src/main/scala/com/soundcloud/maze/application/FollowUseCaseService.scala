package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.FollowUseCase
import com.soundcloud.maze.application.FollowService.Follow
import com.soundcloud.maze.application.ports.out.{ FollowerRepository, NotificationService, SessionRepository }

import cats.effect.IO

class FollowUseCaseService(
    followService: FollowService
) extends FollowUseCase {

  override def follow(followerId: Long, followedUserId: Long): IO[Unit] =
    followService.followUpdate(followerId, followedUserId, Follow)
}
