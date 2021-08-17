package lectures.part6afp

object CurriesPAF extends App { //curries and partially applied function/partial functions

  //scala support curried function definitions
  def curriedAdder(x: Int)(y: Int): Int = x + y

  val adder: Int => Int = curriedAdder(3) //transforming a method(def) to a function(val) is called "lifting"(ETA-expansion)
  println(adder(4))

  //functions != methods (JVM limitation)

  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) //this does the ETA-expansion and rewrite into .map(x => inc(x))


  //partial function applications
  val add5 = curriedAdder(5) _ //notice we haven't defined type but it still works
  //we tell compiler to do a ETA expansion and convert this into Int => Int

  //EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  //write different ways to add7
  val add7option1 = (x: Int) => simpleAddFunction(7, x) //can have any of above three
  val add7option2 = simpleAddFunction.curried(7)
  val add7option7 = simpleAddFunction(7, _: Int)
  val add7option3 = curriedAddMethod(7) _ //partially applied function
  val add7option4 = curriedAddMethod(7)(_) //PAF = alternative syntax

  val add7option5 = simpleAddMethod(7, _: Int) // rewrites into y => simpleAddMethod(7, y)

  //use of underscore
  def concatenate(a: String, b: String, c: String) = a + b + c
  val insertName = concatenate("Hello, I'm ", _: String, " how are you?")
  println(insertName("Poorna"))

  //also use for multiple params
  val multiParamExample = concatenate("Hello", _: String, _: String)
  println(multiParamExample("firstP", "secondP"))


  //Exercise
  //1. process a list of numbers and return their string representations with different formats

  //  val numberFormatter = (x: String) => (y: Double) => x.format(y)
  def numberFormatter(x: String)(y: Double) = x.format(y)
  val simpleFormatter = numberFormatter("%4.2f") _ //lift
  val complexFormatter = numberFormatter("%8.6f") _
  println(List(11.4576345346, Math.PI, 67.234212341).map(simpleFormatter))
  println(List(11.4576345346, Math.PI, 67.234212341).map(complexFormatter))

  //2, difference between
  def byName(n: Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenthesisMethod(): Int = 42

  /*
  calling byName and byFunction
  - int
  - method
  - parenthesisMethod
  - lambda
  - PAF
   */

  byName(23) //ok
  byName(method) //ok
  byName(parenthesisMethod()) //ok
  byName(parenthesisMethod) //ok. calling parenthesisMethod()
  //byName(() => 42) //not ok
  byName((() => 42)()) //ok - calling the lambda
  //byName(parenthesisMethod _) //not ok


  //byFunction(45) //not ok
  //byFunction(method) //not ok!!! does not do ETA-expansion
  byFunction(parenthesisMethod) //ok. compiler does ETA-expansion
  byFunction(() => 46) //ok
  byFunction(parenthesisMethod _) //ok with warnings - unnecessary. compiler already do ETA-expansion
}
