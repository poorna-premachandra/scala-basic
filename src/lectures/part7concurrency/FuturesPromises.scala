package lectures.part7concurrency

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturesPromises extends App {

  def calculate: Int = { //runs on ANOTHER thread
    Thread.sleep(2000)
    42
  } //(global)  implicit val is passed by the compiler

  val aFuture = Future {
    calculate
  }

  //we can get the value from the future object as follows..
  println(aFuture.value) //returns Option[Try[Int]]

  //but the value may still not calculated. therefore we are using 'onComplete' to get it.
  println("waiting on the future")
  //  aFuture.onComplete(t => t match {
  //    case Success(futureVal) => println(s"calculated value is $futureVal")
  //    case Failure(e) => println(s"I have failed with $e")
  //  })
  aFuture.onComplete { //syntactic sugar for pattern matching
    case Success(futureVal) => println(s"calculated value is $futureVal")
    case Failure(e) => println(s"I have failed with $e")
  }

  Thread.sleep(3000) // main thread should finish last in order to give a chance for other threads to run
}
