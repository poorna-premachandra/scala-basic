package lectures.part9ts

object PathDependentTypes extends App {

  class Outer {
    class Inner

    object InnerObject

    type InnerType

    def print(i: Inner): Unit = println(i)

    def printGeneral(i: Outer#Inner): Unit = println(i)
  }

  def aMethod: Int = {
    class HelperClass //This definition of a class inside a method is valid
    //    type HelperType //This won't compile because, anywhere other than classes and traits, we can only define types as aliases
    type HelperType = String //Here we defined the type as an alias

    2
  }

  val o = new Outer
  //In order to refer to an inner type we need an outer instance

  //  val inner = new Inner             This won't work because Inner is in scope of Outer
  //  val inner = new Outer.Inner       This won't work either

  val inner = new o.Inner //This works. "o.Inner" is a TYPE

  val oo = new Outer
  //  val otherInner: oo.Inner = new o.Inner   //This won't work because oo.Inner and o.Inner are different types
  val otherInner: oo.Inner = new oo.Inner

  o.print(inner) //This works
  //  oo.print(inner)    //This doesn't work. Here the method parameter expects a type of "oo.Inner". This is called path-dependent types

  //However, all the inner types have a super type which is "Outer#Inner"
  o.printGeneral(inner)
  oo.printGeneral(inner) //Here both statement works because "o.Inner" and "oo.Inner"" are subtypes of "Outer#Inner"

  //When you want objects created and managed by a specific instance of an outer type cannot be accidentally or purposely interchanged/mix with an another instance created by the same outer type, then path-dependent types can be used


  //Exercise

  trait ItemLike {
    type Key
  }

  trait Item[K] extends ItemLike {
    type Key = K
  }

  trait IntItem extends Item[Int]

  trait StringItem extends Item[String]

  def get[ItemType <: ItemLike](key: ItemType#Key): ItemType = ???

  //Result
  get[IntItem](40) // ok
  get[StringItem]("home") //ok
  get[IntItem]("home") //not ok
}