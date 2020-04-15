package com.github.barry.core.sql

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable
import scala.language.experimental.macros

/**
  * TODO support camel and underscore name mapping like doSomething <-> do_something
  */
object BeanBuilder {

  /**
    * BeanBuilder.build is a utils to build a target case-class instance from other case-class instance.
    *
    * It follows the simple name-to-name field copy, if the default copy not works for you, you may
    * define custom copies in the adds arguament.
    *
    * BeanBuilder support copy field via
    * 1. direct assignment. such as dest.field = src.field
    * 2. using implicit convert. such as dest.field = implicit_func( src.field )
    * 3. for box types, call map. such as dest.field = src.field.map( implicit_func )
    * 4. looking for a implict method copyto, such as dest.field = src.field.copyTo[T]
    * 5. otherwise, try dest.field = BeanBuilder.build[T](src)
    *
    * @param sources
    * @param adds such as "name" -> name etc
    * @tparam T the taregt bean
    * @return
    */
  def build[T](sources: Any*)(adds: (String, Any)*) : T = macro buildImpl[T]

  def convertType(c: scala.reflect.macros.whitebox.Context)(name: String, f: c.Tree, alias: c.Tree, F: c.Type, T: c.Type): c.Tree = {
    import c.universe._

    def directConvert(): Option[c.Tree] = {
      if( F <:< T) Some(alias) //Some(f)
      else None
    }

    def implicitConvert(): Option[c.Tree] = {
      val result = c.typecheck (
        q"""_root_.scala.Predef.implicitly[ $F => $T ].apply( $f ) """,
        silent = true
      )
      result match {
        case EmptyTree => None
        case _ => Some(q"_root_.scala.Predef.implicitly[$F => $T].apply($alias)") // Some(result)
      }
    }

    // BOX[F] => Box[T], f.map( F=>T )
    // TODO support more F=>T
    def boxMap(): Option[c.Tree] = {
      (F, T) match {
        case ( TypeRef(p1, s1, args1), TypeRef(p2, s2, args2) ) if p1 == p2 && args1.length == args2.length && args1.length == 1 =>
          // don't check s1 & s2 equals, eg, s1: Some, s2: Option
          val fromElementType: c.universe.Type = args1.apply(0)
          val toElementType: c.universe.Type = args2.apply(0)
          val mapTo = c.typecheck(
              q""" $f.map( _root_.scala.Predef.implicitly[ $fromElementType => $toElementType] )""",
              silent = true
            )
          mapTo match {
            case EmptyTree => None
            case _ => Some(q"$alias.map(_root_.scala.Predef.implicitly[$fromElementType => $toElementType])") //Some(mapTo)
          }
        case _ =>
          None
      }
    }

    // src.copyTo[T]  the method may require more implicits if it works
    def implicitCopyTo: Option[c.Tree] = {
      val copyTo = c.typecheck(
        q"""($f.copyTo[$T] : $T)""",
        silent = true
      )
      copyTo match {
        case EmptyTree => None
        case _ => Some(q"($alias.copyTo[$T]:$T)")// Some(copyTo)
      }
    }

    // T.apply(f)
    def simpleApply(): Option[c.Tree] = {
      if(T.typeSymbol.companion != null) {
        val companion = T.typeSymbol.companion
        c.typecheck(q"""$companion.apply($f): $T""", silent = true) match {
          case EmptyTree => None
          case x@_ => Some(q"$companion.apply($alias):$T")// Some(x)
        }
      }
      else None
    }

    // F.unapply(f).get
    def simpleUnapply(): Option[c.Tree] ={
      if(F.typeSymbol.companion != null) {
        val companion = F.typeSymbol.companion
        c.typecheck(q"""$companion.unapply($f).get:$T""", silent = true) match {
          case EmptyTree => None
          case x@_ => Some(q"$companion.unapply($alias).get: $T")// Some(x)
        }
      }
      else None
    }
//
//    // T.apply( F.unapply(f) )
//    def applyUnapply(): Option[c.Tree] = {
//      val Fcomp = F.typeSymbol.companion
//      val Tcomp = T.typeSymbol.companion
//
//      if(Fcomp != null && Tcomp != null) {
//        c.typecheck(
//          q"""val fx = $Fcomp.unapply($f).get; $Tcomp.apply( fx ): $T""", silent = true) match {
//          case EmptyTree => None
//          case tree@_ => Some(tree)
//        }
//      }
//      else None
//    }

    // recusive call Build[T](srcField) if it works
    def tryBuild(): Option[c.Tree] = {
      c.typecheck(q"""import ${c.prefix}._; build[$T]($f)() """, silent = true) match {
        case EmptyTree => None
        case x@_ => Some(q"import ${c.prefix}._; build[$T]($alias)() ")// Some(x)
      }
    }

    // all tries failed
    def failed(): c.Tree = {
      c.error(c.enclosingPosition, s"build failed for field $name $F => $T, see documents for build[T](src)(adds)")
      EmptyTree
    }

    directConvert
      .orElse(implicitConvert)
      .orElse(boxMap)
      .orElse(implicitCopyTo)
      .orElse(simpleApply)
      .orElse(simpleUnapply)
//      .orElse(applyUnapply)
      .orElse(tryBuild)
      .getOrElse(failed)
  }

