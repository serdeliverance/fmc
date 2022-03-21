package com.soundcloud.maze.domain

sealed trait Command

object Command {

  case class Follow(seqNo: Long, fromUserId: Long, toUserId: Long)     extends Command
  case class Unfollow(seqNo: Long, fromUserId: Long, toUserId: Long)   extends Command
  case class Broadcast(seqNo: Long)                                    extends Command
  case class PrivateMsg(seqNo: Long, fromUserId: Long, toUserId: Long) extends Command
  case class StatusUpdate(seqNo: Long, fromUserId: Long)               extends Command
}
