Shapeless Features
==================
by Matthias Nitsche and Swaneet Sahoo <br><br>

**Table of content**

 1. Heterogenous lists, HTuple HMaps
 2. Polymorphic Function Values
 3. Singletons and Extensible Records
 4. Generics and Labelled Generics
 5. Lenses and Coproducts
<br>
<br>
**Heterogenous lists, HTuple HMaps**
```scala
Some(None)
```
**Polymorphic Function Values**
```scala
Some(None)
```
**Singletons**
```scala
Some(None)
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
