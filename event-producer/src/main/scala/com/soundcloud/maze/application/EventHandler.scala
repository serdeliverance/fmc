package com.soundcloud.maze.application

import com.soundcloud.maze.application.eventbus.EventBusProducer
import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.Event

class EventHandler(eventBus: EventBusProducer) {

  def handle(event: Event): ApplicationResult[Unit] =
    eventBus.publish(event)
}
