
import shapeless.HNil
import shapeless.lens
import shapeless.syntax.singleton._
import shapeless.record._
import shapeless.Generic

/**
 * Created by sacry on 15/05/14.
 */
object RecordSyntax {

  val books = List(
    ("author" ->> "Benjamin Pierce") ::
      ("title" ->> "Types and Programming Languages") ::
      ("id" ->> 262162091) ::
      ("price" ->> 44.11) ::
      HNil,
    ("author" ->> "William Fuckspear") ::
      ("title" ->> "Types and moare Types") ::
      ("id" ->> 262162091) ::
      ("price" ->> 70.48) ::
      HNil,
    ("author" ->> "Peter Peterson") ::
      ("title" ->> "Love Type n Stuff - Peace!") ::
      ("id" ->> 262162090) ::
      ("price" ->> 100.90) ::
      HNil
  )

  val languages = List(
    ("lang" ->> "Scala") ::
      ("typed" ->> "Static") ::
      HNil,
    ("lang" ->> "Haskell") ::
      ("typed" ->> "Static") ::
      HNil,
    ("lang" ->> "Python") ::
      ("typed" ->> "dynamic") ::
      HNil
  )

  val stuff = List(
    ("books" ->> books) ::
      ("languages" ->> languages) ::
      HNil
  )

  val book =
    ("author" ->> "Benjamin Pierce") ::
      ("title" ->> "Types and Programming Languages") ::
      ("id" ->> 262162091) ::
      ("price" ->> 44.11) ::
      HNil

  def main(args: Array[String]) {
    println(book.get("author"))
    println(books.map(_.get("author")))
    val r = stuff.map(record =>
      record.get("books").map(subrec => subrec.get("title"))
        .zip(
          record.get("languages").map(subrec => subrec.get("lang"))
        )
      ).flatten
    println(r)
  }

}

object LenseSyntax {

  // A pair of ordinary case classes ...
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
  val salaryLens = lens[Person] >> 'job >> 'salary
  val hourlyPaymentLens = lens[Person] >> 'job >> 'salary >> 'hourly

  val personGen = Generic[Person]

  def main(args: Array[String]) {

    val matze = Person("matze", 26,
      Address("Supermanstr 77", "Hamburg", "22047"),
      Job("Software Engineer", Salary(30)))
    println(nameLens.get(matze))
    println(postcodeLens.get(matze))
    println(hourlyPaymentLens.get(matze))
    println(salaryLens.set(matze)(Salary(100)))
    println(personGen.to(matze))
    println(personGen.from(personGen.to(matze)))
  }
}
