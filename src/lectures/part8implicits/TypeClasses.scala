package lectures.part8implicits

object TypeClasses extends App {

  case class User(name: String, age: Int, email: String)

  trait HTMLSerializer[T] { //type class
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] { //type class instance - normally defined as a singleton object
    override def serialize(user: User): String = s"<div>${user.name} (${user.age}y old) <a href=${user.email}/> </div>"
  }

  val john = User("John", 25, "John@gmail.com")
  println(UserSerializer.serialize(john))

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

  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize("bob"))

  //better alternative - have access to the type class
  println(HTMLSerializer[Int].serialize(42))

  //EXERCISE : implement TC(Type Class) pattern for Equality example
  object Equal {
    def apply[T](value1: T, value2: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value1, value2) //make only one method implicit from the above two methods
  }
  println(Equal(john, mary))
  // This is called AD-HOC polymorphism
}
