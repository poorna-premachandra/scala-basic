package lectures.part1basics

object StringOps extends App {

  //S-interpolators
  val name= "David"
  val age = 12
  val greeting = s"Hello, my name is $name and I am $age years old"
  val anotherGreeting = s"Hello, my name is $name and I am ${age + 1} years old"
  println(greeting)


  //F-interpolators
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.4f burgers per minute"
  println(myth)

  //F-interpolators can also be used to check type correctness
//  val x = 1.1f
//  val str = f"$x%3d"  //ERROR : x is not a decimal

  //raw-interpolators
  println(raw"This is a \n newline")  //ignores escape characters(printed in a single line)

  val escaped = "This is a \n newline"
  println(raw"$escaped") //this is printed in two lines
}
