package com.soundcloud.maze.adapter.in.tcp

import com.soundcloud.maze.adapter.in.tcp.EventParser.parseEvent
import com.soundcloud.maze.domain.Event._
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CommandParserSpec extends AnyWordSpec with Matchers {

  "parse Follow payload" in {
    val result = parseEvent("666|F|60|50")
    result mustBe Follow(666, 60, 50)
  }

  "parse Unfollow payload" in {
    val result = parseEvent("1|U|12|9")
    result mustBe Unfollow(1, 12, 9)
  }

  "parse Broadcast payload" in {
    val result = parseEvent("542532|B")
    result mustBe Broadcast(542532)
  }

  "parse Private Message payload" in {
    val result = parseEvent("43|P|32|56")
    result mustBe PrivateMsg(43, 32, 56)
  }

  "parse Status Update payload" in {
    val result = parseEvent("634|S|32")
    result mustBe StatusUpdate(634, 32)
  }
}
