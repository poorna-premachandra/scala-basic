package lectures.part4pm

object PatternMatchingExtra {

  //#1 - catches are matches
  try {
    //code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => ""
  }

  //#2 - generators are also based on pattern matching
  val list = List(1, 2, 3, 4)
  val evenList = for {
    x <- list if x % 2 == 0
  } yield 10 * x

  //#3 - multiple value definitions
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple //using name binding property of pattern matching
  println(b) //2

  val head :: tail = list
  println(head) //1
  println(tail) //List(2, 3, 4)

  //#4 - partial function based on pattern matching
  val mappedList = list.map { //same as   list.map (x => x match {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "one"
    case _ => ""
  } //mappedList is calle partial function literal

  println(mappedList) //every element in list will be evaluated
}
