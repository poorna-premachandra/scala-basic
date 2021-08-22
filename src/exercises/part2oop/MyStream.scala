package exercises.part2oop

import scala.annotation.tailrec

abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] //prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] //concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] //take the first n elements out of this stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object EmptyStream extends MyStream[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException
  def tail: MyStream[Nothing] = throw new NoSuchElementException

  def #::[B >: Nothing](element: B): MyStream[B] = new Cons[B](element, this)
  def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  def foreach(f: Nothing => Unit): Unit = ()
  def map[B](f: Nothing => B): MyStream[B] = this
  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this
  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this
}

class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] { //tail param is written in 'byName' which helps to lazy evaluation of stream
  def isEmpty: Boolean = false
  override val head: A = hd
  override lazy val tail: MyStream[A] = tl //call by need

  def #::[B >: A](element: B): MyStream[B] = new Cons[B](element, this) //lazy evaluation is still preserved
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream) //(anotherStream: => MyStream[B]) //call by name

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  /* MAP functionality
    s = new Cons(1, ?)
    mapped = s.map(_ + 1) = new Cons(2, s.tail.map(_ + 1))  //tail will not get evaluated here
    mapped.tail //only here the tail gets evaluated
   */
  def map[B](f: A => B): MyStream[B] = new Cons[B](f(head), tail.map(f)) //preserves lazy evaluation
  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f) // should make "def ++" call by name otherwise "tail.flatMap(f)" will be eagerly evaluated
  def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new Cons(head, tail.filter(predicate))
    else tail.filter(predicate) //preserves lazy evaluation

  def take(n: Int): MyStream[A] = {
    if (n <= 0) EmptyStream
    else if (n == 1) new Cons(head, EmptyStream)
    else new Cons(head, tail.take(n - 1))
  }
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] =
    new Cons(start, MyStream.from(generator(start))(generator)) //lazily evaluated
}

object StreamsPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals //naturals.#::(0)
  println(startFrom0.head)

  startFrom0.take(10000).foreach(println) //this doesn't crash :D

  println(startFrom0.map(_ * 2).take(100).toList()) //map
  println(startFrom0.flatMap(x => new Cons(x, new Cons(x + 1, EmptyStream))).take(10).toList()) //flatmap
  println(startFrom0.filter(_ < 10).take(10).toList()) //filter

  //Exercise
  //1. stream of Fibonacci numbers
  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] =
    new Cons(first, fibonacci(second, first + second))

  println(fibonacci(1, 1).take(100).toList())

  //2. stream of prime numbers with Eratosthenes' sieve
  /*
  [ 2 3 4 ... ]

  filter out all numbers divisible by 2 (but keep initial 2)
  [ 2 3 5 7 9 11 ... ]

  filter out all numbers divisible by 3 (but keep initial 3)
  [ 2 3 5 7 11 13 17 ... ]

  etc.. continues with next number in the list
   */
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
  if (numbers.isEmpty) numbers
  else new Cons(numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))

  println(eratosthenes(MyStream.from(2)(_ + 1)).take(100).toList())
}
