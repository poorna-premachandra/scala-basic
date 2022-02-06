package lectures.part9ts

object AdvancedInheritance extends App {

  //1. convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closable): Unit = { //"GenericStream[T] with Writer[T] with Closable" is a single type
    stream.foreach(println)
    stream.close(0)
  }


  //2. diamond problem
  trait Animal {def name: String}
  trait Lion extends Animal {override def name: String = "lion"}
  trait Tiger extends Animal {override def name: String = "tiger"}
  class Mutant extends Lion with Tiger

  val m = new Mutant
  println(m.name)

  //prints tiger
  //LAST OVERRIDE GETS PICKED


  //3. type linearization
  trait Cold {
    def print = println("cold")
  }

  trait Green extends Cold {
    override def print = {
      println("green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print = {
      println("blue")
      super.print
    }
  }

  trait Red {
    def print = println("red")
  }

  class White extends Red with Green with Blue {
    override def print = {
      println("white")
      super.print
    }
  }

  val color = new White
  color.print //white, blue, green, cold

  //Cold = AnyRef with <Cold>
  //Green = Cold with <Green> = AnyRef with <Cold> with <Green>
  //Blue = Cold with <Blue> = AnyRef with <Cold> with <Blue>
  //Red = AnyRef with <Red>

  //white = Red with Green with Blue with <White>
  //      = AnyRef with <Red> with AnyRef with <Cold> with <Green> with AnyRef with <Cold> with <Blue> with <white>
  //      = AnyRef with <Red> with X with <Cold> with <Green> X with X with <Blue> with <white>   <- multiple occurrence of a type is neglected
  //      = AnyRef with <Red> with <Cold> with <Green> with <Blue> with <White>  <- types will be linearized like this
  // so it prints white, blue, green, cold
  //white's super will be blue, blue's super will be green, green's super will be cold.. etc.

}
