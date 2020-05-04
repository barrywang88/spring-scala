package com.github.barry.core.cqrs

import com.github.barry.core.domain.ResultModel
import org.slf4j.{Logger, LoggerFactory}

import scala.util.control.NonFatal

/**
 * @ClassName Query
 * @Description Query定义
 * Command Query Responsibility Segregation，CQRS
 * @Author wangxuexing
 * @Date 2020/3/12 10:53
 * @Version 1.0
 */
trait Query[T] {

  /**
   * 定义查询流程
   * @return
   */
  def execute: ResultModel[T]={
    var result: ResultModel[T] = null
    try {
      perCheck
      result = new ResultModel(action)
      postCheck
      result
    } catch {
      case NonFatal(e) =>
        val logger: Logger = LoggerFactory.getLogger("com.github.barry.core")
        logger.error(e.getMessage, e)
        throw e
    } finally {
      // 无操作
      result
    }
  }

  /**
   * 前置检查
   */
  def perCheck

  /**
   * 具体业务逻辑处理
   * @return
   */
  def action: T

  /**
   * 后置检查
   */
  def postCheck={}
}
