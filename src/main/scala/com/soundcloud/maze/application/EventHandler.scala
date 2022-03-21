package com.soundcloud.maze.application

import com.soundcloud.maze.common.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.Event

class EventHandler(eventBus: EventBus) {

  def handle(event: Event): ApplicationResult[Unit] =
    eventBus.publish(event)
}
