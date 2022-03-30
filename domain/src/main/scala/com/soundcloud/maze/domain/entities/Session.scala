package com.soundcloud.maze.domain.entities

case class Session(id: Option[Long], userId: Long, address: Address)

case class Address(ip: String, port: Int)
