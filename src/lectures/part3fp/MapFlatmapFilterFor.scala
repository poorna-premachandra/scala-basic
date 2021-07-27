package lectures.part3fp

object MapFlatmapFilterFor extends App {

  var list = List(1, 2, 3)
  println(list.head)
  println(list.tail)

  //map         A => B : MyList[B]
  println(list.map(_ + 1))

  //filter      A => Boolean : MyList[A]
  println(list.filter(_ % 2 == 0))

  //flatMap     A => MyList[B] : MyList[B]
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  //print all combination between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')

  //Iterating
  //use map and flatmap for 2 loops
  println(numbers.flatMap((x) => chars.map(_.toString + x)))

  //for 3 loops use 2 flatmaps and a map

  //foreach
  numbers.foreach(println)

  //for-comprehensions (an alternative, more readable way for writing chain loops)
  val forCombination = for {
    n <- numbers if n% 2 == 0  //can include a guard(similar to using filter method in chain loops)
    c <- chars
  } yield c.toString + n

  println(forCombination)

  //foreach written in for-comprehensions
  for {
    n <- numbers
  } println(n)


  //syntax overload
  numbers.map { x => x * 2 }



}
