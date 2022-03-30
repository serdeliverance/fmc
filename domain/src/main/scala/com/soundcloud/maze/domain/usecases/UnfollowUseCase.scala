package com.soundcloud.maze.domain.usecases

import cats.effect.IO

trait UnfollowUseCase {
  def unfollow(followerId: Long, followedId: Long): IO[Unit]
}
