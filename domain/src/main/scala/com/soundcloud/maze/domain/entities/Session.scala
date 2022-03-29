package com.soundcloud.maze.domain.entities

case class Session(id: Option[Long], userId: Long, connection: ConnectionData)

case class ConnectionData(ip: String, port: Int)
