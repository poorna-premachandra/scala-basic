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

  object CompareByName extends Equal[User] {
    override def apply(user1: User, user2: User): Boolean = user1.name.equals(user2.name)
  }

  object CompareByNameAndEmail extends Equal[User] {
    override def apply(user1: User, user2: User): Boolean = user1.name.equals(user2.name) && user1.email.equals(user2.email)
  }

  val mary = User("Mary", 25, "Mary@gmail.com")

  println(CompareByName(john, mary))
  println(CompareByName(john, john))
}
