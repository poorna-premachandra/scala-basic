package lectures.part3fp

object AnonymousFunctions extends App {

  //anonymous function (LAMBDA)
  val doubler = (a: Int) => a * 2
  //val doubler: Int => Int = a => a * 2    -> same as above

  println(doubler(3))

  //multiple params in a lambda
  val adder: (Int, Int) => Int = (a, b) => a + b

  //no params
  val noParam: () => Int = () => 3

  println(noParam)   //print object reference
  println(noParam()) //call apply method

  //more syntactic sugar
  val niceIncrement: Int => Int = _ + 1 //equivalent to x => x +1
  val niceAdder: (Int, Int) => Int = _ + _ //equivalent to (a, b) => a + b


//  val specialFunction: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
//    override def apply(a: Int): Function1[Int, Int] = new Function1[Int, Int] {
//      override def apply(b: Int): Int = a + b
//    }
//  }

  val specialFunction: (Int) => (Int => Int) = a => (b => a + b)
  println(specialFunction(2)(3))
}
