package com.soundcloud.maze.application.ports.out

import cats.effect.IO

trait FollowerRepository {
  def isFollowedBy(followedId: Long, followerId: Long): IO[Boolean]

  def addFollower(followedId: Long, followerId: Long): IO[Unit]
}
