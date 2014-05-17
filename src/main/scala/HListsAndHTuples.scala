

/**
 * Created by Swaneet on 17.05.2014.
 */

import shapeless._
object HListsAndHTuples{

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







  def run() = {
    println(myHlist)
    println(nestedHlist)
  }
}
