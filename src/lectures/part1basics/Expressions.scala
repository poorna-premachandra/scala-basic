package lectures.part1basics

object Expressions extends App {

  val x: Int = 1 + 2 // 1 + 2 is an expression : it returns a value. whole line is a statement/instruction
  println(x)

  println(1 == x)

  val aCondition = true
  val aConditionedValue = if (aCondition) 5 else 3 //IF in scala is an expression
  println(aConditionedValue)


  //code blocks
  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }

  val z = "Hello"
  println(z)
}
