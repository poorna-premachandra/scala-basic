package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {
  // similar to switch in java but far better

  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "One"
    case 2 => "Two"
    case 3 => "Three"
    case _ => "Something else" // default case      _ = wild card
  }

  println(x)
  println(description)

  //Decomposing values - use for case classes (to extract attributes)
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 18)

  val greeting = bob match {
    case Person(name, age) if age < 20 => s"Hi, My name is $name and I'm a teenager" //can include guards(if conditions)
    case Person(name, age) if age > 20 => s"Hi, My name is $name and I'm an adult"
    case _ => "Who am I?"
  }
  //cases are matched in order. output the first case it matches with
  //if no cases matches, it throws MatchError. Therefore always include a default case with a wild card
  //return type of the pattern match expression depends with the possible output types, if multiple types = Any

  println(greeting)

  //PM on sealed hierarchies
  sealed class Animal //sealed classes let us identify whether we have included a default case or not
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Cheems")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of $someBreed") //building the project will give us a warning
  }

  //EXERCISE
  // Sum(Number(2), Number(3)) should print 2 + 3
  // Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4))) should print (2 + 1) * (3 + 4)

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  //SOLUTION
  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr): String = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }
      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))  //2 + 3
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))  //(2 + 1) * (3 + 4)
}