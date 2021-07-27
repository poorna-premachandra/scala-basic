package lectures.part2oop

object Inheritance extends App {

  //single class instance
  class Animal {
    val creatureType = "wild"

    //    private def eat = println("nomnom")  //cannot access outside of class for private modifier
    protected def sleep = println("Zzzzzz")

    def eat = println("nomnom")

  }

  class Cat extends Animal{
  }

  class Dog extends Animal {
    override val creatureType: String = "domestic" //overriding attributes with 'override' keyword

    override def sleep = { //overriding method
      super.sleep //only sub-classes can access protected attributes/methods
      println("Dog Sleeps")
    }
  }

  val cat = new Cat
  cat.eat

  val dog = new Dog
  dog.sleep

  //when extending class with constructor we do it like this
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age) //have to pass in constructor params
  class Child(name: String, age: Int, idCard: String) extends Person(name) //also possible for multiple constructors


  //overriding

  //alternative way to override attribute
  class Fish(override val creatureType: String) extends Animal {

    override def eat: Unit = {
      println("ChowChow")
    }
  }
  val fish = new Fish("Marine")
  println(fish.creatureType)

  //To prevent override
  //1. use final of method
  //2. use final on class
  //3. seal the class(can extend the class within this file, but prevent extension from another file)


  //overloading/ polymorphism
  val unknownAnimal = new Fish("Pollock")
  unknownAnimal.eat //most overridden value selected
}
