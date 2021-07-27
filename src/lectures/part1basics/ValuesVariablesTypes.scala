package lectures.part1basics

object ValuesVariablesTypes extends App {
  val x: Int = 42 //val is similar to final in Java but not exactly final       Long is 4 Bytes
  println(x)

  //x=2    <- this won't work as val are immutable

  val y = 42
  println(y) //also works, type of val are optional because compile automatically infers the type

  val aString : String = "Hello" //not need semi-colons as we write each statement in a new line, if not we need them to separate the statements
  println(aString)

  val aBoolean : Boolean = false

  val aChar: Char = 'a'

  val aShort : Short = 32767  //2 Bytes

  val aLong : Long = 9223372036854775807L  //8 Bytes


  val aFloat : Float = 2.0f

  val aDouble : Double = 3.14

  //variables
  var aVariables : Int = 4
  aVariables = 5 //In functional programming variables are also know as "side effects"

}
