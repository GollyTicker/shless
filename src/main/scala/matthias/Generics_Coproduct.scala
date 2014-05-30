package matthias

import shapeless.Generic
import shapeless.HNil

/**
 * Created by sacry on 30/05/14.
 */
class Generics_Coproduct {

}


object GenericsExample extends App {

  case class Address(street: String, city: String, postcode: String)

  case class Person(fn: String, ln: String, age: Int, address: Address, job: String)

  val personGen = Generic[Person]

  val john = Person("John", "Doe", 25, Address("695 Park Ave", "New York, NY", "10065"), "Software Engineer")

  println(s"Person to HList: ${personGen.to(john)}")

  val another_john = "John" :: "Doe" :: 25 ::
    Address("695 Park Ave", "New York, NY", "10065") :: "Software Engineer" :: HNil

  println(s"HList to Person: ${personGen.from(another_john)}")
  println(s"is Equal: ${personGen.from(another_john) == personGen.from(personGen.to(john))}")
}

object CoproductExample extends App {
  println("No CoproductExample yet")
}