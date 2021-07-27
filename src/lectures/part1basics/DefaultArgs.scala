package lectures.part1basics

object DefaultArgs extends App {


  def tailRecFactorial(x: Int, accumulator: BigInt = 1): BigInt = { // default value is set for accumulator
    if (x <= 1) accumulator
    else tailRecFactorial(x - 1, x * accumulator)
  }
  println(tailRecFactorial(5))

  def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("Saving Picture")
  savePicture(height = 600, width = 800)  // we tell compiler what param we want to pass in by mentioning with name, also argument order doesn’t matter

}


