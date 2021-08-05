package bestpractices

//Avoiding Diamond problem

trait Languages {
  def lang(): Unit
}

trait Functional extends Languages { //trait can only extends trait
  override def lang(): Unit = {
    println("Functional")
  }
}

trait ObjectOriented extends Languages {
  override def lang(): Unit = {
    println("ObjectOriented")
  }
}

object DiamondProblem extends App {
  //If there are multiple implementors of a given member, the implementation in the super type that is furthest to the right (in the list of super types) wins.

  class Scala extends ObjectOriented with Functional
  (new Scala).lang()  //prints Functional

  class Java extends Functional with ObjectOriented
  new Java().lang() //prints ObjectOriented
}
