

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
    type File = String
    type Folder = (String, HList)
    // EIn Element im FS ist entweder ein File oder ein Folder.

    import shapeless.BasisConstraint._
    type FileSystem = File :: Folder :: HNil
    def acceptBasis[L <: HList : Basis[FileSystem]#λ](l : L) = true

    val fileSystem = fileSystemOut

    acceptBasis(fileSystem)

    println(fileSystem)
    // Folder(/)(Folder(Swaneet)(File(shapeless-präzi.tex) :: File(todo.txt) :: File(launchMissiles.hs) :: HNil) :: Folder(Matthias)(File(shapeless-präzi.keynote) :: File(passwords.txt) :: Folder(private)(File(permission.err) :: HNil) :: HNil) :: Folder(system)(File(google-master-plan.secret) :: File(Happy-families-are-all-alike.txt) :: File(Every-unhappy-family-is-unhappy-in-its-own-way.txt) :: HNil) :: HNil)

    import syntax.zipper._

    // val z = fileSystem.toZipper

    // println(fsGen.to(fileSystem))

    val fs = ("sdf" :: "sdfsdf" :: HNil) :: "asfd" :: HNil

    println(fs)
  }
  val fileSystemOut =
    ("/",
      "Swaneet" :: "Matze" :: "system" :: HNil
      )
  /*Folder("/")(
    Folder("Swaneet")(
      File("shapeless-präzi.tex") :: File("todo.txt") :: File("launchMissiles.hs") :: HNil
    ) ::
    Folder("Matthias")(
      File("shapeless-präzi.keynote") :: File("passwords.txt") ::
        Folder("private")(File("permission.err"):: HNil) :: HNil
    ) ::
    Folder("system")(
      File("google-master-plan.secret") ::
      File("Happy-families-are-all-alike.txt") ::
      File("Every-unhappy-family-is-unhappy-in-its-own-way.txt") ::
      HNil
    ) ::
    HNil
  )*/

}
