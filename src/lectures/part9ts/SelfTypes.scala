package lectures.part9ts

class SelfTypes extends App {

  trait Instrumentalist {
    def play(): Unit
  }

  trait Singer {
    self: Instrumentalist => //Enforces whoever implements Singer should also implement Instrumentalist as well
    def sing(): Unit
  }

  //The naming 'self' is flexible. It can be any name including 'this'

  class LeadSinger extends Singer with Instrumentalist {
    override def sing(): Unit = ???
    override def play(): Unit = ???
  }

  //  class Vocalist extends Singer {   // This won't work
  //    override def sing(): Unit = ???
  //  }


  class A
  class B extends A //In inheritance, it says B is an A

  trait T {
    def executeT(): String
  }

  trait S {
    self: T => //Here it says S requires T
    def executeS():String = executeT() + " is completed." //Note that trait S has access to trait T's executeT method
  }

  //Cake Pattern can be implemented using this concept. This pattern is also known as "dependency injection" in other languages.
}
