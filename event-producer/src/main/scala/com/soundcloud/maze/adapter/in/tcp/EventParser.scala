package com.soundcloud.maze.adapter.in.tcp

import com.soundcloud.maze.commons.globals.ApplicationError.ParsingError
import com.soundcloud.maze.commons.globals.ApplicationResult.EitherResult
import com.soundcloud.maze.domain.Event
import com.soundcloud.maze.domain.Event._
import org.slf4j.LoggerFactory

import scala.util.Try

class EventParser {

  val logger = LoggerFactory.getLogger(getClass)

  def parseEvent(payload: String): EitherResult[Event] = {
    Try {
      logger.info(s"Parsing payload: $payload")

      val payloadSplitted   = payload.split("\\|")
      val commandIdentifier = payloadSplitted(1)

      val fragments = payloadSplitted.zipWithIndex.collect {
        case (fragment, index) if index != 1 => fragment.toInt
      }

      commandIdentifier match {
        case "F" => Follow(fragments(0), fragments(1), fragments(2))
        case "U" => Unfollow(fragments(0), fragments(1), fragments(2))
        case "B" => Broadcast(fragments(0))
        case "P" => PrivateMsg(fragments(0), fragments(1), fragments(2))
        case "S" => StatusUpdate(fragments(0), fragments(1))
      }
    }.map(Right(_))
      .getOrElse(Left(ParsingError()))

  }
}
