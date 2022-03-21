package com.soundcloud.maze.application

import com.soundcloud.maze.common.ApplicationError.EventBusError
import com.soundcloud.maze.common.ApplicationResult
import com.soundcloud.maze.domain.Event.Followed
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.mockito.MockitoSugar.mock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EventHandlerSpec extends AnyWordSpec with Matchers with ScalaFutures {

  private val eventBus     = mock[EventBus]
  private val eventHandler = new EventHandler(eventBus)

  "publish event successfully" in {
    when(eventBus.publish(any())).thenReturn(ApplicationResult(()))
    val result = eventHandler.handle(Followed(1000, 1, 3))

    whenReady(result) { r =>
      r.isRight mustBe true
    }
  }

  "falling publishing event" in {
    when(eventBus.publish(any())).thenReturn(ApplicationResult.fail(EventBusError("error while publishing")))
    val result = eventHandler.handle(Followed(1000, 1, 3))

    whenReady(result) { r =>
      r.isLeft mustBe true
    }
  }
}
