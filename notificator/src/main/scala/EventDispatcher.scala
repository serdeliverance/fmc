import com.soundcloud.maze.domain.entities.Event
import com.soundcloud.maze.domain.entities.Event._

import scala.concurrent.Future

object EventDispatcher {

  // TODO
  def dispatch(event: Event): Future[Unit] = event match {
    case Follow(seqNo, fromUserId, toUserId)     => ???
    case Unfollow(seqNo, fromUserId, toUserId)   => ???
    case Broadcast(seqNo)                        => ???
    case PrivateMsg(seqNo, fromUserId, toUserId) => ???
    case StatusUpdate(seqNo, fromUserId)         => ???
  }
}
