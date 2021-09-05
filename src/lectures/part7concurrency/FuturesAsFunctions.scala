package lectures.part7concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Random, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

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


  //use of Await.result/Await.ready to block on a future
  //online banking app
  case class User(name: String)

  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "BOC"

    def fetchUser(name: String): Future[User] = Future {
      //simulate fetching from the DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, ampunt: Double): Future[Transaction] = Future {
      //simulate some process
      Thread.sleep(1000)
      Transaction(user.name, merchantName, ampunt, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch user from db - 1st future
      // create transaction - 2nd future
      // wait for transaction to finish

      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      //block on the transactionStatusFuture
      Await.result(transactionStatusFuture, 2.seconds) //2.seconds returns a duration object. (through implicit conversions -> pimp my library)
      //after 2 sec, it throws an exception with a timeout
    }
  }

  println(BankingApp.purchase("Sam", "item1", "Store", 3000))


  //PROMISES - manual manipulations of futures
  val promise = Promise[Int]() // promise is a "controller" over a future
  val future = promise.future

  //thread 1 - consumer
  future.onComplete {
    case Success(r) => println("[consumer] I've received " + r)
  }

  //thread 2 - producer
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    //"fulfilling" the promise
    promise.success(42) //or promise.failure(exception)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)
}