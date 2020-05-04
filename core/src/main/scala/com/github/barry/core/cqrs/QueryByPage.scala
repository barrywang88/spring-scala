package com.github.barry.core.cqrs

import com.github.barry.core.domain.PageQuery
import com.github.pagehelper.PageInfo

import scala.collection.JavaConversions._

/**
 * @ClassName PageQuery
 * @Description 分页查询接口
 * @Author wangxuexing
 * @Date 2020/3/24 15:21
 * @Version 1.0
 */
trait QueryByPage[P <: PageQuery, T] extends Query[PageInfo[T]] {

  /**
   * 添加分页信息
   * @return
   */
  override def action: PageInfo[T] = {
    val pageInfo = new PageInfo[T]
    val param = setParam
    val result = resultListAndTotal
    pageInfo.setList(result._1)
    pageInfo.setPageNum(param.getCurrentPage)
    pageInfo.setPageSize(param.getPageSize)
    pageInfo.setTotal(result._2)
    pageInfo
  }

  /**
   * 获取分页查询结果
   * @return
   */
  def resultListAndTotal: (List[T], Int)

  /**
   * 设置查询条件
   * @return
   */
  def setParam: P
}
