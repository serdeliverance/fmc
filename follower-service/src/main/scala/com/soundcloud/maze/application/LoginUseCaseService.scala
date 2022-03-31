package com.soundcloud.maze.application

import com.soundcloud.maze.domain.usecases.LoginUseCase
import cats.effect.IO

class LoginUseCaseService extends LoginUseCase {

  // TODO implement
  override def login(userId: Long): IO[Unit] = ???
}
