package exercises

object OOClasses extends App {
  val novelWriter = new Writer("John", "Wick", 1986)
  println(novelWriter.getFullName())

  val novel = new Novel("Tom Sawyer", 2018, novelWriter)
  println(novel.getAuthorAge())
  println(novel.isWrittenBy(novelWriter))
  val newNovelEdition = novel.copy(2021)
  println(s"new edition for ${newNovelEdition.name} was publish on ${newNovelEdition.yearOfRelease}")

  val counter = new Counter(5)
  val newCounter1 = counter.increment()
  println(newCounter1.getCounter())
  val newCounter2 = newCounter1.increment(5)
  println(newCounter2.getCounter())
}


class Writer(firstName: String, lastName: String, val yearOfBirth: Int) {

  def getFullName(): String = s"$firstName $lastName"
}

class Novel(val name: String, val yearOfRelease: Int, author: Writer) {

  def getAuthorAge(): Int = author.yearOfBirth

  def isWrittenBy(author: Writer): Boolean = this.author == author

  def copy(newYear: Int): Novel = new Novel(name, yearOfRelease = newYear, author)
}

class Counter(val number: Int) {

  def getCounter(): Int = number

  def increment(): Counter = new Counter(number + 1) //immutability

  def decrement(): Counter = new Counter(number - 1)

  def increment(number: Int): Counter = new Counter(this.number + number)

  def decrement(number: Int): Counter = new Counter(this.number - number)
}