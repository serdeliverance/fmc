package com.soundcloud.maze.adapters.out.persistence

import com.soundcloud.maze.domain.entities.Session

object SessionOps {

  implicit class SessionToMapConverter(session: Session) {
    def toMap: Map[String, String] =
      // TODO extract fields to constants
      Map("userId" -> session.userId.toString, "address.ip" -> session.address.ip, "address.port" -> session.address.port.toString)
  }
}
