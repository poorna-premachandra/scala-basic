package lectures.part3fp

import scala.language.postfixOps

object Sequences extends App {

  //Seq - have a well defined order, can be indexed
  val aSequence = Seq(1, 3, 4, 2)

  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  //Range
  val aRange: Seq[Int] = 1 to 10 //or "1 until 10" to exclude 10
  aRange.foreach(println)

  //simple way to do something 10 times
  (1 to 10).foreach(x => println("Hello"))


  //Lists - A LinearSeq immutable linked list
  val aList = List(1, 2, 3)

  val prepended = 14 +: aList   //also 14 :: aList
  val appended = aList :+ 20

  val apple5 = List.fill(5)("Apple") //curried function
  println(apple5)
  println(apple5.mkString("--"))

  //Vector - default impl of immutable sequences, effective read and writes, fast element addition
  val vector: Vector[Int] = Vector(1, 2, 3)

  //comparing Lists vs Vectors, vectors are much more efficient


  //Arrays - equivalent of simple java arrays, mutable
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3) //defining an array with 3 allocations
  numbers(2) = 0 //mutation   this is syntax sugar for numbers.update(2, 0)

  //arrays and seq
  val numbersSeq: Seq[Int] = numbers  //implicit conversion - check advanced course
  println(numbersSeq)
  //arrays have access to the methods of seq
}
