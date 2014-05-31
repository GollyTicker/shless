package matthias

import shapeless._
import shapeless.Generic
import shapeless.LabelledGeneric
import shapeless.record._
import syntax.singleton._

/**
 * Created by sacry on 30/05/14.
 */
class Lenses_LabelledGenerics {

}


object LensesExample extends App {

  case class Address(street: String, city: String, postcode: String)

  case class Salary(hourly: Int, bonus: Int = 0)

  case class Job(name: String, salary: Salary)

  case class Person(name: String, age: Int, address: Address, job: Job)

  val nameLens = lens[Person] >> 'name
  val ageLens = lens[Person] >> 'age
  val addressLens = lens[Person] >> 'address
  val streetLens = lens[Person] >> 'address >> 'street
  val cityLens = lens[Person] >> 'address >> 'city
  val postcodeLens = lens[Person] >> 'address >> 'postcode

  val jobLens = lens[Person] >> 'job
  val jobNameLens = lens[Person] >> 'job >> 'name
  val hourlyLens = lens[Person] >> 'job >> 'salary >> 'hourly
  val bonusLens = lens[Person] >> 'job >> 'salary >> 'bonus
  val salaryLens = hourlyLens ~ bonusLens

  val john = Person("John", 25,
    Address("695 Park Ave", "New York, NY", "10065"),
    Job("Software Engineer", Salary(30)))

  val normal = Person("John", 25,
    Address("695 Park Ave", "New York, NY", "10065"),
    Job("Software Engineer", Salary(50)))
  println(s"normal: ${normal}")
  println(s"normal salary: ${normal.job.salary.hourly}")

  val lensesStyle = hourlyLens.set(john)(100)
  println(s"lenses: ${lensesStyle}")
  println(s"get salary: ${hourlyLens.get(lensesStyle)}")
  println(s"Modify salary: ${hourlyLens.modify(lensesStyle)(_ + 5)}")

  println(s"connect lenses get: ${salaryLens.get(lensesStyle)}")
  println(s"connect lenses set: ${salaryLens.set(lensesStyle)(100, 50)}")
}

object LabelledGenericsExample extends App {

  case class Book(author: String, title: String, id: Int, price: Double)

  case class ExtendedBook(author: String, title: String, id: Int, price: Double, inPrint: Boolean)

  val bookGen = LabelledGeneric[Book]
  val bookExtGen = LabelledGeneric[ExtendedBook]

  val tapl = Book("Benjamin Pierce", "Types and Programming Languages", 262162091, 44.11)

  val rec = bookGen.to(tapl)

  println(s"Access the price: ${rec('price)}")

  println(s"Update the price: ${bookGen.from(rec.updateWith('price)(_ + 2.0))}")

  val extBook = bookExtGen.from(rec + ('inPrint ->> true))
  println(s"Normal to extended: ${extBook}")

}