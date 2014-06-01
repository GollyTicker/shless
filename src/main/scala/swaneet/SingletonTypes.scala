package swaneet

import shapeless._
import syntax.singleton._
import syntax.std.tuple._
import scala.util.Try
import shapeless.test.illTyped

/**
 * Created by Swaneet on 01.06.2014.
 */
object SingletonTypes {


  // Singleton Types
  // Erlaubt zur Compilezeit statisch die konkreten Werte zu kennen
  // um entsprechend anders zu Compilieren

  def apply() = {

    val ls = "Hallo" :: 5 :: true :: HNil

    val hallo = ls(0)
    // hallo wird automatisch zum type String!
    val five = ls(1)
    // hallo wird automatisch zum type Int

    // aber wie kann man denn beliebige Zahlen als Arugmente vergeben?
    // Von welchem Typ ist denn das apply(x:???) in der HList?
    // das apply liefert bei 0 einen String
    // aber bei 1 ein Int?

    // Wie kann denn scala zur Compilezeit schon das Argument wissen?

    // Man muss den Wert mit in den Typen integrieren!
    // Das machen SingletonTypes.

    // Ein Witness ist ein Objekt welches in seinem Typen nicht nur
    // den Typen des Wertes, sondern den Wert selber kennt!
    val wt0 = Witness(0)
    // wt0: Witness{type T = Int(0)}  // <- die 0 wurde hier mit in den Typen reingenommen.

    val wt1 = Witness(1)
    // wt1: Witness{type T = Int(1)}

    println("Witness(0): " + wt0) // SingletonTypes$$1$$1@17fa242
    println("Witness(1): " + wt1) // SingletonTypes$$3$$1@1a85286

    // trait welches von allen Eingabesingletons unterstüzt werden soll
    trait Foo[In] {
      type Out
      def out: Out
    }
    implicit val ap0 = new Foo[wt0.T] {
      // bei 0 soll 5 rauskommen
      type Out = Int
      val out = 5
    }
    implicit val ap1 = new Foo[wt1.T] {
      // bei 1 soll List("ABC","DEF") rauskommen
      type Out = List[String]
      val out = List("ABC", "DEF")
    }

    def foo(wt: WitnessWith[Foo]): wt.Out = wt.instance.out.asInstanceOf[wt.Out]

    println("foo(1): " + foo(1)) // List(ABC, DEF)
    println("foo(0): " + foo(0)) // 5

    import shapeless.test.illTyped
    // compiliert nciht. 7 wurde nie registriert.
    illTyped("foo(7)")

    val res: Int = foo(0) + 2 // Integer kommt und Addition funktioniert
    println("foo(0) + 2: " + res) // 7

    illTyped("foo(1) + 2") // bei 1 kommt aber eine Liste. Entsprechend compiliert das hier nicht

    // Mit diesem Trick ist 'foo' nun eine Funktion die bereits zur Kompilezeit
    // ihr Argument kennt um damit auch wieder zur Compilezeit zu reagieren und untershciedliche
    // Typen anzubieten.

    // Und somit können HListen ihren Ergebnistyp zur Compilezeit kennen.
    // Sie 'verwenden SingletonTypes und Witnesses.

  }

  // nicht in die Präsi:

  val zero = 0
  // zero: Int
  val zeroSingleton = 0.narrow
  // zeroSingleton: Int(0)

  // Dies kann man insbesondere daran erkennen, dass man foo kein beliebiges Argument übergeben kann.
  illTyped("def bar(x:Int) = foo(x)")
  //  Error:(83, 26) Expression x does not evaluate to a constant or a stable value
  //    def bar(x:Int) = foo(x)
  //                         ^

  // nicht preäsentieren
  // val nochein_wt0 = Witness(0)
  // println(wt0 == nochein_wt0)// es$$1$$1@17fa242

}
