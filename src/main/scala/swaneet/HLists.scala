

/**
 * Created by Swaneet on 17.05.2014.
 */

import scala.util.Try
import shapeless._
import shapeless.poly._

object HLists {

  case class Euro(pr: Double)

  case class NatoShip

  def launchMissiles(x: String) = ()

  def apply() = {


    // HLists

    // there are Tuples
    val hiFive = ("Hi", 5)
    // fixed length, variable types
    val book = ("Benjamin Pierce", "Types and Programming Languages", Euro(42.23), true)
    // author           title                             price        availible

    // and there are lists
    val ingredients = List("strawberry", "cherry", "chocolate", "nut")
    val nato_ships_in_this_room: List[NatoShip] = List()
    val arthimetics: List[(Int, Int) => Int] = List((_ + _), (_ - _), (_ * _), (_ / _))
    // variable lengths, but same type

    // Hlsits combine both
    val myHlist = 23 :: "foo" :: true :: List('b, 'a', 'r') :: "BAR" :: HNil
    //  myHLsit is  Int :: String :: Boolean  :: List[Char]       :: String :: HNil
    println("myHlist: " + myHlist)

    // Tuple like access
    println("myHlist(0): " + myHlist(0))
    println("myHlist(2): " + myHlist(2))

    // list like operations
    val after23 = myHlist.tail
    println("myHlist.tail: " + after23)
    println("myHlist.filter[String]: " + (myHlist.filter[String]))

    // der Typ bleibt erhalten
    val res = if (myHlist(0) < 26 && myHlist(2)) myHlist(1) else "baz"
    println("Conditional: " + res)

    // Hlisten sind geeignet um alles dort rein zu tun.

    val plus: Int => Int => Int = x => (x + _)
    val mult: Int => Int => Int = x => (x * _)

    val bucket =
      plus ::
        mult ::
        "234" ::
        List("strawberry", "cherry", "chocolate", "nut") ::
        Try(launchMissiles("5min")) ::
        HNil

    val doStuff = bucket(0)
    println("doStuff(4)(5): " + doStuff(4)(5))
    println(bucket(4))


    // Und man kann über Hlisten wandern
    // Beispielshaft hier ein Filesystem
    // EIn Element im FS ist entweder ein File oder ein Folder.

    val fileSystem = fileSystemOut

    println("FileSystem: " + fileSystem)

    import syntax.zipper._

    println(s"root Zipper: ${fileSystem.toZipper}")

    println(s"root: ${fileSystem.toZipper.get}")  // der Foldername "/"
    println(s"root contents: ${fileSystem.toZipper.right.get}") // mit einem Right können wir uns die HList zurückgeben lassen

    // mit einem down gehen wir automatisch in das allererste Folder rein
    println(s"swaneet : ${fileSystem.toZipper.right.down.get}")

    println(s"matze contents: ${fileSystem.toZipper
                                      .right.down // zeigt nun auf den Swaneet Ordner
                                      .right  // nach rechts zu "Matthias navigieren"
                                      .down // Ordner betreten. Zeiger zeigt nun auf den Ordnernamen "Matthias"
                                      .right  // hol dir nun die Contents des Ordners
                                      .get}")

    // Neue Datei in Swaneet erzeugen.
    println(s"Swaneet verändert: ${
      fileSystemOut.toZipper
                                      .right.down // zeigt auf Ordner Swaneet
                                      .down   // betritt Ordndr Swaneet. Zeigt auf Ordnernamen "Swaneet"
                                      .right  // Ordnerinhalte betreten. Zeigt auf das allersrte Element ("shapeless-präzi.tex")
                                      .insert("airbnb-plans.txt")  // neue Datei vorne hinzufügen
                                      .root.reify // zurück zum springen und Änderungen als neue HList zurückgeben
    }")
  }

  // Eine Datei: "dateiname.txt"
  // Mehrere Dateien: "dateiname.txt" :: "dateiname2.txt" :: HNil
  // Ein Folder: ("foldername", ("dateiname.txt" :: "dateiname2.txt" :: HNil))

  val swaneet =
    "Swaneet" ::
      ("shapeless-präzi.tex" :: "todo.txt" :: "launchMissiles.hs" :: HNil) ::
      HNil

  val matze =
    "Matze" ::
      ("shapeless-präzi.keynote" :: "passwords.txt" :: HNil) ::
    HNil

  val fileSystemOut =
    "/" ::
    (swaneet :: matze :: "root-password.txt" :: HNil) ::
    HNil

}
