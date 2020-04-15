package com.github.barry.core.utils

/**
  * 类功能描述：Scala高级函数
  *
  * @author WangXueXing create at 18-11-22 下午5:29
  * @version 1.0.0
  */
object ScalaFunction {
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
}
