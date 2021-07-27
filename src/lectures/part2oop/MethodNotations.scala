package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favMovie: String, val age: Int = 25) {
    def likes(movie: String): Boolean = movie == favMovie

    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}" //  -> Infix

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}" //same function implementation as hangOutWith   -> Infix

    def +(name: String): Person = new Person(s"${this.name} (${name})", favMovie)

    def unary_+ : Person = new Person(this.name + s" (${name})", favMovie, age + 1)

    def unary_! : String = s"$name, random quote?" //there need to be a space between unary_! and :  -> Prefix

    def isAlive: Boolean = true //function with no param -> Postfix

    def apply(): String = "apply() is called" //enable to call object as a function   e.g - mary()

    def apply(number: Int) : String = s"$name watched $favMovie $number times"

    def learns(subject: String) : String = s"$name learns $subject"

    def learnScala: String = learns("scala")
  }


  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))

  //Infix notation(operator notation) for method with single param (Syntactic Sugar)
  println(mary likes "Inception") //object   method   param

  //"operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary hangOutWith tom) //hangOutWith act as an operator here
  println(mary + tom) //operators can be named as we wish

  println(1 + 2)
  println(1.+(2)) //ALL OPERATORS ARE METHODS IN SCALA


  //Prefix notation
  val x = -1
  val y = 1.unary_- //unary_ prefix only work with - + ~ !

  println(!mary)
  println(mary.unary_!)


  //Postfix notation
  println(mary.isAlive)
  println(mary isAlive)


  //apply  (enable to call object as a function)
  println(mary.apply())
  println(mary())  //equivalent


  //EXERCISE
  println("EXERCISE")

  val newMary = mary + "the rockstar"
  println(newMary.age)
  println(newMary(5))

  println("younger mary's age : " + mary.age)
  val elderMary = +mary
  println("elder mary's age : " + elderMary.age)

  println(mary learnScala)

  println(mary(3))

}
