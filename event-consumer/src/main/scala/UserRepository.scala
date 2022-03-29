import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.entities.User

trait UserRepository {

  def getFollowersOf(userId: Long): ApplicationResult[List[User]]

  def unfollow(currentFollower: User, userFollowed: User): ApplicationResult[Unit]

  def follow(newFollower: User, userFollowed: User): ApplicationResult[Unit]
}
