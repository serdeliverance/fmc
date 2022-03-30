package com.soundcloud.maze.domain.entities

import java.util.UUID

case class Session(id: Option[UUID], userId: Long, address: Address)

case class Address(ip: String, port: Int)
