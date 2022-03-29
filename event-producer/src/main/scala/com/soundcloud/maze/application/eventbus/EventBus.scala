package com.soundcloud.maze.application.eventbus

import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.entities.Event

trait EventBus {

  def publish(event: Event): ApplicationResult[Unit]
}
