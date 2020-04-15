package com.github.barry.core.utils

import org.slf4j.LoggerFactory

/**
  * 类功能描述：Debug日志追踪
  *
  * @author barry create at 18-8-29 下午3:41
  * @version 1.0.0
  */
object Debug {
  val LOGGER = LoggerFactory.getLogger(getClass)
  /**
    *追踪代码块
    * @param label 标签名
    * @param codeBlock 代码块
    * @tparam T 返回结果类型
    * @return
    */
  def trace[T](label: String)(codeBlock: => T) = {
    val t0 = System.nanoTime()
    val result = codeBlock
    val t1 = System.nanoTime()
    LOGGER.info(s"${label} time:{}ms", (t1-t0)/1000000)
    result
  }

  def main(args: Array[String]): Unit = {
    val size = 3
    val size1 = size
    for(i <- 0 until size1 ){
      println(i)
      println(size)
      val pageNum = i match {
        case a if((i+1) == size) => println(s"--10--${a}")
        case _ => println(s"--1--${i}")
      }
    }
  }
}
