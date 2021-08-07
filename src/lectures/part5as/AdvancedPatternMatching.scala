package lectures.part5as

import lectures.part2oop.Generics.MyList

object AdvancedPatternMatching extends App {

  val numbers = List(1, 2, 3)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head")
    case _ =>
  }

  //to enable pattern matching for normal classes
  class Person(val name: String, val age: Int)

  object Person { //PM depend on this object not on class, but as a best practice we name the object with the same name as class
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None //can add a if guard
      else Some(person.name, person.age)

    def unapply(age: Int): Option[String] =
      Some(if(age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 22)
  val greeting = bob match { //bob is passed on unapply(person: Person)
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
  }

  println(greeting)

  val legalStatus = bob.age match { //bob.age is passed on unapply(age: Int)
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)


  //Exercise

  //  object even {
  //    def unapply(arg: Int): Option[Boolean] =
  //      if (arg % 2 == 0) Some(true)
  //      else None
  //  }

  //above method can be reduced as follows
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigit { //can reduce the method like this
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val n: Int = 12
  val matchedVal = n match {
    case singleDigit() => "single digit"
    case even() => "an even number"
    case _ => "no property"
  }

  println(matchedVal)

  //pros - can reuse patterns
  //cons - code can get verbose with more patterns


  //infix pattern
  case class Or[A, B](a: A, b: B)
  val either = Or(2, "two")
  val readableDesc = either match {
    case number Or string => s"$number is written as $string" //Or(number, string) => s"$number is written as $string"
  }
  println(readableDesc)


  //decomposing sequence
  val vararg = numbers match { //this won't work, we need an impl with unapplySeq
    case List(1, _*) => "starting with 1"
  }

  //decomposing sequence - impl
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _ => "something else"
  }

  println(decomposed)


  //custom return types for unapply - rarely used in practice
  //need to have two methods. 1. isEmpty: Boolean  2. get: something

  abstract class Wrapper[T] { //this is the class we used instead of Option
    def isEmpty: Boolean //these two methods should be present
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(n) => s"Person's name is $n"
    case _ => ""
  })
}
