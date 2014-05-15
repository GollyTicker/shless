

/**
 * Created by Swaneet on 15.05.2014.
 */

import shapeless._
import shapeless.syntax.singleton._
import shapeless.record._


object blubb {

  class BiMapIS[K, V]

  implicit val intToString = new BiMapIS[Int, String]
  implicit val stringToInt = new BiMapIS[String, Int]


  val l = 123 :: "sdf" :: HNil

  import poly.identity

  val t = l map identity

  val t2 = ((23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil) flatMap identity

  def A() = {
    println("sdfsdf")

    println(l)
    println(t)
    println(t2)

    //val ls =

  }

  val keytag = ("person" ->> "matze")

  val book =
    ("author" ->> "Benjamin Pierce") ::
      ("title"  ->> "Types and Programming Languages") ::
      ("id"     ->>  262162091) ::
      ("price"  ->>  44.11) ::
      HNil

  //val hm = HMap[BiMapIS](23 -> "foo", "bar" -> 13)
  //shapeless.HMap[BiMapIS] = shapeless.HMap@ff95e1


  /*val ls = 23 :: "bar" :: HNil
  //l: shapeless.::[Int,shapeless.::[String,shapeless.HNil]] = 23 :: bar :: HNil

  import hm._

  val swapped = ls map hm*/


  val int23 = 23.narrow

  val (wTrue, wFalse) = (Witness(true), Witness(false))

  type True = wTrue.T

  type False= wFalse.T

  trait Select[B] { type Out }
  def select[T](b: WitnessWith[Select])(t: b.Out) = t


}
