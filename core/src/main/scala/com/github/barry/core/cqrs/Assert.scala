package com.github.barry.core.cqrs

import com.github.barry.core.exception.{BaseCodeInterface, CommonException}

/**
 * @ClassName Assert
 * @Description 断言定义
 * @Author wangxuexing
 * @Date 2020/3/17 17:38
 * @Version 1.0
 */
object Assert {
  def assert(assertion: scala.Boolean, errorCodeEnums: BaseCodeInterface): Unit =
    if (!assertion) throw new CommonException(errorCodeEnums.getCode, errorCodeEnums.getMsg)
}
