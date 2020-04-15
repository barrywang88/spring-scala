package com.github.barry.core.exception

/**
 * @ClassName CommonException
 * @Description 定义异常类
 * @Author wangxuexing
 * @Date 2020/3/18 11:50
 * @Version 1.0
 */
class CommonException(val errorCode: String, val message: String) extends RuntimeException with BaseCodeInterface{
  override def getCode: String = errorCode
  override def getMsg: String = message
}

object CommonException {
  // 参数异常
  def illegalArgumentException(message: String) =  new CommonException("Err-Common-001", message)
  // 数据不存在
  def dataIsEmpty(message: String) = new CommonException("Err-Common-002", message)
}
