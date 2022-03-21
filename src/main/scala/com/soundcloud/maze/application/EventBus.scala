package com.soundcloud.maze.application

import com.soundcloud.maze.common.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.Event

trait EventBus {

  def publish(event: Event): ApplicationResult[Unit]
}
