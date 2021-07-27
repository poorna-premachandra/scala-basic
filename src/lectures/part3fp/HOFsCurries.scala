package lectures.part3fp

object HOFsCurries extends App {

  //let's write a function that applies a function n times over a value x
  //nTimes(f, n, x)

  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))
  }

  val plusTen = nTimesBetter(plusOne, 10)
  println(plusTen(1))

  //curried functions - helpful in defining helper functions which we can use later
  val specialFunction: (Int) => (Int => Int) = a => (b => a + b)
  val add3 = specialFunction(3) //y => 3+ y
  println(add3(10)) // or println(specialFunction(3)(10))

  //functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f") //have to specify return type (Double => String)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

}
