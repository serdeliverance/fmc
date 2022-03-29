package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.FollowUseCase
import cats.effect.IO

class FollowUseCaseService extends FollowUseCase {
  // TODO
  override def follow(followerId: Long, followedId: Long): IO[Unit] = ???
}
