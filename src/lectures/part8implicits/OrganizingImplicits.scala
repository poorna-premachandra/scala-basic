package lectures.part8implicits

object OrganizingImplicits extends App {
  //  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  //  implicit def normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _) compiler gets confused if there are two implicits methods
  println(List(1, 4, 5, 3, 2).sorted) //sorted method takes a implicit param. If not overridden, this is by default taken by 'Predef' scala library.

  /*
  possible implicits (used as implicit parameters)
   - val/var
   - object
   - accessor methods = defs with NO parenthesis
   */

  //Exercise
  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

  //order persons by name
  //  implicit def alphabeticalOrdering: Ordering[Person] = Ordering.fromLessThan(_.name.charAt(0) < _.name.charAt(0)) // or ((a, b) => a.name.compareTo(b.name) < 0)
  //  println(persons.sorted)

  /*
  Implicit scope - priority levels
    - normal scope = LOCAL SCOPE (highest priority) (like we did above)
    - imported scope (like 'global' implicit we imported in Future lectures)
    - companions of all types involved in the method signature (if we take the method signature of above example, def sorted[B >: A](implicit ord : scala.Ordering[B]) : List[B]
        - List (output)
        - Ordering (input)
        - all the types involved = A or any supertype (e.g - if we create a Person companion object and put the implicit method inside it as below)
        object Person {
          implicit def alphabeticalOrdering: Ordering[Person] = Ordering.fromLessThan(_.name.charAt(0) < _.name.charAt(0))
        }
 */

  //structuring implicits
  object AlphabeticalOrdering {
    implicit def alphabeticalOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit def ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  import AgeOrdering._ //imported scope

  println(persons.sorted)


  //EXERCISE - organising implicits
  case class Purchase(nUnits: Int, unitPrice: Double)

  //Order by these criteria
  //1. totalPrice (this method id 50% used)
  //2. unit count (this method id 25% used)
  //3. unit price (this method id 25% used)


  object Purchase { //inside Purchase companion object - can be used directly
    implicit def totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object UnitCountOrdering { //have to import and use
    implicit def unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering { //have to import and use
    implicit def unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }
}
