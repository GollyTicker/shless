

/**
 * Created by Swaneet on 15.05.2014.
 */

import shapeless._


object blubb {

  class BiMapIS[K, V]

  implicit val intToString = new BiMapIS[Int, String]
  implicit val stringToInt = new BiMapIS[String, Int]

  def A() = {
    println("sdfsdf")

    val l = 123 :: "sdf" :: HNil

    import poly.identity

    val t = l map identity

    val t2 = ((23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil) flatMap identity

    println(l)
    println(t)
    println(t2)

  }

  //val hm = HMap[BiMapIS](23 -> "foo", "bar" -> 13)
  //shapeless.HMap[BiMapIS] = shapeless.HMap@ff95e1


  /*val ls = 23 :: "bar" :: HNil
  //l: shapeless.::[Int,shapeless.::[String,shapeless.HNil]] = 23 :: bar :: HNil

  import hm._

  val swapped = ls map hm*/
}
