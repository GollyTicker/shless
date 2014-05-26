

/**
 * Created by Swaneet on 17.05.2014.
 */

import shapeless._
import shapeless.HList.hlistOps
import shapeless.poly._
object HList{

  val myHlist = 23 :: "foo" :: HNil
  // The type is Int :: String :: HNil
  // The type encoded the different lists in use.

  val nestedHlist = {
    /*1st Element*/ (45 :: true :: HNil)               ::
    /*2nd Element*/ ("Hallo" :: "Welt" :: 'c' :: HNil) ::
    /*3rd Element*/ Set(1,3,6)                         ::
    /*4th Element*/ List(true, false)                  ::
    /*5th Element*/ HNil
  }

  // mögliches Demobeispiel
  // unstrukturierte Daten parsen
  // Geparste Typen sind unterschiedlich.
  // auf den speziellesten Typ parsen.
  // Dann darauf z.B. polymorphe funktionen aufrufen.


  val ls = 23 :: HNil :: "dsf" :: HNil

  val unflat = (23 :: "foo" :: 54 :: HNil) :: HNil :: (-2 :: true :: 12 ::  HNil) :: HNil

  val blubb = (23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil

  val flatten = unflat flatMap identity

  object ints extends (Set ~> Option) {
    def apply[T](s : Set[T]) = s.headOption
  }

  object ident extends (Id ~> Id) {
    def apply[A](x:A) = x
  }

  object size extends Poly1 {
    implicit def fInt:Case[Int] = at[Int](x => 1)
    implicit def fString:Case[String] = at[String](_.size)
    implicit def fList[A](implicit fA:Case.Aux[A,Int]):Case[List[A]] = ??? //at[List[A]](_.map(_.size).sum)
  }

  //val getInts = flatten filter ints

  def run() = {

    val withList = 23 :: 24 :: "dvf" :: List("dvf","vfde") :: HNil

    //println(myHlist)
    //println(nestedHlist)
    println("ls " + ls)
    //println(unflat)

    println("ls map ident " + ls map ident)
    //println("ls map ident " + ls map size)
    println("withList " + withList)
    //println("withList " + withList map size)

    println(size(23))
    println(size(24))
    println(size(List("sdf","sdffe")))

  }
}
