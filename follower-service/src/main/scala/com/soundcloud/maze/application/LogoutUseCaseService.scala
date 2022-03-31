package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.LogoutUseCase
import cats.effect.IO

class LogoutUseCaseService extends LogoutUseCase {
  // TODO implement
  override def logout(userId: Long): IO[Unit] = ???
}
