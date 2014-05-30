package matthias

import shapeless.HNil
import shapeless.syntax.singleton._
import shapeless.record._

/**
 * Created by sacry on 30/05/14.
 */


class Singletons_Records {

}

object SingletionsExample extends App {
  println("No SingletionsExample yet")
}

object RecordExample extends App {

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