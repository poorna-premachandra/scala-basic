package lectures.part2oop

object Exceptions extends App {

  //exceptions are instances of classes
  //to be throwable a class should extends the Throwable class

//  val aWeirdValue: String = throw new NullPointerException  //throwing exception returns Nothing (return type of aWeirdValue is Nothing)


  def getInt(withException: Boolean): Int =
    if (withException) throw new RuntimeException("No Int for you!")
    else 42

  val potentialFail = try {   // potentialFail return type is anyVal because try block returns int and catch block returns unit. It doesn't consider the finally block
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught a Runtime Exception")
  } finally { //use finally block only for side effects
    println("finally")
  }


  //custom exceptions
  class MyException extends Exception
  val customException = new Exception

  throw customException //since Exception class extends Throwable class

}
