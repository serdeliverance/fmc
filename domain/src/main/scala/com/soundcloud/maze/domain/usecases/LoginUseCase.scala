package com.soundcloud.maze.domain.usecases

import cats.effect.IO

trait LoginUseCase {
  def login(userId: Long): IO[Unit]
}
