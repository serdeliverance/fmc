import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.Tcp
import com.soundcloud.maze.domain.entities.Event

// TODO unit test
object Session {

  final case class GetSessionData(event: Event, replyTo: ActorRef[GetSessionData])

  final case class UsersToNotify(sessions: List[UserSession])

  // TODO move to another class
  case class UserSession(userId: Long, connection: Tcp.IncomingConnection)

  def apply(userId: Long, connection: Tcp.IncomingConnection): Behavior[GetSessionData] =
    Behaviors.receive { (context, message) =>
      context.log.info(s"Retrieving session data: userId = $userId, connection = $connection")

      Behaviors.same
    }
}
