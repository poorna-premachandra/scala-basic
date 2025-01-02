package lectures.part9ts

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HigherKindedTypes extends App {

  trait AHigherKindedType[F[_]] //This is a higher kinded type

  trait MyList[T] {
    def flatMap[B](f: T => B): MyList[B]
  }

  trait MyOption[T] {
    def flatMap[B](f: T => B): MyOption[B]
  }

  trait MyFuture[T] {
    def flatMap[B](f: T => B): MyFuture[B]
  }

  //combining/multiplying List(1, 2) x List("a", "b") => List(1a, 1b, 2a, 2b)

  //  def multiply[A, B](listA: List[A], listB: List[B]): List[(A, B)] =
  //    for {
  //      a <- listA
  //      b <- listB
  //    } yield (a, b)
  //
  //  def multiply[A, B](optionA: Option[A], optionB: Option[B]): Option[(A, B)] =
  //    for {
  //      a <- optionA
  //      b <- optionB
  //    } yield (a, b)
  //
  //  def multiply[A, B](futureA: Future[A], futureB: Future[B]): Future[(A, B)] =
  //    for {
  //      a <- futureA
  //      b <- futureB
  //    } yield (a, b)


  //Goal is to design a 'multiply' method that is supported for any above types List, Option or Future

  //Solution for this is to use a higher kinded type

  trait Monad[F[_], A] { //F[_] represents List, Option or Future and A is type of what it contains
    def flatMap[B](f: A => F[B]): F[B]

    def map[B](f: A => B): F[B]
  }

  implicit class MonadList[A](list: List[A]) extends Monad[List, A] { //implicit was added later
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)

    override def map[B](f: A => B): List[B] = list.map(f)
  }

  //  def multiply[F[_], A, B](ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] = {
  //    for {
  //      a <- ma
  //      b <- mb
  //    } yield (a, b)
  //  }

  def multiply[F[_], A, B](implicit ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] = { //implicit was added later
    for {
      a <- ma
      b <- mb
    } yield (a, b)
  }

  // ma.flatMap(a => mb.map(b => (a, b)))

  val monadList = new MonadList(List(1, 2, 3))
  monadList.flatMap(x => List(x, x + 1)) // List[Int]
  // Monad[List, Int] => List[Int]

  monadList.map(_ * 2) // List[Int]
  // Monad[List, Int] => List[Int]

  println(multiply(new MonadList(List(1, 2)), new MonadList(List("a", "b"))))
  //List((1,a), (1,b), (2,a), (2,b))


  implicit class MonadOption[A](option: Option[A]) extends Monad[Option, A] { //implicit was added later
    override def flatMap[B](f: A => Option[B]): Option[B] = option.flatMap(f)

    override def map[B](f: A => B): Option[B] = option.map(f)
  }

  println(multiply(new MonadOption(Some(2)), new MonadOption(Some("Hello"))))
  //Some((2,Hello))

  //Now we have to generalise the param type in multiply method.
  // For that we will add implicit keyword to multiply method signature.

  //Notice that we don't have to mention wrapper classes anymore
  println(multiply(List(1, 2), List("a", "b"))) //MonadList is not mentioned
  println(multiply(Some(2), Some("Hello"))) //MonadOption is not mentioned
}
