Shapeless Features
==================
by Matthias Nitsche and Swaneet Sahoo <br><br>

**Table of content**

 1. Coproducts and discriminated unions
 2. Automatic instance Derivation
 3. Heterogenous lists, HTuple HMaps
 4. Singleton Types, Generics, Records and Lenses
<br>
<br>

**Coproducts and discriminated unions**
```scala
val t = List(1,2,3,4).map { case _ => x + 1 }
```
**Automatic instance Derivation**
```scala
val t = List(1,2,3,"4").map { 
    case x: Int => x + 1 
    case _ => x.toInt
}
```
**Heterogenous lists, HTuple HMaps**
```scala
val t = List(1,2,3,4).map { case _ => x + 1 }
```
**Singleton Types, Generics, Records and Lenses**
```scala
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
    // all This because of immutability, just got more money. Whats the deal?
    val matze_cluttered = Person("matze_cluttered", 26,
      Address("Supermanstr 77", "Hamburg", "22047"),
      Job("Software Engineer", Salary(30)))
    println("Matze cluttered: " + matze_cluttered)
    // to
    val matze_uncluttered = salaryLens.set(matze)(Salary(40))
    println("Matze uncluttered: " + matze_uncluttered) // yeahy!!
    // basic working with Matze
    println(nameLens.get(matze))
    println(postcodeLens.get(matze))
    println(hourlyPaymentLens.get(matze))
    println(salaryLens.set(matze)(Salary(100)))
    println(personGen.to(matze))
    println(personGen.from(personGen.to(matze)))
  }
}
```
