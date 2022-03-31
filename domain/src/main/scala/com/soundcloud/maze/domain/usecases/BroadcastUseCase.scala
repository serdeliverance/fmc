package com.soundcloud.maze.domain.usecases

import cats.effect.IO

trait BroadcastUseCase {
  def broadcast(seqNo: Long): IO[Unit]
}
