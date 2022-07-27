package lectures.part9ts

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType //abstract type member
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    type AnimalC = Cat //type alias
  }

  val ac = new AnimalCollection

  val dog: ac.AnimalType = ??? //cannot construct

  //val cat: ac.BoundedAnimal = new Cat    not compiling(compiler does not know what is bounded animal)

  val pup: ac.SuperBoundedAnimal = new Dog

  val cat: ac.AnimalC = new Cat //can construct

  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  //we may see this kind of code(alternative to generics)
  trait MyList {
    type T
    def add(element: T): MyList
  }

  //have to override explicitly unlike in generics
  class NonEmptyList(value: Int) extends  MyList {
    override type T = Int
    override def add(element: Int): MyList = ???
  }

  // .type
  type CatsType = cat.type
  val newCat: CatsType = cat
  // new CatsType    not compiling


  //Exercise - enforce a type to be applicable to 'some types' only

  //not allowed to change
  trait MList {
    type A

    def head: A
    def tail: MList
  }

  //enforcing to allow only int types
  trait MListRefactored {
    type A <: Number
  }

  class IntList(hd: Integer, tl: IntList) extends MList with MListRefactored {
    type A = Integer

    override def head: Integer = hd
    override def tail: MList = tl
  }

  //does not compile
//  class StringList(hd: String, tl: StringList) extends MList with MListRefactored {
//    type A = String
//
//    override def head: String = hd
//    override def tail: MList = tl
//  }
}
