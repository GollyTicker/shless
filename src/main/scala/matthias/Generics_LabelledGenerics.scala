package matthias

import shapeless._
import shapeless.PolyDefns.->
import shapeless.Generic
import shapeless.LabelledGeneric
import record._
import syntax.singleton._
import poly._

/**
 * Created by sacry on 30/05/14.
 */
class Generics_LabelledGenerics {

}


object GenericsExample1 extends App {

  object useCase1 {

    case class Address(street: String, city: String, postcode: String)

    case class Person(fn: String, ln: String, age: Int, address: Address, job: String)

    val personGen = Generic[Person]

    val john = Person("John", "Doe", 25, Address("695 Park Ave", "New York, NY", "10065"), "Software Engineer")

    println(s"Person to HList: ${personGen.to(john)}")

    val another_john = "John" :: "Doe" :: 25 ::
      Address("695 Park Ave", "New York, NY", "10065") :: "Software Engineer" :: HNil

    object inc extends ->((i: Int) => i + 1)

    println(s"everywhere on person: ${everywhere(inc)(john)}")

    println(s"HList to Person: ${personGen.from(another_john)}")
    println(s"is Equal: ${personGen.from(another_john) == personGen.from(personGen.to(john))}")
  }

}

object GenericsExample2 extends App {

  case class Company(depts: List[Dept])

  sealed trait Subunit

  case class Dept(name: Name, manager: Manager, subunits: List[Subunit]) extends Subunit

  case class Employee(person: Person, salary: Salary) extends Subunit

  case class Person(name: Name, address: Address)

  case class Salary(salary: Int)

  type Manager = Employee
  type Name = String
  type Address = String

  val beforeRaise  =
    Company(
      List(
        Dept("Research",
          Employee(Person("Ralf", "Amsterdam"), Salary(8000)),
          List(
            Employee(Person("Joost", "Amsterdam"), Salary(1000)),
            Employee(Person("Marlow", "Cambridge"), Salary(2000))
          )
        ),
        Dept("Strategy",
          Employee(Person("Blair", "London"), Salary(100000)),
          List()
        )
      )
    )

  object raise extends ->((i: Int) => i * 110 / 100)

  val afterRaise = everywhere(raise)(beforeRaise)
  println(afterRaise)
}

object LabelledGenericsExample extends App {

  case class Book(author: String, title: String, id: Int, price: Double)

  case class ExtendedBook(author: String, title: String, id: Int, price: Double, inPrint: Boolean)

  val bookGen = LabelledGeneric[Book]
  val bookExtGen = LabelledGeneric[ExtendedBook]

  val tapl = Book("Benjamin Pierce", "Types and Programming Languages", 262162091, 44.11)

  val res = bookGen.to(tapl)

  println(s"Access the price: ${res('price)}")

  println(s"Update the price: ${bookGen.from(res.updateWith('price)(_ + 2.0))}")

  val extBook = bookExtGen.from(res + ('inPrint ->> true))
  println(s"Normal to extended: ${extBook}")

}