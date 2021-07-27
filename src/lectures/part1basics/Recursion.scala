package lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  //factorial function written using tail recursion

  def tailRecFactorial(n: Int): BigInt = {
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)
    }
    factHelper(n, 1)
  }

  //concatenate a string n times
  def concatString(n: String, m: Int): String = {
    @tailrec //to verify this is a tail recursive function
    def concatStringHelper(x: String, accumulator: Int): String = {
      if (accumulator == 1) x
      else concatStringHelper(x + n, accumulator - 1)
    }
    concatStringHelper(n, m)
  }

//  println(concatString("poorna", 2))


  //fibonacci sequence in tail recursion
  def getFibonacci(number: Int): Int = {
    @tailrec
    def fibHelper(prevNum1: Int, prevNum2: Int, counter: Int): Int = {
      if (counter == number) prevNum1
      else fibHelper(prevNum1 + prevNum2, prevNum1, counter + 1)
    }
    fibHelper(1, 1, 2)
  }

  println("fib " + getFibonacci(14))

  def getFactorial (n: Int): BigInt = {
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x-1, x * accumulator)
    }
    factHelper(n, 1)
  }

}
