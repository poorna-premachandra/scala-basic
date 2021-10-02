package lectures.part8implicits

object TypeClasses extends App {

// PART 1 - type classes. without using implicits..
  case class User(name: String, age: Int, email: String)

  trait HTMLSerializer[T] { //type class
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] { //type class instance - normally defined as a singleton object
    override def serialize(user: User): String = s"<div>${user.name} (${user.age}y old) <a href=${user.email}/> </div>"
  }

  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name}</div>"
  }

  val john = User("John", 25, "John@gmail.com")
  println(UserSerializer.serialize(john)) //NOTE : here we serialize by specifically referring the serializer(here, we do not use implicits)

  //we can define serializers for other types
  //we can also define multiple serializers for a same type

  //EXERCISE
  //Implement Equal type class that has a method 'equal' that can compare two values

  trait Equal[T] {
    def apply(value1: T, value2: T): Boolean
  }

  implicit object CompareByName extends Equal[User] {
    override def apply(user1: User, user2: User): Boolean = user1.name.equals(user2.name)
  }

  object CompareByNameAndEmail extends Equal[User] {
    override def apply(user1: User, user2: User): Boolean = user1.name.equals(user2.name) && user1.email.equals(user2.email)
  }

  val mary = User("Mary", 25, "Mary@gmail.com")

  println(CompareByName(john, mary))
  println(CompareByName(john, john))






  // PART 2 - using implicit methods
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer //better alternative
  }

  implicit object StringSerializer extends HTMLSerializer[String] {
    override def serialize(value: String): String = s"<div>Serialized String : $value </div>"
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div>Serialized Int : $value</div>"
  }

  println(HTMLSerializer.serialize(42)) //NOTE : here we serialize by referring the base serializer
  println(HTMLSerializer.serialize("bob"))

  //better alternative - have access to the type class
  println(HTMLSerializer[Int].serialize(42))

  //EXERCISE : implement TC(Type Class) pattern for Equality example
  object Equal {
    def apply[T](value1: T, value2: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value1, value2) //make only one method implicit from the above two methods
  }
  println(Equal(john, mary))
  // This is called AD-HOC polymorphism






  // PART 3 - using implicit classes
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }
  /*
    - extend to new types
    - choose the implementation
    - super expressive
   */

  println(2.toHTML) //NOTE : here we serialize but not referring any serializer
  println(john.toHTML) //println(new HTMLEnrichment[User](john).toHTML(UserSerialize))
  println(john.toHTML(PartialUserSerializer))

  /*
  EXERCISE : improve the equal TC with an implicit conversion class
  ===(anotherValue: T)
  !==(anotherValue: T)
   */

  implicit class EqualEnrichment[T](value1: T) {
    def ===(value2: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value1, value2)
    def !==(value2: T)(implicit equalizer: Equal[T]): Boolean = !equalizer.apply(value1, value2)
  }

  println(john === mary)
  println(john === john)


  //context bounds
  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String = s"<html><body> ${content.toHTML(serializer)} </body></html>"

  //can re-write above as follows
  def htmlSugar[T : HTMLSerializer](content: T): String = s"<html><body> ${content.toHTML} </body></html>"
  //"[T: HTMLSerializer]" is a context bound. Here, compiler will inject the serializer for us.


  //implicitly keyword
  //this can be used to get the default implicit defined
  def htmlSugar[T: HTMLSerializer](content: T): String = {
    val serializer = implicitly[HTMLSerializer[T]]
    s"<html><body> ${content.toHTML(serializer)} </body></html>"
  }
}
