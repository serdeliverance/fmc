package com.soundcloud.maze.domain

sealed trait Command

object Command:

  // TODO: pasar todo a Long
  case class Follow(seqNo: Int, fromUserId: Int, toUserId: Int)     extends Command
  case class Unfollow(seqNo: Int, fromUserId: Int, toUserId: Int)   extends Command
  case class Broadcast(seqNo: Int)                                  extends Command
  case class PrivateMsg(seqNo: Int, fromUserId: Int, toUserId: Int) extends Command
  case class StatusUpdate(seqNo: Int, fromUserId: Int)              extends Command
