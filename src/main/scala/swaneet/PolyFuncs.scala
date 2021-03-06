package swaneet

import shapeless._
import shapeless.poly._

/**
 * Created by Swaneet on 26.05.2014.
 */
object PolyFuncs {

  // Viele Anwendungen.

  // hier nur ein kleines Beispiel wie man einfacher und verständlicher type-class artige Funktionen implementieren kann

  object reverse extends Poly1 {
    import Integer.parseInt
    implicit def revInt = at[Int](x => parseInt(x.toString().reverse))

    implicit def revString = at[String](_.reverse)

    implicit def revList[A](implicit revA: Case.Aux[A, A])
    = at[List[A]](ls => (ls map (reverse(_)) reverse))

    implicit def revTuple[A, B](implicit revA: Case.Aux[A, A], revB: Case.Aux[B, B])
    = at[(A, B)]{ case (a, b) => (reverse(b), reverse(a)) }
  }


  def apply() = {

    println("strLen Int: " + reverse(324))
    println("strLen String: " + reverse("12345"))
    println("strLen List: " + reverse(List(2, 3, 5, 63)))
    println("strLen List: " + reverse(List("2", "3", "5", "63")))
    println("strLen List: " + reverse(("abedc", 123)))

    println("strLen List: " + ((14 :: 23 :: "sdfsdf" :: HNil) map reverse))

    import shapeless.test.illTyped
    illTyped("reverse(true)") // compiliert nicht, weil kein reverse über Booleans definiert ist.

    illTyped("reverse(Set(1,2,4))") // compiliert nicht, weil kein reverse über Sets definiert ist.

  }

}
