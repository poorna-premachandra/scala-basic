package lectures.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("hahahahahaha")
  }

  println(funnyAnimal.getClass) //anonymous class

  //also
  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name")
  }

  val jim = new Person("Jim") {// <- have to include constructor parameters
    override def sayHi: Unit = println(s"Hello, I'm Jim")
  }

  println(jim.getClass) //also an anonymous class.
}
