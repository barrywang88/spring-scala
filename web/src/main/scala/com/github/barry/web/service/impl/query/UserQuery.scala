package com.github.barry.web.service.impl.query

import com.github.barry.core.cqrs.{Assert, QueryByPage}
import com.github.barry.core.exception.CommonException._
import com.github.barry.web.domain.request.UserQueryReq
import com.github.barry.web.domain.response.UserListResp
import com.github.barry.web.service.impl.query.sql.UserQuerySql
import org.apache.commons.lang3.StringUtils

/**
 * @ClassName UserQuery
 * @Description 分页查询用户信息
 * @Author wangxuexing
 * @Date 2020/4/12 18:37
 * @Version 1.0
 */
class UserQuery(request: UserQueryReq) extends QueryByPage[UserQueryReq, UserListResp]{
  /**
   * 设置查询条件
   *
   * @return
   */
  override def setParam: UserQueryReq = request

  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    request.name match {
      case Some(x) =>  Assert.assert(StringUtils.isNoneBlank(x), dataIsEmpty("用户名不能为空"))
      case None =>
    }
  }

  /**
   * 获取分页查询结果
   *
   * @return
   */
  override def resultListAndTotal: (List[UserListResp], Int) = {
    UserQuerySql.list(request)
  }

}
