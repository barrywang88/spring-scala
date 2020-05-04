package com.github.barry.core.utils

import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => ru}
//隐式转换必须要导入

/**
  * 类功能描述：Scala高级函数
  *
  * @author WangXueXing create at 18-11-22 下午5:29
  * @version 1.0.0
  */
object ScalaFunction {
  val run = ru.runtimeMirror(Thread.currentThread().getContextClassLoader)
  /**
    * Scala实现Java7 try-with-resources
    * @see https://www.cnblogs.com/barrywxx/p/10002422.html
    */
  def tryWithResources[A <: {def close(): Unit }, B](a: A)(f: A => B): B = {
    try {
      f(a)
    } finally {
      if(a != null){
        a.close()
      }
    }
  }

  /**
   * 判断是否为Java类
   * @param clazz 类
   * @return
   */
  def isJavaClass(clazz: Class[_]): Boolean= {
    run.classSymbol(clazz).asClass.isJava
  }

  /**
   * 判断是否为Java类
   * @tparam T
   * @return
   */
  def isJava[T: Manifest]: Boolean = ru.typeOf[T].typeSymbol.asClass.isJava

  /**
   * 分页查询
   * @return
   */
 /* def queryWithTotal[T](codeBlock: => List[T]): (List[T], Int) = {
    (codeBlock, ScfDataSource.mysqlData.queryInt(sql"""SELECT FOUND_ROWS()"""))
  }
*/
  /* def json2CaseClass[T](jsonStr: String, t: T)(implicit tag: ru.WeakTypeTag[T]) = {
     implicit val formats = org.json4s.DefaultFormats
     parse(jsonStr).extract[T]
   }*/

  def mkArray[T: ClassTag](elems: T*) = Array[T](elems: _*)

  def main(args: Array[String]): Unit = {
    /*println(mkArray(1,2,3))
    println(mkArray("a", "b"))*/
    case class Class1(_name:String, students: List[Student])
    case class Student(sid:String, _name:String)
    val s = "{\"_name\":\"Class1\",\"students\":[{\"sid\":\"1\",\"_name\":\"小明\"},{\"sid\":\"1\",\"_name\":\"小王\"}]}"
//    println(json2CaseClass[Class1](s))

//    val clazz:Class1 = parse(s).extract[Class1]
//    println(clazz)
/*val gson = new Gson
    val student = Student("张三", "100")
    val str = gson.toJson(student, classOf[Student])
    println(str)
    val student2 = gson.fromJson(str, classOf[Student])
    println(student2)*/
  }

}
