import Session.GetSessionData
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ ActorRef, Behavior }
import akka.stream.scaladsl.Tcp.IncomingConnection
import com.soundcloud.maze.domain.entities.Event.Broadcast

object SessionManager {

  sealed trait Command
  case class Connect(userId: Long, connection: IncomingConnection) extends Command
  case class BroadcastMessage(seqNo: Long)                         extends Command

  def apply(sessions: List[ActorRef[GetSessionData]]): Behavior[Command] = sessionManager(List.empty)

  private def sessionManager(sessions: List[ActorRef[GetSessionData]]): Behavior[Command] =
    Behaviors.setup { context =>
      Behaviors.receive {
        case Connect(userId, connection) =>
          context.log.info(s"Creating new session for user: ${userId}")
          val session = context.spawn(Session(userId, connection), s"Session-User-$userId")
          sessionManager(session :: sessions)
        case BroadcastMessage(seqNo) =>
          sessions.foreach(session => session ! GetSessionData(Broadcast(seqNo)))
          Behaviors.same
      }
    }
}

trait SessionManagerService {
  // TODO facade of SessionManager actor
}
