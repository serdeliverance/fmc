package com.soundcloud.maze.domain

sealed trait Event

object Event:
  // TODO pasar todo a Long
  case class Followed(seqNo: Int, fromUserId: Int, toUserId: Int)       extends Event
  case class Unfollowed(seqNo: Int, fromUserId: Int, toUserId: Int)     extends Event
  case class BroadcastSent(seqNo: Int)                                  extends Event
  case class PrivateMsgSent(seqNo: Int, fromUserId: Int, toUserId: Int) extends Event
  case class StatusUpdated(seqNo: Int, fromUserId: Int)                 extends Event
