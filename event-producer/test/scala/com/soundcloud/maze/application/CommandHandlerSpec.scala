package com.soundcloud.maze.application

import .handle
import com.soundcloud.maze.domain.Event._
import com.soundcloud.maze.domain.Event._
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CommandHandlerSpec extends AnyWordSpec with Matchers {
  "handle Follow command" in {
    val result = handle(Follow(666, 60, 50))
    result mustBe Followed(666, 60, 50)
  }

  "handle Unfollow command" in {
    val result = handle(Unfollow(1, 12, 9))
    result mustBe Unfollowed(1, 12, 9)
  }

  "handle Broadcast command" in {
    val result = handle(Broadcast(542532))
    result mustBe BroadcastSent(542532)
  }

  "handle Private Message command" in {
    val result = handle(PrivateMsg(43, 32, 56))
    result mustBe PrivateMsgSent(43, 32, 56)
  }

  "handle Status Update command" in {
    val result = handle(StatusUpdate(634, 32))
    result mustBe StatusUpdated(634, 32)
  }
}
