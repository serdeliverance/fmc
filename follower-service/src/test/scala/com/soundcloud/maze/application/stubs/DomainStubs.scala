package com.soundcloud.maze.application.stubs

import com.soundcloud.maze.domain.entities.Address

trait DomainStubs {
  val anAddress = Address("127.0.0.1", 9092)
}
