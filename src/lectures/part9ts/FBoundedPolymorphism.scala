package lectures.part9ts

class FBoundedPolymorphism extends App {

  //  trait Animal {
  //    def breed: List[Animal]
  //  }
  //
  //  class Cat extends Animal {
  //    override def breed: List[Animal] = ??? //We want this to return List[Cat].
  //  }
  //
  //  class Dog extends Animal {
  //    override def breed: List[Animal] = ??? //We want this to return List[Dog]
  //  }

  //How do we do that?


  //Solution #1
  //    trait Animal {
  //      def breed: List[Animal]
  //    }
  //
  //    class Cat extends Animal {
  //      override def breed: List[Cat] = ??? //This is ok
  //    }
  //
  //    class Dog extends Animal {
  //      override def breed: List[Cat] = ??? //But this code is also valid. This is not ok. We want the compiler to tell us this is wrong.
  //    }


  //Solution #2
  //    trait Animal[A <: Animal[A]] { //recursive type: F-Bounded Polymorphism(FBP)
  //      def breed: List[Animal[A]]
  //    }
  //
  //    class Cat extends Animal[Cat] {
  //      override def breed: List[Animal[Cat]] = ??? //This is ok
  //    }
  //
  //    class Dog extends Animal[Dog] {
  //      override def breed: List[Animal[Dog]] = ??? //This is ok
  //    }
  //
  //    class Crocodile extends Animal[Dog] {
  //      override def breed: List[Animal[Dog]] = ??? //But this is not ok.
  //    }

  //This F-Bounded Polymorphism often found in ORMs
  //trait Entity[E <: Entity[E]]


  //Solution 3 - Use FBP with self-types
  //  trait Animal[A <: Animal[A]] {
  //    self: A => //This says, whatever animal descendent "A"(E.g- Crocodile) I'm going to be implement, it should also be an "A"(E.g- Crocodile)
  //    def breed: List[Animal[A]]
  //  }
  //
  //  class Cat extends Animal[Cat] {
  //    override def breed: List[Animal[Cat]] = ??? //This is ok
  //  }
  //
  //  class Dog extends Animal[Dog] {
  //    override def breed: List[Animal[Dog]] = ??? //This is ok
  //  }
  //
  //  class Crocodile extends Animal[Crocodile] {
  //    override def breed: List[Animal[Crocodile]] = ??? //This is ok now
  //  }
  //
  //  //But there is a limitation to this solution
  //  //If we bring down the class hierarchy by 1 level, this solution will not be effective
  //
  //  trait Fish extends Animal[Fish]
  //
  //  class Shark extends Fish {
  //    override def breed: List[Animal[Fish]] = List(new Cod) //This is not ok!
  //  }
  //
  //  class Cod extends Fish {
  //    override def breed: List[Animal[Fish]] = ???
  //  }


  //Solution 4 - Use type class pattern
  //  trait Animal
  //
  //  trait CanBreed[A] {
  //    def breed(a: A): List[A]
  //  }
  //
  //  class Dog extends Animal
  //
  //  object Dog {
  //    implicit object DogsCanBreed extends CanBreed[Dog] {
  //      override def breed(a: Dog): List[Dog] = List()
  //    }
  //  }
  //
  //  implicit class CanBreedOps[A](animal: A) {
  //    def breed(implicit canBreed: CanBreed[A]): List[A] = canBreed.breed(animal)
  //  }
  //
  //  val dog = new Dog
  // dog.breed // new CanBreedOps[Dog](dog).breed(Dog.DogsCanBreed)
  //
  //  class Cat extends Animal
  //
  //  object Cat {
  //    implicit object CatsCanBreed extends CanBreed[Cat] {
  //      override def breed(a: Cat): List[Cat] = List()
  //    }
  //  }
  //
  //  val cat = new Cat
  // cat.breed


  //Solution 5 - Cleaner than the solution 4
  trait Animal[A] { //pure type classes
    def breed(a: A): List[A]
  }

  class Dog

  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal[A]): List[A] = animalTypeClassInstance.breed(animal)
  }

  val dog = new Dog
  dog.breed
}
