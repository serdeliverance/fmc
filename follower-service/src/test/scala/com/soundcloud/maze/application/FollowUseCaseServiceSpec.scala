package com.soundcloud.maze.application

import munit.CatsEffectSuite
import org.mockito.MockitoSugar
import org.mockito.ArgumentMatchers.any
import cats.effect.IO

import com.soundcloud.maze.application.ports.out.{ FollowerRepository, NotificationService, SessionRepository }
import com.soundcloud.maze.domain.entities.Session
import com.soundcloud.maze.application.stubs.DomainStubs

class FollowUseCaseServiceSpec extends CatsEffectSuite with MockitoSugar with DomainStubs {

  private val followerRepository  = mock[FollowerRepository]
  private val sessionRepository   = mock[SessionRepository]
  private val notificationService = mock[NotificationService]

  private val subject = new FollowUseCaseService(followerRepository, sessionRepository, notificationService)

  test("follow a user") {
    // given
    val userToFollow = 1
    val followerId   = 2

    when(followerRepository.isFollowedBy(1, 2)).thenReturn(IO.pure(false))
    when(followerRepository.addFollower(1, 2)).thenReturn(IO.pure(()))
    when(sessionRepository.getByUserId(1)).thenReturn(IO.pure(Some(Session(Some(23), 1, anAddress))))
    when(notificationService.notify(any(), any())).thenReturn(IO.pure(()))
    // when

    val result = subject.follow(2, 1)

    // then
    assertIO(result, ())
  }

  test("following a user which has no active session not involves notification sending") {
    // TODO implement
  }

  // TODO add spy or something to check that notify is not called
  test("following a user that already follows produce no changes") {
    // given
    val userToFollow = 1
    val followerId   = 2

    when(followerRepository.isFollowedBy(2, 1)).thenReturn(IO.pure(true))
    // when

    val result = subject.follow(1, 2)

    // then
    assertIO(result, ())
  }
}