  def buildImpl[T: c.WeakTypeTag](c: scala.reflect.macros.whitebox.Context)(sources: c.Tree*)(adds: c.Tree*): c.Tree = {

    import c.{universe => u}
    import u._

    val t = implicitly[c.WeakTypeTag[T]]

    if(t.tpe.typeSymbol.asClass.isCaseClass == false) {
      c.error(c.enclosingPosition, "only support build Case Class, but [$t] is not ")
      EmptyTree
    }
    else {
      // dest fields
      val targetFields: List[TermSymbol] = t.tpe.typeSymbol.asClass.primaryConstructor.asMethod.paramLists.apply(0).map(_.asTerm)

      // using alias for each source because it maybe a evaluation expr, avoid dupicate eval
      // (c.Tree, alias, field-caseAccessor) foreach sources.field

      val idx = new AtomicInteger(0)
      val sourceFields2: List[(c.Tree, TermName, MethodSymbol)] = sources.toList.flatMap { src: c.Tree =>
        val alias = TermName( s"x${idx.incrementAndGet}")  // x1, x2
        src.tpe.members.filter(m => m.isMethod && m.asMethod.isCaseAccessor).map(m => (src, alias, m.asMethod))
      }

      val additionByName: Map[String, c.Tree] = adds.toList.map { add =>
        //val method = TermName("->")
        //println(s"add = ${u.showRaw(add)}")
        val (a: c.Tree, b: c.Tree) = add match {
          case q"($a, $b)" => (a, b)
          case q"scala.Predef.ArrowAssoc[..$x]($a).->[..$y]($b)" => (a, b)
          case q"scala.this.Predef.ArrowAssoc[..$x]($a).->[..$y]($b)" => (a, b)
          case _ =>
            println(s"can't match ${u.showRaw(add)}\nusing fieldNameStr -> fieldValue style")
            throw new AssertionError()
        }
        val Literal(Constant(str: String)) = a
        (str, b)
      }.toMap

      val aliases: List[(c.Tree, c.TermName)] = sourceFields2.map { case (tree, term, method) => (tree, term) }.toSet.toList

      // generated val x1 = source1; val x2 = source2
      val aliasTrees: List[c.Tree] = aliases.map { case (tree, term) => q"val $term  = $tree" }

      val parameters: List[c.Tree] = targetFields.flatMap { field: TermSymbol =>
        val fieldType = field.typeSignature
        val fieldName = field.name.toString
        val termName = TermName(fieldName)

        // first check additional which must be direct matched
        if (additionByName contains fieldName) {
          val add = additionByName(fieldName)
          Some(q"$termName = $add")  // 2017-11-22 additional part should not convert based on it's infer type.
          // val convert = convertType(c)(fieldName, add, add.tpe, fieldType)
          // Some(q"""$termName = $convert""")
        }
        else { // then check source fields, support convert
          val matchedFields: List[(c.Tree, TermName, MethodSymbol)] = sourceFields2.filter(tree_method => tree_method._3.name.toString == fieldName)

          matchedFields match {
            case (tree: c.Tree, alias: TermName, accessor: MethodSymbol) :: Nil => // only 1
              val srcType = accessor.returnType

              // using tree for typecheck, but generate code on alias
              val convert = convertType(c)(fieldName, q"""$tree.$termName""", q"$alias.$termName",srcType, fieldType)
              Some(q"""$termName = $convert""")
            case Nil => // no source field matched, error checked via Case-Class's constructor
              None
            case header :: tailer =>
              val symbolNames = matchedFields.map(_._1.symbol.name.toString).mkString(",")
              throw new AssertionError(s"$field has ambiguous, it occurs in ${symbolNames}, to avoid it, you can define in the adds args.")
          }
        }
      }

      val result = q"""{ ..$aliasTrees; new $t( ..$parameters ) }"""

//      println( "generate code:" + u.show(result) )

      result
    }
  }

}
