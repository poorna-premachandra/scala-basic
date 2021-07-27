package lectures.part2oop

object Generics extends App {

  class MyList[A]{ //defining a generic class with type parameter A
  }

  val listOfInt = new MyList[Int]
  val listOfString = new MyList[String]

  //trait MyList[A]  trait can be also type parameterized

  class MyMap[key, value] //can have multiple type parameters

  //generic methods
  object MyList {  //object can not type parameterized
    def empty[A] : MyList[A] = ???
  }

  val emptyListOfIntegers = MyList.empty[Int]


  //variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. Yes, List[Cat] extends List[Animals]   ->   COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  //But what should happen if we add a Dog instance into List of Cats?

  // 2. No   ->   INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]  //must be of same type

  // 3. Hell, No!   ->    CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]  //not makes sense in this case but useful in different scenario

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]  //makes sense in this case

  //bounded types (<: is upperbound, >: is lowerbound)
  class Cage[A <: Animal](animal: A) //whatever we are providing as A should extend Animal

  val cage = new Cage(new Dog)

  //This is the solution to the above question
  class MyListEx[+A]{
    def add[B >: A](element: B): MyListEx[B] = ??? //if we add a Dog instance, we will get a list of Animals instead of list of Cats
  }


}
