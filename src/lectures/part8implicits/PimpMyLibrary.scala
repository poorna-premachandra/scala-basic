package lectures.part8implicits

object PimpMyLibrary extends App {

  //implicit classes
  implicit class RichInt(val value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(f: () => Unit): Unit = {
      def timesAux(n: Int): Unit = {
        if (n <= 0) ()
        else {
          f()
          timesAux(n - 1)
        }
      }
      timesAux(value)
    }
    def *[T](list: List[T]): List[T] = {
      def concatanate(n: Int): List[T] = {
        if (n <= 0) List()
        else concatanate(n - 1) ++ list
      }
      concatanate(value)
    }
  }


  41.isEven // new RichInt(42).isEven
  //this is called type enrichment = pimping
  // e.g - 1 to 10

  3.times(() => println("Hello World"))
  println(2 * List(1, 2, 3))

  //NOTE : compiler doesn't do multiple implicit searches
  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }
  //42.isOdd   - this doesn't work

  //EXERCISE : enrich the String class
  implicit class RichString(val value: String) {
    def asInt: Int = value.toInt
    def encrypt: String = value.map(chr => (chr.toInt + 2).toChar)
  }

  /*
  BEST PRACTICES
  1. keep type enrichment to implicit classes and type classes
  2. avoid implicit defs as much as possible
  3. package implicits clearly, bring into scope only what you need
  4. if you need type conversions, make them specific
   */
}
