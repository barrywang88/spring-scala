package com.github.barry.core.utils

import java.util.concurrent.atomic.AtomicInteger

/**
  * 数据学计算工具类
  *
  * @author BarryWang create at 2018/7/12 16:45
  * @version 0.0.1
  */
object MathUtils {
  var randomNo = new AtomicInteger(0)

  /**
    * 格式化金额(精确到小数后2位)
    * @param param
    * @return
    */
  def format(param: BigDecimal): BigDecimal = {
    this.format(param, 2)
  }
  /**
    * 格式化金额(精确到小数后2位)
    * @param param
    * @return
    */
  def format3(param: BigDecimal): BigDecimal = {
    this.format(param, 3)
  }
  /**
    * 格式化金额
    * @param param
    * @return
    */
  def format(param: BigDecimal, precision: Int): BigDecimal = {
    if(param == null){
      return BigDecimal(0.00)
    }
    param.setScale(precision, BigDecimal.RoundingMode.HALF_UP)
  }

  /**
    * 四舍五入(精确2位小数)
    * @param param
    * @return
    */
  def roundToDouble(param: BigDecimal): Double = {
    this.round(param, 2).toDouble
  }

  /**
    * 四舍五入(精确2位小数)
    * @param param
    * @return
    */
  def round(param: BigDecimal): BigDecimal = {
    this.round(param, 2)
  }

  /**
    * 四舍五入
    * @param param
    * @return
    */
  def round(param: BigDecimal, precision: Int): BigDecimal = {
    param.setScale(precision, BigDecimal.RoundingMode.HALF_UP)
  }

  /**
    * 获取指定长度的随机数
    * @param size 指定长度
    * @return
    */
  def getRandom(size: Int) = {
    val seqNum = String.format(s"%0${size}d",Integer.valueOf(randomNo.incrementAndGet()))
    seqNum.substring(seqNum.length - size,seqNum.length)
  }

  def bigDecimalRoundToInt(param: BigDecimal): Int ={
    Math.round(param.toDouble).toInt
  }
  def main(args: Array[String]): Unit = {
    println(getRandom(20))
  }
}
