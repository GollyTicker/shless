

/**
 * Created by Swaneet on 17.05.2014.
 */

import shapeless._
import shapeless.poly._

object HLists {

  // HLsits (Tuples x Lists = HLists)
    // HOF examples
      // filter, map, flatten, take
      // get, head, tail
      // lifting functions
      // actually these are natural transformations
      // reduce and fold
  // UseCase
  // Extension zu HMaps and HTuples



  val nestedHlist = {
      /*1st Element*/ (45 :: true :: HNil)               ::
      /*2nd Element*/ ("Hallo" :: "Welt" :: 'c' :: HNil) ::
      /*3rd Element*/ Set(1,3,6)                         ::
      /*4th Element*/ List(true, false)                  ::
      /*5th Element*/ HNil
  }

  val unflat = (23 :: "foo" :: 54 :: HNil) :: HNil :: (-2 :: true :: 12 ::  HNil) :: HNil

  val blubb = (23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil

  val flatten = unflat flatMap identity

  val secondList = 23 :: "dvf" :: 24 :: true :: ("Hi", 5) :: 7 :: List("dvf","vfde") :: HNil

  object onlyInt extends Poly1 {
    implicit def caseInt                = at[Int](x => true)
    implicit def caseElse[A]            = at[A](x => false)
  }

  case class Euro(pr:Double)
  case class NatoShip

  def apply() = {


    // HLists

    // there are Tuples
    val hiFive = ("Hi", 5)
    // fixed length, variable types
    val book = ("Benjamin Pierce", "Types and Programming Languages", Euro(42.23), true)
                // author           title                             price        availible


    // and there are lists
    val ingredients = List("strawberry", "cherry", "chocolate", "nut")

    val nato_ships_in_this_room:List[NatoShip] = List()

    val arthimetics:List[(Int, Int) => Int] = List( (_+_), (_-_), (_*_), (_/_) )
    // variable lengths, but same type

    // Hlsits combine both

    val myHlist =   23  :: "foo"  :: true     :: List('b,'a','r') :: HNil
    //  myHLsit is  Int :: String :: Boolean  :: List[Char]       :: HNil
    println(myHlist)

    // List operations
    println(myHlist(0))
    println(myHlist(2))
    val after23 = myHlist.tail
    println(after23)

    // der Typ bleibt erhalten
    val res = if (myHlist(0) < 26 && myHlist(2)) myHlist(1) else "baz"
    println(res)

    val plus:Int => Int => Int = x => (x + _)
    val mult:Int => Int => Int = x => (x * _)
    val myBucket =
            plus ::
            mult ::
            "234" ::
            List("strawberry", "cherry", "chocolate", "nut") ::
            HNil

    val doStuff = myBucket(0)
    println(doStuff(4)(5))

    val divide:List[String] => (Int, String) = ls => { val t = ls.splitAt(2); ( t._1.length, t._2.mkString(":"))

    val f = ???
    val g = ???

    val pairApply = ???

    val merge = ???
    val h = ???

    val compositional = divide :: pairApply :: merge :: h :: HNil

    println(compositional)

    // reduce?

    println(secondList map onlyInt)
    println(unflat)

  }


  object ident extends (Id ~> Id) {
    def apply[A](x:A) = x
  }

  def hof() {
    val ls = 23 :: true :: "Hall" :: List(1,3,5,21) :: ("Hi",5) :: "Welt!" :: HNil

    println("ls: " + ls)
    println("ident: " + ls map ident)
    println("ident: " + ls map identity)

  }
}
