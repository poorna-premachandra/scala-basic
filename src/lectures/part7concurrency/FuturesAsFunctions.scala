package lectures.part7concurrency

import scala.concurrent.Future
import scala.util.Random

import scala.concurrent.ExecutionContext.Implicits.global

object FuturesAsFunctions extends App {

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) = println(s"${this.name} poking ${anotherProfile.name}")
  }

  object SocialNetwork {
    //database
    val names = Map(
      "fb.id.0" -> "Dummy",
      "fb.id.1" -> "Mark",
      "fb.id.2" -> "Bill"
    )

    val friends = Map(
      "fb.id.1" -> "fb.id.2"
    )

    val random = new Random()

    //APIs
    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val friendId = friends(profile.id)
      Profile(friendId, names(friendId))
    }
  }


  //functional compositions of futures
  val mark = SocialNetwork.fetchProfile("fb.id.1")

  //map, flatmap, filter

  //map - transform future of a given type to a future of a different type
  val nameOnTheWall = mark.map(profile => profile.name) //if the original future fails with an exception, mapped future will also fails with same exception
  //flatmap - obtain a future of same type
  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchFriend(profile))
  //filter - filter a future with a predicate and obtain a future of same type
  val marksBestFriendUsingFilter = marksBestFriend.filter(profile => profile.name.startsWith("B"))

  //for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1")
    bill <- SocialNetwork.fetchFriend(mark)
  } mark.poke(bill)


  Thread.sleep(1000)

  //fallbacks
  //returns a dummy/default profile if original call fails
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0", "Dummy Profile")
  }

  //fetch another profile if original call fails
  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0")
  }
  
  //fetch another profile if original call fails
  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0"))
}
