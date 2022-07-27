package lectures.part9ts

object Variance extends App {

  trait Animal

  class Dog extends Animal

  class Cat extends Animal

  class Kitty extends Cat

  class Crocodile extends Animal

  //what is variance? It solves the following problem
  class Cage[T]

  //since cat inherit from animal, should a cage cat also inherit from cage animal?

  //YES - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  //NO - invariance
  class ICage[T]
  val icage: ICage[Animal] = new ICage[Animal]

  //HELL NO (Opposite) - contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]




//val

  class InvariantCage[T](val animal: T) //invariant position

  class CovariantCage[+T](val animal: T) //val animal is in covariant position

  // class ContravariantCage[-T](val animal: T) //This won't compile because if it is, following may possible
  /*
    val ccage: XCage[Cat] = new XCage[Animal](new Crocodile)
  */




//var

  //class CovariantVariableCage[+T](var animal: T) //types of vars are in contravariant position
  /*
    val xcage: CCage[Animal] = new CCage[Cat](new Cat)
    ccage.animal = new Crocodile
  */

  //class ContravariantVariableCage[-T](var animal: T) //types of vars are in covariant position
  /*
   val ccage: XCage[Cat] = new XCage[Animal](new Crocodile)
  */

  class InvariantVariableCage[T](var animal: T) //This is ok




//methods

  //METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION
  //But we do a little hack to widening the type

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B] //widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty) //type is Kitty
  val moreAnimals = animals.add(new Cat) //type is Cat (super of Kitty)
  val evenMoreAnimals = moreAnimals.add(new Dog) //type is Animal (super of both Cat and Dog)




//return types

  //METHOD RETURN TYPES ARE IN COVARIANT POSITION

  class PetShop[-T] {
    def get[S <: T](defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]

  class Beagle extends Dog

  val beaglePup = shop.get(new Beagle)
}
