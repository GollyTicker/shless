import shapeless._
import shapeless.poly._

/**
 * Created by Swaneet on 26.05.2014.
 */
object PolyFuncs {


  object ident extends (Id ~> Id) {
    def apply[A](x:A) = x
  }

  object size extends Poly1 {
    implicit def fInt:Case[Int] = at[Int](x => 1)
    implicit def fString:Case[String] = at[String](_.size)
    // implicit def fList[A](implicit fA:Case.Aux[A,Int]):Case[List[A]] = ??? //at[List[A]](_.map(_.size).sum)
    // implicit def fList[Int]:Case[List[Int]] = at[List[Int]](_.map(_.size).sum)
  }


  object sizeGood extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseString = at[String](_.length)
    implicit def caseTuple[T, U]
    (implicit st : Case.Aux[T, Int], su : Case.Aux[U, Int]) =
      at[(T, U)](t => sizeGood(t._1)+sizeGood(t._2))
    implicit def caseList[T](implicit st : Case.Aux[T, Int]) = at[List[T]](ls => ls map(sizeGood(_)) sum)
  }

  def apply() = {
    val ls = 23 :: true :: "Hall" :: List(1,3,5,21) :: "Welt!" :: HNil

    println("ls: " + ls)
    println("ident: " + ls map ident)


    println("size Int: " + size(324))
    println("size String: " + size("12345"))
    // println("size List: " + size(List(2,3,5,63)))


    println("size Int: " + sizeGood(324))
    println("size String: " + sizeGood("12345"))
    println("size Tuple: " + sizeGood((234,"sdfsdf")))
    println("size List: " + sizeGood(List(2,3,5,63)))
  }


}
