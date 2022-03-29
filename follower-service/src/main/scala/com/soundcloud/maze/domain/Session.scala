package com.soundcloud.maze.domain

// TODO change connection data for fs2 representative
case class Session(id: Option[Long], userId: Long, connectionData: String)
