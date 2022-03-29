package com.soundcloud.maze.domain.usecases

import cats.effect.IO

trait FollowUseCase {
  def follow(followerId: Long, followedId: Long): IO[Unit]
}
