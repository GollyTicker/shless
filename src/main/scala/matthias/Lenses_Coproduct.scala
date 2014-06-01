package matthias

import shapeless._
import record.RecordType
import syntax.singleton._
import union._

/**
 * Created by sacry on 30/05/14.
 */
class Lenses_Coproduct {

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

object CoproductExample extends App {

  object size extends Poly1 {
    implicit def caseInt = at[Int](i => (i, i))

    implicit def caseString = at[String](s => (s, s.length))

    implicit def caseBoolean = at[Boolean](b => (b, 1))
  }

  type ISB = Int :+: String :+: Boolean :+: CNil
  val str = Coproduct[ISB]("foo")
  val bool = Coproduct[ISB](true)

  println(s"Has Int? ${str.select[Int]}")
  println(s"Has String? ${str.select[String]}")

  val strs = str map size
  println(strs.select[(String, Int)])

  val bools = bool map size
  println(bools.select[(Boolean, Int)])

  val uSchema = RecordType.like('i ->> 23 :: 's ->> "foo" :: 'b ->> true :: HNil)
  type U = uSchema.Union
  val u = Coproduct[U]('s ->> "foo")
  println(u.get('i))
  println(u.get('s))
}