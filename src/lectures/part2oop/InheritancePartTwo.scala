package lectures.part2oop

object InheritancePartTwo extends App {

  abstract class Animal { //abstract class
    val creatureType:String  //an abstract field(notice that we haven't use abstract keyword here)
    def eat: Unit  //an abstract method
    def sleep: Unit = println("ZzZzZzZzZz")  //not an abstract method
  }

  class Dog extends Animal { //when extending have to implement all the abstract methods
    val creatureType: String = "Canine"  //can do without 'override' keyword since we are implementing an abstract member
    def eat: Unit = println("Crunch Crunch")
  }


  //traits
  trait Carnivore { //similar to interfaces in JAVA
    def eat(animal: Animal): Unit //can have abstract field and methods
    val like: String = "Meat " //can also have non abstract field and methods
  }

  trait Reptile

  class Crocodile extends Animal with Carnivore with Reptile { //can include many trait as we want like in interfaces in Java
    val creatureType: String = "Croc"
    def eat: Unit = println("NomNomNom")
    def eat(animal: Animal): Unit = println(s"I'm a ${creatureType} and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile

  croc.eat(dog)

//abstract classes vs traits
  // 1. traits do not have constructor parameters
  // 2 . multiple traits may inherited by the same class
  // 3. traits are behaviors where as abstract class are type of things


  //Scala's Type Hierarchy
  //    scala.Any    <-     scala.AnyRef(java.lang.Object such as String, List, Set or any user created classes)     <-      scala.Null      <-      scala.Nothing
  //    scala.Any    <-     scala.AnyVal(contains primitive types like Int, Unit, Boolean, Float ..etc.)    <-     scala.Nothing

  //Nothing can replace everything
}
