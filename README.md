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
// Higher Rank Functions
object choose extends (List ~> Option) {
  def apply[A](s : List[A]) = s.headOption
}

scala> choose(List(1, 2, 3))
res0: Option[Int] = Some(1)
```
```scala
// constrainted polymorphic function - Implementation
object reverse extends Poly1 {
  import Integer.parseInt
  implicit def revInt = at[Int](x => parseInt(x.toString().reverse))
  
  implicit def revString = at[String](_.reverse)

  implicit def revList[A](implicit revA: Case.Aux[A, A])
    = at[List[A]](ls => (ls map (reverse(_)) reverse))
  
  implicit def revTuple[A, B]
    (implicit revA: Case.Aux[A, A], revB: Case.Aux[B, B])
    = at[(A, B)]{ case (a, b) => (reverse(b), reverse(a)) }
  }
```
```scala
// constrainted polymorphic function - Beispiele
scala> reverse(324)
423
scala> reverse("12345")
54321
scala> reverse(List(2, 3, 5, 63))
List(36, 5, 3, 2)
scala> reverse(List("2", "3", "5", "63"))
List(36, 5, 3, 2)
scala> reverse( ("abedc", 123) )
(321,cdeba)

reverse(true)   // compiliert nicht
reverse(Set(1,2,4))   // compiliert nicht
```
**Heterogenous lists**
```scala
scala> myHlist = 23 :: "foo" :: true :: List('b', 'a', 'r') :: "BAR" :: HNil
myHList: Int :: String :: Boolean :: List[Char] :: String :: HNil
```

```scala
// Array like access
scala> myHlist(0)
res0: Int = 23
scala> myHlist(1)
res0: String = "foo"

// Tuple like types
scala> if (myHlist(0) < 26 && myHlist(2)) myHlist(1) else "baz"
res0: String = foo

// List like operations
scala> myHlist.tail
foo :: true :: List(b, a, r) :: BAR :: HNil
scala> (14 :: 23 :: "sdfsdf" :: HNil) map reverse
41 :: 32 :: fdsfds :: HNil
scala> myHlist.filter[String]
foo :: BAR :: HNil
```
```scala
val fileSystem =
  "/" ::
  (
    ("Swaneet" :: ("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) :: HNil) ::
    ("Matze" :: ("shapeless-präzi.keynote" :: "passwords.txt" :: HNil) :: HNil ) ::
    "root-password.txt" ::
    HNil
  ) :: HNil

scala> fileSystem.toZipper.right.down.get
"Swaneet" :: ("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) :: HNil

scala> fileSystem.toZipper.right.down.down.right.insert("plans.txt").root.reify
"/" ::
(
    ("Swaneet" :: ("plans.txt" :: "shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) :: HNil) ::
    ("Matze" :: ("shapeless-präzi.keynote" :: "passwords.txt" :: HNil) :: HNil ) ::
    "root-password.txt" ::
    HNil
) :: HNil

```
**Singleton Types**
```scala
val ls = "Hallo" :: 5 :: true :: HNil

scala> ls(0)
res0: String = Hallo
scala> ls(1)
res0: Int = 5
```
```scala
// Examples
scala> foo(1)
res0: List[String] = List(ABC, DEF)
scala> foo(0)
res0: Int = 5
scala> foo(0) + 6
res0: Int = 11

foo(7)   // compiliert nicht
foo(1) + 2   // compiliert nicht
```
```scala
// Implementation
scala> val wt0 = Witness(0)
wt0: Witness{type T = Int(0)}

scala> val wt1 = Witness(1)
wt1: Witness{type T = Int(1)}

trait Foo[In] {
    type Out
    def out: Out
}

implicit val ap0 =
  new Foo[wt0.T] {
      type Out = Int
      val out = 5
}

implicit val ap1 =
  new Foo[wt1.T] {
  type Out = List[String]
  val out = List("ABC","DEF")
}
  
def foo(wt: WitnessWith[Foo]): wt.Out =
  wt.instance.out.asInstanceOf[wt.Out]
```
**Extensible Records**
```scala
case class Book(title: String, author: String, price: Double)

val books = List(
  "title" ->> "Types and Programming Languages" ::
    "author" ->> "Benjamin Pierce" :: "price" ->> 44.95 :: HNil, 
  
  "title" ->> "The Tragedy of Scala and Java" ::
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
) // add the last name of the author (by splitting the author field) and remove the author field
```
**Generics and Labelled Generics**
```scala
------ Generics -----
case class Person(name: String, address: Address, job: String)

case class Address(street: String, city: String, postcode: String)

val personGen = Generic[Person]

val john = Person("John", Address("695 Park Ave", "New York, NY", "10065"), "Software Engineer")

scala> personGen.to(john)
res = "John" :: Address("695 Park Ave", "New York, NY", "10065") :: "Software Engineer" :: HNil

scala> personGen.from(res) // Person
scala> personGen.from(res) == personGen.from(personGen.to(john)) // True

------ Labelled Generics -----
case class Book(author: String, title: String, id: Int, price: Double)

case class ExtendedBook(author: String, title: String, id: Int, price: Double, inPrint: Boolean)

val bookGen = LabelledGeneric[Book]
val bookExtGen = LabelledGeneric[ExtendedBook]

val aBook = Book("MrGeneric", "About Types", 262162, 44.11)

val res = bookGen.to(aBook) // HList with Records/Labels
// 'name ->> "MrGeneric" ::  'title ->> "About Types" :: 'id ->> 262162 :: 'price ->> 44.11 :: HNil

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

---- Lenses ----
case class Salary(hourly: Int, bonus: Int)

case class Job(name: String, salary: Salary)

case class Person(name: String, job: Job)

val hourlyLens = lens[Person].job.salary.hourly // two ways of access
val bonusLens = lens[Person] >> 'job >> 'salary >> 'bonus
val salaryLens = hourlyLens ~ bonusLens // Composition

val john = Person("John", Job("Software Engineer", Salary(30, 0)))

val newJohn = hourlyLens.set(john)(100)
hourlyLens.get(newJohn) // hourly payment = 100
hourlyLens.modify(newJohn)(_ + 5) // hourly payment = 105

val (hourly, bonus) = salaryLens.get(newJohn)
val anotherJohn = salaryLens.set(newJohn)(100, 50) 
// Person("John", Job("Software Engineer", Salary(100, 50)))
```
