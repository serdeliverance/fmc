package com.soundcloud.maze.domain.usecases

import cats.effect.IO

trait LogoutUseCase {
  def logout(userId: Long): IO[Unit]
}
