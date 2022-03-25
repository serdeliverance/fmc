package com.soundcloud.maze.domain.entities

case class User(followers: List[User], follows: List[User])
