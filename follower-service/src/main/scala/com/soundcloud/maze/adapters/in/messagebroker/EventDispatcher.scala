package com.soundcloud.maze.adapters.in.messagebroker

import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.entities.Event
import com.soundcloud.maze.domain.entities.Event._

class EventDispatcher {

  // TODO
  def dispatch(event: Event): ApplicationResult[Unit] = event match {
    case Follow(seqNo, fromUserId, toUserId)     => ???
    case Unfollow(seqNo, fromUserId, toUserId)   => ???
    case Broadcast(seqNo)                        => ???
    case PrivateMsg(seqNo, fromUserId, toUserId) => ???
    case StatusUpdate(seqNo, fromUserId)         => ???
  }
}
