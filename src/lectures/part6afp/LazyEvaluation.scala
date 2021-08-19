package lectures.part6afp

object LazyEvaluation extends App {

  //lazy DELAYS the evaluation of values
  //val x: Int = throw new RuntimeException
  lazy val y: Int = throw new RuntimeException

  //lazy evaluates once
  lazy val z: Int = {
    println("hello")
    42
  }
  println(z)
  println(z)
  //prints
  //hello
  //42
  //42


  //examples
  //1. side effects
  def sideEffectCondition: Boolean = {
    println("Bool")
    true
  }
  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no") //not printing "Bool" because lazyCondition not evaluated

  //2. in conjunction with "call by name"
  def byNameMethod(n: => Int): Int = {
    //CALL BY NEED
    lazy val t = n //only evaluated once
    t + t + t + 1 //if n + n + n , will evaluated 3 times(prints "waiting" 3 times)
  }

  def retrieveMagicValue = {
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byNameMethod(retrieveMagicValue))

  //3. filtering with lazy val
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30Lazy = numbers.withFilter(lessThan30) //withFilter use vals under the hood
  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)
  println
  gt20Lazy.foreach(println) //evaluates predicates "by needs" basis


  //for-comprehensions use withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0 //if guards use lazy vals
  } yield a + 1
  // rewrites into List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)
}
