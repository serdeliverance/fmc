package com.soundcloud.maze.application.eventbus

import com.soundcloud.maze.common.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.Event

trait EventBusProducer {

  def publish(event: Event): ApplicationResult[Unit]
}
