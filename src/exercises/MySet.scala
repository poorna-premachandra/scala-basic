package exercises

import scala.annotation.tailrec

//let's prove  Set is a function
trait MySet[A] extends (A => Boolean) { //(A => Boolean) is the apply method?

  def apply(elem: A): Boolean = contains(elem) //general contract of MySet
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A] //union

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A] //difference
  def &(anotherSet: MySet[A]): MySet[A] //intersection

  def unary_! : MySet[A] //negation of a set
}

class EmptySet[A] extends MySet[A] { //defined a class not a object because MySet[A] is in-variance
  def contains(elem: A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()

  def -(elem: A): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true) //all inclusive set
}

//this class is for all elements of type A which satisfy a property
class PropertyBasedSet[A](property:A => Boolean) extends MySet[A] {
  // {x in A | property(x)}
  def contains(elem: A): Boolean = property(elem)

  // {x in A | property(x)} + element = {x in A | property(x) || x == element}
  def +(elem: A): MySet[A] = new PropertyBasedSet[A](x => property(x) || x == elem)

  // {x in A | property(x)} ++ set = {x in A | property(x) || set contains x}
  def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  def map[B](f: A => B): MySet[B] = politelyFail //cannot implement
  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail //cannot implement
  def foreach(f: A => Unit): Unit = politelyFail //cannot implement

  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))

  def -(elem: A): MySet[A] = filter(x => x != elem)
  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}


class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this contains elem) this
    else new NonEmptySet[A](elem, this)

  def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] = (tail map f) + f(head)
  def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head

  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet) //(x => !anotherSet.contains(x)) into (x => !anotherSet(x)) into (!anotherSet)

  //intersection = filter
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet) //(x => anotherSet.contains(x)) can be rewritten as (x => anotherSet(x)) and it can be further reduced to (anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
}


object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }

  object MySetPlayground extends App {
    val s = MySet(1, 2, 3, 4)
    s + 5 ++ MySet(-1, -2) + 3 flatMap (x => MySet(x, 10 * x)) filter (_ % 2 == 0) foreach println

    val negative = !s //s.unary! all the naturals not equal to 1,2,3,4
    println(negative(2)) //true
    println(negative(5)) //false

    val negativeEven = negative.filter(_ % 2 == 0)
    println(negativeEven(5)) //false
  }

}