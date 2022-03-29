package com.soundcloud.maze.domain.usecases

class FollowUseCase(
    sessionRepository: SessionRepository,
    followerRepository: FollowerRepository,
    notificationService: NotificationService
) extends FollowUseCaseService {

  def follow(followerId: Long, followedId: Long): IO[Unit] =
    followerRepository.isFollowedBy(followedId, followedId)
}
