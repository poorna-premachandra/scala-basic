package lectures.part4pm

object AllPatterns extends App {

  //1. match constant
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "the Scala"
    case true => "The truth"
  }

  //2. match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something" //whatever we pass as x gets matched to something
  }

  //3. match tuple
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I've found $something"
  }

  //nested pattern matching
  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }

  case class Person(name: String, age: Int)

  //4. cases classes - constructor pattern
  //PM can be nested with CCs as well
  val aPerson: Person = Person("Sam", 28)
  val matchAPerson= aPerson match {
    case Person(name, age) =>
  }

  //5. list patterns
  val aStandardList = List(1, 2, 3, 4)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => //extractor
    case List(1, _*) => //list of arbitrary length (var args style)
    case 1 :: List(_) => //infix pattern
    case List(1, 2, 3) :+ 42 => // infix pattern
  }

  //6. type specifier
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => //explicit type specifier
    case _ =>
  }

  //7. name binding
  //can also used in nested patterns
  val nameBindingMatch = aPerson match {
    case nonEmpty@Person(_, _) => //name binding => use the name later(here)
  }

  //8. multi pattern
  val multiPattern = aPerson match {
    case Person(_, 18) | Person(_, 20) =>
  }

  //9. if guards
  val secondElementSpecial = aPerson match {
    case Person(_, age) if age > 18 =>
  }

  //FINISHED

  //Special case to keep in mind
  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of string"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch) //a list of string
  //This is due to the JVM behaviour of backward compatability to support older java 1, which makes JVM erase generic types
}
