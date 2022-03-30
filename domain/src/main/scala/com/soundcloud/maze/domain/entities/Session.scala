package com.soundcloud.maze.domain.entities

import java.util.UUID
import Session._

case class Session private (id: SessionId, userId: Long, address: Address)

case class Address(ip: String, port: Int)

object Session {

  type SessionId = String

  def apply(userId: Long, address: Address): Session =
    Session(s"user-$userId", userId, address)
}
