package com.soundcloud.maze.domain

sealed trait Event

object Event {

  case class Follow(seqNo: Long, fromUserId: Long, toUserId: Long)     extends Event
  case class Unfollow(seqNo: Long, fromUserId: Long, toUserId: Long)   extends Event
  case class Broadcast(seqNo: Long)                                    extends Event
  case class PrivateMsg(seqNo: Long, fromUserId: Long, toUserId: Long) extends Event
  case class StatusUpdate(seqNo: Long, fromUserId: Long)               extends Event
}
