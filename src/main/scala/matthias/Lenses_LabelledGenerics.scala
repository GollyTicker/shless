package matthias

import shapeless._
import shapeless.Generic

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
  val salaryLens = lens[Person] >> 'job >> 'salary >> 'hourly
  val bonusLens = lens[Person] >> 'job >> 'salary

  val john = Person("John", 25,
    Address("695 Park Ave", "New York, NY", "10065"),
    Job("Software Engineer", Salary(30)))

  val normal = Person("John", 25,
    Address("695 Park Ave", "New York, NY", "10065"),
    Job("Software Engineer", Salary(50)))
  println(s"Matze cluttered: ${normal}")
  println(s"Matze salary cluttered: ${normal.job.salary.hourly}")

  val lensesStyle = salaryLens.set(john)(100)
  println(s"Matze uncluttered: ${lensesStyle}")
  println(s"Matze salary uncluttered: ${salaryLens.get(lensesStyle)}")
  println(s"Modify salary: ${salaryLens.modify(lensesStyle)(_ + 5)}")

}

object LabelledGenericsExample extends App {
  println("No LabelledGenericsExample yet")
}