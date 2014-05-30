package matthias

import shapeless._

/**
 * Created by sacry on 30/05/14.
 */
class Lenses_LabelledGenerics_Zipper {

}


object LensesExample extends App {

  case class Address(street: String, city: String, postcode: String)

  case class Salary(hourly: Int)

  case class Job(name: String, salary: Salary)

  case class Person(name: String, age: Int, address: Address, job: Job)

  // Some lenses over Person/Address ...
  val nameLens = lens[Person] >> 'name
  val ageLens = lens[Person] >> 'age
  val addressLens = lens[Person] >> 'address
  val streetLens = lens[Person] >> 'address >> 'street
  val cityLens = lens[Person] >> 'address >> 'city
  val postcodeLens = lens[Person] >> 'address >> 'postcode

  // Some lenses over Person/Job ...
  val jobLens = lens[Person] >> 'job
  val jobNameLens = lens[Person] >> 'job >> 'name
  val salaryLens = lens[Person] >> 'job >> 'salary >> 'hourly

  val matze = Person("matze", 26,
    Address("Supermanstr 77", "Hamburg", "22047"),
    Job("Software Engineer", Salary(30)))

  // all This because of immutability, just got more money. Whats the deal?
  val matze_cluttered = Person("matze", 26,
    Address("Supermanstr 77", "Hamburg", "22047"),
    Job("Software Engineer", Salary(50)))
  println(s"Matze cluttered: ${matze_cluttered}")
  println(s"Matze salary cluttered: ${matze_cluttered.job.salary.hourly}")

  val matze_uncluttered = salaryLens.set(matze)(100) // he gets 100, much better!
  println("Matze uncluttered: " + matze_uncluttered) // yeahy!!
  println("Matze salary uncluttered: " + salaryLens.get(matze_uncluttered))

}

object LabelledGenericsExample extends App {
  println("No LabelledGenericsExample yet")
}

object ZipperExample extends App {
  println("No ZipperExample yet")
}