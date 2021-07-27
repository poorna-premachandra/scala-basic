package lectures.part3fp

object WhatAFunction extends App {

  //working with functions ; pass functions as params, get functions as returns

  //function traits are available upto 22 params

  //all scala functions are objects instantiated from anonymous classes of traits

  val adder = new Function2[String, String, String] { //Function2 supports two input types and one output type
    override def apply(a: String, b: String): String = a + b
  }

  //val adder = (a: String, b: String) => a + b

  println(adder ("Hello","Poorna"))

  //higher order functions - either receives functions as parameters or return functions as results

  val specialFunction: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(a: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(b: Int): Int = a + b
    }
  }

  println(specialFunction(2)(3)) // this is a curried function

}