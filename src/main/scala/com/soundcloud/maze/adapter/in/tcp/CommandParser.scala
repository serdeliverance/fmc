package com.soundcloud.maze.adapter.in.tcp

import com.soundcloud.maze.domain.Command
import com.soundcloud.maze.domain.Command._

object CommandParser {

  def parse(payload: String): Command = {
    val payloadSplitted   = payload.split("\\|")
    val commandIdentifier = payloadSplitted(1)

    val fragments = payloadSplitted.zipWithIndex.collect {
      case (fragment, index) if index != 1 => fragment.toInt
    }

    commandIdentifier match {
      case "F" => Follow(fragments(0), fragments(1), fragments(2))
      case "U" => Unfollow(fragments(0), fragments(1), fragments(2))
      case "B" => Broadcast(fragments(0))
      case "P" => PrivateMsg(fragments(0), fragments(1), fragments(2))
      case "S" => StatusUpdate(fragments(0), fragments(1))
    }
  }
}
