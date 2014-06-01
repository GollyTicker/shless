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
// Implementation
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
// Beispiele
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
scala> (14 :: 23 :: "sdfsdf" :: HNil) map reverse
41 :: 32 :: fdsfds :: HNil

import shapeless.test.illTyped
illTyped("reverse(true)")
illTyped("reverse(Set(1,2,4))")
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
val fileSystem =
  "/" ::
  (
    ("Swaneet" :: ("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) :: HNil) ::
    ("Matze" :: ("shapeless-präzi.keynote" :: "passwords.txt" :: HNil) :: HNil ) ::
    "root-password.txt" ::
    HNil
  ) :: HNil

scala> fileSystem.toZipper.right.down.get
"Swaneet" ::
("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) ::
HNil

scala> fileSystem.toZipper.right.down.down.right.insert("airbnb-plans.txt").root.reify
"/" ::
(
    ("Swaneet" :: ("airbnb-plans.txt" :: "shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) :: HNil) ::
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

illTyped("foo(7)")
illTyped("foo(1) + 2")
```
```scala
// Implementation
scala> val wt0 = Witness(0)
wt0: Witness{type T = Int(0)}

scala> val wt1 = Witness(1)
wt1: Witness{type T = Int(1)}

trait Foo[In] {type Out; def out: Out}
implicit val ap0 =
  new Foo[wt0.T] {type Out = Int; val out = 5 }
implicit val ap1 =
  new Foo[wt1.T] {type Out = List[String]; val out = List("ABC","DEF") }

def foo(wt: WitnessWith[Foo]): wt.Out =
  wt.instance.out.asInstanceOf[wt.Out]
```
**Extensible Records**
```scala
object RecordExample extends App {

  def printTitle(msg: String) = {
    println(s"\n${"-" * 10} ${msg} ${"-" * 10}")
  }

  def printRecord[T](record: List[T]) {
    for (e <- record)
      println(e)
  }

  case class Book(id: Int, title: String, author: String, price: Double)

  val books = List(
    "id" ->> 262162091 :: "title" ->> "Types and Programming Languages" ::
      "author" ->> "Benjamin Pierce" :: "price" ->> 44.95 :: HNil,
    "id" ->> 262162092 :: "title" ->> "Love Type n Stuff - Peace!" ::
      "author" ->> "Peter Peace" :: "price" ->> 104.11 :: HNil,
    "id" ->> 262162093 :: "title" ->> "The Tragedy of Scala and Java" ::
      "author" ->> "Java Scalaspeare" :: "price" ->> 12.12 :: HNil
  )

  printTitle("two records")

  println("r2: " + books(1))
  println("r1: " + books(0))

  printTitle("update a record")

  val newPrice = books(1).get("price") + 2.0
  val updateR2 = books(1) + ("price" ->> newPrice)
  println(s"price: ${updateR2("price")}")

  printTitle("extend a record")

  val extended = ("id" ->> 262162091 :: "title" ->> "This is quite nice!" ::
    "author" ->> "Nimrod Nice" :: "price" ->> 11.11 :: HNil) :: books
  printRecord(extended)

  printTitle("remove a field from one record")

  val removeField = extended(0) - "price"
  println(removeField)

  printTitle("add a field from all records")

  val addFields = extended map (p => p + ("inPrint" ->> true))
  printRecord(addFields)

  printTitle("remove a field from all records")

  val removeFields = addFields map (p => p - "inPrint")
  printRecord(removeFields)
}
```
**Generics and Labelled Generics**
```scala
Some(None)
```
**Lenses and Coproducts**
```scala
Some(None)
```
