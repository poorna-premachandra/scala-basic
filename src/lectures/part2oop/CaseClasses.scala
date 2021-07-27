package lectures.part2oop

object CaseClasses extends App {

  case class Person(name: String, age: Int)

  //1. class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name)

  //2. implemented to String method
  println(jim.toString)
  println(jim) //println(instance) = println(instance.toString)    syntactic sugar

  //3. implemented equals and hashCode methods
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)

  //4. copy methods
  val jim3 = jim.copy(age = 45)
  println(jim3)

  //5. have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23) //apply method in CCs does same thing as Constructors
  // In practice, we use above form to instantiate case classes(doesn't use new keyword)

  //6. CC are serializable
  //useful when dealing with distributed systems (e.g.- Akka framework sends serializable messages through network)

  //7. CCs have extractor patterns == CC can be used in PATTERN MATCHING


  //similar to CC except that they are their own companion objects
  case object UnitedKingdom {
    def name: String = "The UK"
  }

}
