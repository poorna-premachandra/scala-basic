package lectures.part6afp

object PartialFunctions extends App {

  val aFullFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new Exception //can be a custom exception created by us

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } //this is called "partial function value/literal"

  println(aPartialFunction(2))
  //println(aPartialFunction(10)) //match error exception



  //PF utilities

  //1. check whether value is present
  println(aPartialFunction.isDefinedAt(67))

  //2. PFs can be lifted to full functions to return Option
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(99))

  //3. chaining multiple PFs
  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  //4. PFs extends normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  } // this is a partial function literal (because PF is a subtype of total functions)

  // HOFs accepts PF as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 43
    case 2 => 78
    case 3 => 100
  }

  //NOTE : PF can have only one param type


  //Exercise

  //construct a PF instance MANUALLY using anonymous class
  val aPartialFunctionManual = new PartialFunction[Int, Int] {
    //have to override below two functions
    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 65
      case 5 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 | x == 2 | x == 5
  }

  //Implement a chatbot that uses a partial function to respond
  val chatbot: PartialFunction[String, String] = {
    case "hello" => "hi there"
    case "bye" => "goodbye, see you soon"
    case _ => "..."
  }

  //this is how to get std input
  scala.io.Source.stdin.getLines().foreach(line => println("chatbot says: " + chatbot(line)))
}
