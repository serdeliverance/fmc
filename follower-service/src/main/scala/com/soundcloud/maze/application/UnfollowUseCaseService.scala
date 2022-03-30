package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.UnfollowUseCase
import com.soundcloud.maze.application.FollowService.Unfollow
import cats.effect.IO

class UnfollowUseCaseService(followService: FollowService) extends UnfollowUseCase {
  override def unfollow(followerId: Long, followedId: Long): IO[Unit] =
    followService.followUpdate(followerId, followedId, Unfollow)
}
