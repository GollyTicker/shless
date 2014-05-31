package matthias

import shapeless._
import shapeless.PolyDefns.->
import record.RecordType
import syntax.singleton._
import union._

/**
 * Created by sacry on 30/05/14.
 */
class Generics_Coproduct {

}


object GenericsExample extends App {

  sealed trait Tree[T]

  case class Leaf[T](t: T) extends Tree[T]

  case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]

  object inc extends ->((i: Int) => i + 1)

  def inorderTreeMap[T, B](tree: Tree[T])(f: (T => B)): Tree[B] = tree match {
    case tree: Node[T] => Node(inorderTreeMap(tree.left)(f), inorderTreeMap(tree.right)(f))
    case tree: Leaf[T] => Leaf(f(tree.t))
  }

  val tree: Tree[Int] = Node(
    Node(Leaf(1), Leaf(3)),
    Node(Leaf(5), Leaf(6))
  )

  val every = everywhere(inc)(tree)
  val notOdd = inorderTreeMap(tree)(p => p + 1)
  println(notOdd)
  println(every)

  case class Address(street: String, city: String, postcode: String)

  case class Person(fn: String, ln: String, age: Int, address: Address, job: String)

  val personGen = Generic[Person]

  val john = Person("John", "Doe", 25, Address("695 Park Ave", "New York, NY", "10065"), "Software Engineer")

  println(s"Person to HList: ${personGen.to(john)}")

  val another_john = "John" :: "Doe" :: 25 ::
    Address("695 Park Ave", "New York, NY", "10065") :: "Software Engineer" :: HNil

  println(s"HList to Person: ${personGen.from(another_john)}")
  println(s"is Equal: ${personGen.from(another_john) == personGen.from(personGen.to(john))}")
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