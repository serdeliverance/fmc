package com.soundcloud.maze.application.ports.out

import com.soundcloud.maze.domain.entities.Event
import cats.effect.IO
import com.soundcloud.maze.domain.entities.Address

trait NotificationService {

  def notify(address: Address, message: String): IO[Unit]
}
