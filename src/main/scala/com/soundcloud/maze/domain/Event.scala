package com.soundcloud.maze.domain

sealed trait Event

object Event {
  case class Followed(seqNo: Long, fromUserId: Long, toUserId: Long)       extends Event
  case class Unfollowed(seqNo: Long, fromUserId: Long, toUserId: Long)     extends Event
  case class BroadcastSent(seqNo: Long)                                    extends Event
  case class PrivateMsgSent(seqNo: Long, fromUserId: Long, toUserId: Long) extends Event
  case class StatusUpdated(seqNo: Long, fromUserId: Long)                  extends Event
}
