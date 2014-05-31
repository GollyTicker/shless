package matthias

import shapeless.{LabelledGeneric, syntax, HNil}
import shapeless.record._
import syntax.singleton._

/**
 * Created by sacry on 30/05/14.
 */


class Singletons_Records {

}

object SingletionsExample extends App {

}


object RecordExample extends App {

  def printRecord(record: shapeless.HList) {
    for (e <- record.toList)
      println(e)
  }

  case class Book(id: Int, title: String, author: String, price: Double)

  val books =
    ("r1" ->> ("id" ->> 262162091 :: "title" ->> "Types and Programming Languages" ::
      "author" ->> "Benjamin Pierce" :: "price" ->> 44.95 :: HNil)) ::
      ("r2" ->> ("id" ->> 262162092 :: "title" ->> "Love Type n Stuff - Peace!" ::
        "author" ->> "Peter Peace" :: "price" ->> 104.11 :: HNil)) ::
      ("r3" ->> ("id" ->> 262162093 :: "title" ->> "The Tragedy of Scala and Java" ::
        "author" ->> "William Shakespeare" :: "price" ->> 12.12 :: HNil)) ::
      HNil

  println("r2: " + books.get("r2"))
  println("r1: " + books.get("r1"))

  println("-" * 50)

  val newPrice = books.get("r2").get("price") + 2.0
  val updateR2 = books.get("r2") + ("price" ->> newPrice)
  println(updateR2("price"))

  println("-" * 50)

  val extended = books + ("r4" ->> ("id" ->> 262162091 :: "title" ->> "This is quite nice!" ::
    "author" ->> "Nimrod Nice" :: "price" ->> 11.11 :: HNil))
  printRecord(extended)

  println("-" * 50)

  val removeRecord = extended - "r4"
  printRecord(removeRecord)
}