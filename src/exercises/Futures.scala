package exercises

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Random, Try}

object Futures extends App {

  //EXERCISES
  //1. fulfill a future immediately with a value
  def immediateFuture[T](value: T): Future[T] = Future(value)

  //2.inSequence(fa, fb) ; fb should run after completing fa
//  val fa = Future {
//    Thread.sleep(2000)
//    println("fa completed")
//  }
//  val fb = Future {
//    Thread.sleep(500)
//    println("fb completed")
//  }

  def inSequence(f1: Future[Unit], f2: Future[Unit]): Unit = f1.flatMap(_ => f2) //this does not work?
//  inSequence(fa, fb)

  //3.first(fa, fb) - returns the output of future finishing first
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)

    promise.future
  }


  //4.last(fa, fb) - returns the output of future finishing last
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val bothPromise = Promise[A] //both futures will try to complete
    val lastPromise = Promise[A] //last future will complete
    val checkAndComplete = (result: Try[A]) =>
      if (!bothPromise.tryComplete(result))
        lastPromise.complete(result)
    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    lastPromise.future
  }

  //example
  val fast = Future {
    Thread.sleep(100)
    42
  }
  val slow = Future {
    Thread.sleep(200)
    45
  }

  first(fast, slow).foreach(f => println("FIRST: " + f))
  last(fast, slow).foreach(l => println("LAST: " + l))

  Thread.sleep(5000)


  //5.retry until
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }

  //example
  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) => x < 10).foreach(result => println("settled at " + result))

  Thread.sleep(10000)
}
