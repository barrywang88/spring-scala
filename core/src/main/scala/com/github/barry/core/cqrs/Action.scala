package com.github.barry.core.cqrs

import com.github.barry.core.domain.ResultModel
import org.slf4j.{Logger, LoggerFactory}

import scala.util.control.NonFatal

/**
 * @ClassName Action
 * @Description Command定义
 * Command Query Responsibility Segregation，CQRS
 * @Author wangxuexing
 * @Date 2020/3/12 11:52
 * @Version 1.0
 */
trait Action[Output] {
  /**
   * 定义查询流程
   * @return
   */
  def execute: ResultModel[Output]={
    try {
      perCheck
      val output = new ResultModel(action)
      postCheck
      output
    }  catch {
      case NonFatal(e) =>
        val logger: Logger = LoggerFactory.getLogger("com.github.barry.core")
        logger.error(e.getMessage, e)
        throw e
    } finally {
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
  def action: Output

  /**
   * 后置检查
   */
  def postCheck={}
}
