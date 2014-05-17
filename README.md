Shapeless Features
==================
by Matthias Nitsche and Swaneet Sahoo <br><br>

**Table of content**

 1. Coproducts and discriminated unions
 2. Automatic instance Derivation
 3. Heterogenous lists, HTuple HMaps
 4. Singleton Types, Generics, Records and Lenses
<br>
<br>

**Coproducts and discriminated unions**
```scala
val t = List(1,2,3,4).map { case _ => x + 1 }
```
**Automatic instance Derivation**
```scala
val t = List(1,2,3,"4").map { 
    case x: Int => x + 1 
    case _ => x.toInt
}
```
**Heterogenous lists, HTuple HMaps**
```scala
val t = List(1,2,3,4).map { case _ => x + 1 }
```
**Singleton Types, Generics, Records and Lenses**
```scala
val t = List(1,2,3,4).map { case _ => x + 1 }
```
