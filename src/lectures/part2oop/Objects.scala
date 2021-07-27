package lectures.part2oop

object Objects extends App {
  //scala does not have class level functionality(cannot have static attributes/methods in a class like JAVA)
  //instead we use objects
  object Person { //this is a singleton object, it cannot receives parameters
    // static/class level functionality
    val N_EYES = 2 //attribute

    def canFly: Boolean = false //method


    def apply(mother: Person, father: Person): Person = new Person("Bobby") //factory method
  }
  class Person(val name: String){
    // instance level functionality
  }

  //as a practice we write both of these together.
  //since they both have same name and reside in the same scope they are called COMPANIONS(it's a design pattern)

  println(Person.N_EYES)
  println(Person.canFly)

  // Scala object is a singleton instance
  //  val mary = Person
  //  val john = Person
  //  println(mary == john)

  val mary = new Person("Mary")
  val john = new Person("John")

  val bobby = Person(mary, john)

  //Scala Applications = Scala object with the method def main(args: Array[String]): Unit
  //this is equivalent to JAVA's public static void main(Sting[] args)


  //In a Scala application, the object is extending the App class or have the method def main(args: Array[String]): Unit
}
