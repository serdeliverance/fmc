package com.soundcloud.maze.domain.usecases

import com.soundcloud.maze.domain.entities.{ User, UserRepository }

import scala.concurrent.Future

class FollowUseCase(userRepository: UserRepository) {

  def follow(user: User, followed: User): Future[Unit] =
    userRepository.
}
