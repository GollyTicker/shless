

/**
 * Created by Swaneet on 17.05.2014.
 */

import shapeless._

object HLists {

  case class Euro(pr: Double)

  def apply() = {
    // there are Tuples
    val hiFive = ("Hi", 5)
    // fixed length, variable types
    val book = ("Benjamin Pierce", "Types and Programming Languages", Euro(42.23), true)
    // author           title                             price        availible

    // and there are lists
    val ingredients = List("strawberry", "cherry", "chocolate", "nut")
    // variable lengths, but same type

    // Hlsits combine both
    val myHlist = 23 :: "foo" :: true :: List('b', 'a', 'r') :: "BAR" :: HNil
    //  myHLsit is  Int :: String :: Boolean  :: List[Char]         :: String :: HNil

    println(s"myHlist: ${myHlist}")

    // Arrey like access
    println(s"myHlist(0): ${myHlist(0)}")
    println(s"myHlist(2): ${myHlist(2)}")

    // Tuple like types (the return type is its original type)
    val res = if (myHlist(0) < 26 && myHlist(2)) myHlist(1) else "baz"
    println(s"Conditional: $res")

    // List like operations
    val after23 = myHlist.tail
    println(s"myHlist.tail: $after23")
    println(s"myHlist.filter[String]: ${myHlist.filter[String]}")

    // Hlisten sind buckets. Man kann dort alles reintun was man will.

    // Und man kann über Hlisten wandern
    // Beispielshaft hier ein Filesystem
    // EIn Element im FS ist entweder ein File oder ein Folder.

    val fileSystem = fileSystemOut

    println("FileSystem: " + fileSystem)

    import syntax.zipper._

    println(s"root Zipper: ${fileSystem.toZipper}")

    println(s"root: ${fileSystem.toZipper.get}") // der Foldername "/"
    println(s"root contents: ${fileSystem.toZipper.right.get}") // mit einem Right können wir uns die HList zurückgeben lassen

    // mit einem down gehen wir automatisch in das allererste Folder rein
    println(s"swaneet : ${fileSystem.toZipper.right.down.get}")

    println(s"matze contents: ${
      fileSystem.toZipper
        .right.down // zeigt nun auf den Swaneet Ordner
        .right // nach rechts zu "Matthias navigieren"
        .down // Ordner betreten. Zeiger zeigt nun auf den Ordnernamen "Matthias"
        .right // hol dir nun die Contents des Ordners
        .get
    }")

    // Neue Datei in Swaneet erzeugen.
    println(s"Swaneet verändert: ${
      fileSystemOut.toZipper
        .right.down // zeigt auf Ordner Swaneet
        .down // betritt Ordner Swaneet. Zeigt auf Ordnernamen "Swaneet"
        .right // Ordnerinhalte betreten. Zeigt auf das allersrte Element ("shapeless-präzi.tex")
        .insert("airbnb-plans.txt") // neue Datei vorne hinzufügen
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

  /*
  myHlist: 23 :: foo :: true :: List('b, a, r) :: BAR :: HNil
  myHlist(0): 23
  myHlist(2): true
  Conditional: foo
  myHlist.tail: foo :: true :: List('b, a, r) :: BAR :: HNil
  myHlist.filter[String]: foo :: BAR :: HNil
  doStuff(4)(5): 9
  ingredients: List(strawberry, cherry, chocolate, nut)
  FileSystem: / :: Swaneet :: shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil :: HNil :: Matze :: shapeless-präzi.keynote :: passwords.txt :: HNil :: HNil :: root-password.txt :: HNil :: HNil
  root Zipper: Zipper(HNil,/ :: Swaneet :: shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil :: HNil :: Matze :: shapeless-präzi.keynote :: passwords.txt :: HNil :: HNil :: root-password.txt :: HNil :: HNil,None)
  root: /
  root contents: Swaneet :: shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil :: HNil :: Matze :: shapeless-präzi.keynote :: passwords.txt :: HNil :: HNil :: root-password.txt :: HNil
  swaneet : Swaneet :: shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil :: HNil
  matze contents: shapeless-präzi.keynote :: passwords.txt :: HNil
  Swaneet verändert: / :: Swaneet :: airbnb-plans.txt :: shapeless-präzi.tex :: todo.txt :: launchMissiles.hs :: HNil :: HNil :: Matze :: shapeless-präzi.keynote :: passwords.txt :: HNil :: HNil :: root-password.txt :: HNil :: HNil
  */

}
