package com.soundcloud.maze.application

import com.soundcloud.maze.domain.Command._
import com.soundcloud.maze.domain.Event._
import com.soundcloud.maze.domain.{ Command, Event }

object CommandHandler {

  def handle(command: Command): Event =
    command match {
      case Follow(seqNo, fromUserId, toUserId)     => Followed(seqNo, fromUserId, toUserId)
      case Unfollow(seqNo, fromUserId, toUserId)   => Unfollowed(seqNo, fromUserId, toUserId)
      case Broadcast(seqNo)                        => BroadcastSent(seqNo)
      case PrivateMsg(seqNo, fromUserId, toUserId) => PrivateMsgSent(seqNo, fromUserId, toUserId)
      case StatusUpdate(seqNo, fromUserId)         => StatusUpdated(seqNo, fromUserId)
    }
}
