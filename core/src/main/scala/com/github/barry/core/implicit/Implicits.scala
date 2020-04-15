package com.github.barry.core.`implicit`

import com.github.barry.core.sql.SQLWithArgs
import com.github.barry.core.sql.sql._

/**
 * @ClassName Implicits
 * @Description 定义常用隐式转换
 * @Author wangxuexing
 * @Date 2020/3/24 11:18
 * @Version 1.0
 */
object Implicits {

  /**
   * "hello" in List("hello", "world")
   */
  implicit class CommonEx[T](v: T) {
    def in(seq: Seq[T]) = seq.contains(v)

    def notIn(seq: Seq[T]) = !in(seq)
  }

  /**
   * 10 between (10,20)
   */
  implicit class OrderedExt[T: Ordering](v: T) {

    def between(min: T, max: T) = {
      val ordering = implicitly[Ordering[T]]
      ordering.lteq(min, v) && ordering.lteq(v, max)
    }

  }


  implicit class SqlBooleanImplicit(b: Boolean) {
    def default(): SQLWithArgs = sql""

    // call by name
    def optional(left: => SQLWithArgs, right: => SQLWithArgs = null): SQLWithArgs =
      if (b) left
      else {
        val r = right
        if (r != null) r
        else default
      }

    def optional(f: => SQLWithArgs): SQLWithArgs = if (b) f else default
  }

  /**
   *   val query = sql " SELECT * FROM user WHERE 1=1 "
   *   val name:Option[String] = None
   *   val optionSql = name.optional( str => s" and name = ${str}")
   */
  implicit class SqlOptionEx[T] (opt: Option[T]) {
    def optional(op: T => SQLWithArgs) : SQLWithArgs = opt match {
      case Some(value) => op(value)
      case None => SQLWithArgs("", Seq.empty)
    }

  }

  /**
   * timestamp => Long
   */
  implicit def timestamp2Long(x: java.sql.Timestamp): Long = x.getTime

  /**
   *Long => date
   */
  implicit def long2Date(x: Long) = new java.sql.Timestamp(x)

  /**
   * BigDecimal => Double
   */
  implicit def bigDecimal2Double(x:scala.math.BigDecimal): Double = x.toDouble

}
