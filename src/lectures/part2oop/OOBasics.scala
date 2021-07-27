package lectures.part2oop

object OOBasics extends App {

  val person = new Person("John", 26)
  person.greet("Poorna")
}

//class Person(name: String, age: Int) //class parameters are not class attributes
class Person(val name: String, val age: Int = 0) { //defined class parameters(constructor) with val/var to make them class attributes
  //body

  val x = 2 //this is also class attribute

  //method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  //method overloading
  def greet(): Unit = println(s"Hi, I am $name") //'this' keyword automatically implied

  //multiple constructors
  //def this(name: String) = this(name, 0) //auxilary constructors should call either primary or secondary constructors

  //assigning a default value to class param removed the need to create intermediate constructor
  def this() = this("Sam")
}


