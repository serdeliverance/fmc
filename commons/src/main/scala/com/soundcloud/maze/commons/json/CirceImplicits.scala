package com.soundcloud.maze.commons.json

import cats.syntax.functor._
import com.soundcloud.maze.domain.Event
import com.soundcloud.maze.domain.Event._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{ deriveConfiguredDecoder, deriveConfiguredEncoder }
import io.circe.syntax._
import io.circe.{ Decoder, Encoder, Printer }

object CirceImplicits {

  implicit val customPrinter: Printer      = Printer.noSpaces.copy(dropNullValues = true)
  implicit val customConfig: Configuration = Configuration.default.withKebabCaseMemberNames

  implicit val followEncoder: Encoder[Follow] = deriveConfiguredEncoder
  implicit val followDecoder: Decoder[Follow] = deriveConfiguredDecoder

  implicit val unfollowEncoder: Encoder[Unfollow] = deriveConfiguredEncoder
  implicit val unfollowDecoder: Decoder[Unfollow] = deriveConfiguredDecoder

  implicit val broadcastEncoder: Encoder[Broadcast] = deriveConfiguredEncoder
  implicit val broadcastDecoder: Decoder[Broadcast] = deriveConfiguredDecoder

  implicit val privateMessageEncoder: Encoder[PrivateMsg] = deriveConfiguredEncoder
  implicit val privateMessageDecoder: Decoder[PrivateMsg] = deriveConfiguredDecoder

  implicit val statusUpdateEncoder: Encoder[StatusUpdate] = deriveConfiguredEncoder
  implicit val statusUpdateDecoder: Decoder[StatusUpdate] = deriveConfiguredDecoder

  implicit val eventEncoder: Encoder[Event] = Encoder.instance {
    case followed @ Follow(_, _, _)           => followed.asJson
    case unfollowed @ Unfollow(_, _, _)       => unfollowed.asJson
    case broadcast @ Broadcast(_)             => broadcast.asJson
    case privateMsgSent @ PrivateMsg(_, _, _) => privateMsgSent.asJson
    case statusUpdated @ StatusUpdate(_, _)   => statusUpdated.asJson
  }
  implicit val eventDecoder: Decoder[Event] =
    List[Decoder[Event]](
      Decoder[Follow].widen,
      Decoder[Unfollow].widen,
      Decoder[Broadcast].widen,
      Decoder[PrivateMsg].widen,
      Decoder[StatusUpdate].widen
    ).reduceLeft(_ or _)
}
