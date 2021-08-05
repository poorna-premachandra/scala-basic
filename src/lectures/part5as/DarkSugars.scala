package lectures.part5as

import scala.util.Try

object DarkSugars extends App {

  //#1: methods with single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks.."

  val description = singleArgMethod {
    //some code
    42 //parameter
  }

  println(description) // prints "42 little ducks.."

  //examples

  val aTryInstance = Try {
    //some code
    throw new RuntimeException //parameter
  }

  List(1, 2, 3).map {
    x => x + 1
  }


  //#2: single abstract method pattern
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = (x: Int) => x + 1

  //also works with abstract classes where one method left un-implemented
  //useful when instantiating Thread with passing Runnable instance as a param
  //val aThread = new Thread(() => println("Hello, Scala"))


  //#3: the :: and #:: methods
  val prependedList = 2 :: List(3, 4)  // equals List(3, 4).::(2)
  println(prependedList) // prints List(2, 3, 4)

  //scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4 ,5)
  List(4, 5).::(3).::(2).::(1) //equivalent for above

  class MyStream[T] {
    //method ends with ":" are right associative, else left associative
    def -->:(value: T): MyStream[T] = this //actual impl here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]


  //#4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  val lillySaid = lilly `and then said` "Something"

  //#5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???


  //#6: update() is a special as apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 5 // rewritten to anArray.update(2, 7)
  //used in mutable collections


  //#7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 //private for OO encapsulation
    def member = internalMember //getter
    def member_=(value: Int): Unit = internalMember = value //setter
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 23 //rewritten as aMutableContainer.member_=(42)
}
