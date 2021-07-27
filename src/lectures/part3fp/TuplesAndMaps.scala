package lectures.part3fp

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists
  val aTuple = Tuple2(2, "Hello")
  val altTuple = (2, "Hello")
  println(aTuple)
  println(altTuple)

  println(aTuple._1) // prints 2
  println(aTuple.copy(_2 = "hola"))
  println(aTuple.swap) //(Hello,2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), "Daniel" -> 786).withDefaultValue(-1) //can include default value to avoid throwing exceptions
  println(phoneBook) //a -> b is sugar for (a, b)

  //map operations
  println(phoneBook.contains("Jim"))
  println(phoneBook("Mary"))

  //adding a pair
  val newPair = "Mary" -> 678
  val newPhonebook = phoneBook + newPair
  println(newPhonebook)

  //functionals on map
  //map, flatMap, filter

  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  //filterKeys
  println(phoneBook.filterKeys(x => x.startsWith("J")))

  //mapValues
  println(phoneBook.mapValues(number => "0245-" + number))


  //conversion to other collections
  println(phoneBook.toList)
  println(List(("Daniel", 555)).toMap)

  //groupBy
  val names = List("Bob", "Ben", "Alice", "Anna", "Micheal", "Jim")
  println(names.groupBy(name => name.charAt(0)))
}
