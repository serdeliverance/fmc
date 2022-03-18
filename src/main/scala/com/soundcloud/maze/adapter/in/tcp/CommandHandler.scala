package com.soundcloud.maze.adapter.in.tcp

import com.soundcloud.maze.domain.Command
import com.soundcloud.maze.domain.Command.*
import com.soundcloud.maze.domain.Event
import com.soundcloud.maze.domain.Event.*

object CommandHandler:

  def handle(command: Command): Event =
    command match
      case Follow(seqNo, fromUserId, toUserId)     => Followed(seqNo, fromUserId, toUserId)
      case Unfollow(seqNo, fromUserId, toUserId)   => Unfollowed(seqNo, fromUserId, toUserId)
      case Broadcast(seqNo)                        => BroadcastSent(seqNo)
      case PrivateMsg(seqNo, fromUserId, toUserId) => PrivateMsgSent(seqNo, fromUserId, toUserId)
      case StatusUpdate(seqNo, fromUserId)         => StatusUpdated(seqNo, fromUserId)
