import Session.Notify
import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.Tcp.IncomingConnection
import com.soundcloud.maze.domain.entities.Event

object SessionManager {

  sealed trait Command
  case class Connect(userId: Long, connection: IncomingConnection) extends Command
  case class Broadcast(seqNo: Long)                                extends Command

  def apply(sessions: List[ActorRef[Notify]]): Behavior[Command] = sessionManager(List.empty)

  private def sessionManager(sessions: List[ActorRef[Notify]]): Behavior[Command] =
    Behaviors.setup { context =>
      Behaviors.receive {
        case Connect(userId, connection) =>
          context.log.info(s"Creating new session for user: ${userId}")
          val session = context.spawn(Session(userId, connection), s"Session-User-$userId")
          sessionManager(session :: sessions)
        case Broadcast(seqNo) =>
          sessions.foreach(session => session ! Notify(Event.Broadcast(seqNo)))
          Behaviors.same
      }
    }
}
