package lectures.part2oop

//packages mirrors the file structure of the project


// import  playground._                                       -> to import all classes
// import  playground.(ClassOne, ClassTwo)                    -> select and import multiple
// import  playground.(ClassOne, ClassTwo => SecondClass)     -> give an alias to import(useful for importing classes with same name but from different packages)

object PackagingAndImports extends App {

  //members within the package are visible. Can refer to them directly using their name.

  //package object(rarely used in practice)
  sayHello
  println(SPEED_OF_LIGHT)
}

//default imports
//java.lang - String, Object, Exception
//scala - Int, Nothing, Function
//scala.Predef - println, ???