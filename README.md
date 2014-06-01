Shapeless Features
==================
by Matthias Nitsche and Swaneet Sahoo <br><br>

**Table of content**

 1. Polymorphic Function Values
 2. Heterogenous lists
 3. Singleton Types
 4. Extensible Records
 5. Generics and Labelled Generics
 6. Lenses and Coproducts
<br>

**Polymorphic Function Values**
```scala
Some(None)
```
**Heterogenous lists**
```scala
scala> myHlist = 23 :: "foo" :: true :: List('b', 'a', 'r') :: "BAR" :: HNil
myHLsit: Int :: String :: Boolean :: List[Char] :: String :: HNil
```

```scala
// Array like access
scala> myHlist(0)
res0: Int = 23
scala> myHlist(1)
res0: String = 23

// Tuple like types
scala> if (myHlist(0) < 26 && myHlist(2)) myHlist(1) else "baz"
res0: String = foo

// List like operations
scala> myHlist.tail
foo :: true :: List(b, a, r) :: BAR :: HNil
scala> myHlist.filter[String]
foo :: BAR :: HNil
```
```scala
val swaneet =
  "Swaneet" ::
  ("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) ::
  HNil

val matze =
  "Matze" ::
  ("shapeless-präzi.keynote" :: "passwords.txt" :: HNil) ::
  HNil

val fileSystemOut =
  "/" ::
  (swaneet :: matze :: "root-password.txt" :: HNil) ::
  HNil

scala> fileSystem.toZipper.right.down.get
Swaneet ::
(shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil) ::
HNil

scala> fileSystemOut.toZipper.right.down.down.right.insert("airbnb-plans.txt").root.reify
/ ::
(
  ( 
    Swaneet ::
    (airbnb-plans.txt :: shapeless-präzi.tex ...) ::
    HNil
  ) ::
  (Matze :: (...) :: HNil) ::
  root-password.txt ::
  HNil
) ::
HNil

```
**Singleton Types**
```scala
Some(None)
```

**Extensible Records**
```scala
case class Book(id: Int, title: String, author: String, price: Double)

val books = List(
"id" ->> 262162091 :: "title" ->> "Types and Programming Languages" ::
  "author" ->> "Benjamin Pierce" :: "price" ->> 44.95 :: HNil,
"id" ->> 262162092 :: "title" ->> "Love Type n Stuff - Peace!" ::
  "author" ->> "Peter Peace" :: "price" ->> 104.11 :: HNil,
"id" ->> 262162093 :: "title" ->> "The Tragedy of Scala and Java" ::
  "author" ->> "Java Scalaspeare" :: "price" ->> 12.12 :: HNil
) // List[HList[records.Fields]]

val oldPrice = books(1).get("price") // 104.11
val updatePrice = books(1) + ("price" ->> oldPrice + 2.0) 
// books(1).get("price") = 106.11
  
val removeField = books(0) - "price" // a book without price

val addFields = books map (p => p + ("inPrint" ->> true)) 
// all records have the field inPrint = true

val removeFields = addFields map (p => p - "inPrint")
// remove inPrint from all records

val pricierItems = books.map(i => i + ("price" ->> i("price")*2.0))
// update all prices

val removeAuthorAddLastname = books.map(book => book 
  + ("lastname" ->> book("author").split("\\s+")(1))
  - "author"
) // add the last name of the author and remove the author field
```
**Generics and Labelled Generics**
```scala
------ Generics -----
case class Person(fn: String, ln: String, age: Int, address: Address, job: String)

case class Address(street: String, city: String, postcode: String)

val personGen = Generic[Person]

val john = Person("John", "Doe", 25, Address("695 Park Ave", "New York, NY", "10065"), "Software Engineer")

personGen.to(john) // HList

val another_john = "John" :: "Doe" :: 25 :: Address("695 Park Ave", "New York, NY", "10065") :: "Software Engineer" :: HNil

object inc extends ->((i: Int) => i + 1)
everywhere(inc)(john) // john.age = 26

personGen.from(another_john) // Person
personGen.from(another_john) == personGen.from(personGen.to(john)) // True

------ Labelled Generics -----
case class Book(author: String, title: String, id: Int, price: Double)

case class ExtendedBook(author: String, title: String, id: Int, price: Double, inPrint: Boolean)

val bookGen = LabelledGeneric[Book]
val bookExtGen = LabelledGeneric[ExtendedBook]

val aBook = Book("MrGeneric", "About Types", 262162, 44.11)

val res = bookGen.to(aBook) // HList with Records/Labels

res('price) // 44.11

bookGen.from(res.updateWith('price)(_ + 2.0))
// Book(MrGeneric, About Types, 262162, 46.11)

val extBook = bookExtGen.from(res + ('inPrint ->> true)) 
// Book to ExtendedBook with update
  
------ Everywhere -----
case class Company(depts: List[Dept])

sealed trait Sub

case class Dept(name: Name, manager: Manager, subunits: List[Sub]) extends Sub

case class Employee(person: Person, salary: Salary) extends Sub

case class Person(name: Name, address: Address)

case class Salary(salary: Int)

type Manager = Employee
type Name = String
type Address = String

val beforeRaise =
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

object raise extends ->((i: Salary) => Salary(i.salary * 110 / 100))

val afterRaise = everywhere(raise)(beforeRaise)
```
**Lenses and Coproducts**
```scala
---- Coproduct ----
object size extends Poly1 {
  implicit def caseInt = at[Int](i => (i, i))
  implicit def caseString = at[String](s => (s, s.length))
  implicit def caseBoolean = at[Boolean](b => (b, 1))
}

type ISB = Int :+: String :+: Boolean :+: CNil
val str = Coproduct[ISB]("foo")
val bool = Coproduct[ISB](true)

str.select[Int] // None
str.select[String] // Some("foo")

val strs = str map size
strs.select[(String, Int)] // Some(("foo", 3))

val bools = bool map size
bools.select[(Boolean, Int)] // Some((true, 1))

val uSchema = RecordType.like('i ->> 23 :: 's ->> "foo" :: 'b ->> true :: HNil)
type U = uSchema.Union
val u = Coproduct[U]('s ->> "foo")

u.get('i) // None
u.get('s) // Some("foo")

---- Lenses ----
case class Address(street: String, city: String, postcode: String)

case class Salary(hourly: Int, bonus: Int = 0)

case class Job(name: String, salary: Salary)

case class Person(name: String, age: Int, address: Address, job: Job)

val streetLens = lens[Person].address.street // two ways of access
val cityLens = lens[Person].address.city // this and..
val postcodeLens = lens[Person] >> 'address >> 'postcode // ..that

val jobLens = lens[Person] >> 'job
val jobNameLens = lens[Person] >> 'job >> 'name
val hourlyLens = lens[Person] >> 'job >> 'salary >> 'hourly
val bonusLens = lens[Person] >> 'job >> 'salary >> 'bonus
val salaryLens = hourlyLens ~ bonusLens // Composition

val john = Person("John", 25,
Address("695 Park Ave", "New York, NY", "10065"),
Job("Software Engineer", Salary(30)))

val newJohn = hourlyLens.set(john)(100)
hourlyLens.get(newJohn) // hourly payment = 100
hourlyLens.modify(newJohn)(_ + 5) // hourly payment = 105

val (hourly, bonus) = salaryLens.get(lensesStyle)
val anotherJohn = salaryLens.set(lensesStyle)(100, 50) 
// anotherJohn with Salary(100, 50)
```
