package lectures.part1basics

import scala.annotation.tailrec

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 5))

  def aNonParamFunc(): Int = 42

  println(aNonParamFunc())
  println(aNonParamFunc) //can also be called with only the name

  //we do looping in scala like this, using recursion
  def aRepeatedFun(aString: String, n: Int): String = {
    if (n == 1) aString else aString + " " + aRepeatedFun(aString, n - 1)
  }

  println(aRepeatedFun("Poorna", 3))

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  aFunctionWithSideEffects("this is a function returning unit")


  //nested functions
  def aBigFunc(a: String): String = {
    def aSmallFunc(b: String, c: String): String = b + c

    aSmallFunc(a, a + 1) //return expression of outer function
  }

  def greetingFunc(name: String, age: Int): String = "Hi, My name is %s and I'm %s years old".format(name, age)

  println(greetingFunc("Poorna", 24))


  def getFactorial(number: Int): Int = {
    if (number == 1) 1
    else number * getFactorial(number - 1)
  }

  println(getFactorial(5))

  var counter = 0

  def getFibonacci(number: Int): Int = {
    if (number <= 2) 1
    else getFibonacci(number - 1) + getFibonacci(number - 2)
  }

  println("fib " + getFibonacci(5))

@tailrec
  def isPrime(number: Int, iterator: Int): Boolean = {
    if (iterator < number / 2) {
      if (number % iterator == 0) false
      else isPrime(number, iterator + 1)
    }
    else true
  }

  println("prime " + isPrime(13, 2))
}
