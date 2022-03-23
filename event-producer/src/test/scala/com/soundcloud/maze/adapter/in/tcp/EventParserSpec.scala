package com.soundcloud.maze.adapter.in.tcp

import com.soundcloud.maze.commons.globals.ApplicationError.ParsingError
import com.soundcloud.maze.domain.Event._
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EventParserSpec extends AnyWordSpec with Matchers {

  private val subject = new EventParser()

  "parse Follow payload" in {
    val result = subject.parseEvent("666|F|60|50")
    result mustBe Right(Follow(666, 60, 50))
  }

  "parse Unfollow payload" in {
    val result = subject.parseEvent("1|U|12|9")
    result mustBe Right(Unfollow(1, 12, 9))
  }

  "parse Broadcast payload" in {
    val result = subject.parseEvent("542532|B")
    result mustBe Right(Broadcast(542532))
  }

  "parse Private Message payload" in {
    val result = subject.parseEvent("43|P|32|56")
    result mustBe Right(PrivateMsg(43, 32, 56))
  }

  "parse Status Update payload" in {
    val result = subject.parseEvent("634|S|32")
    result mustBe Right(StatusUpdate(634, 32))
  }

  "fail when parsing invalid input" in {
    val result = subject.parseEvent("Hallo !")
    result mustBe Left(ParsingError())
  }
}
