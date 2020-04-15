package com.github.barry.web.service.query

import com.github.barry.core.cqrs.{Assert, Query}
import com.github.barry.core.exception.CommonException._
import com.github.barry.web.domain.request.UserQueryReq
import com.github.barry.web.domain.response.UserListResp
import com.github.barry.web.service.query.sql.UserQuerySql

/**
 * @ClassName UserQuery
 * @Description TODO
 * @Author wangxuexing
 * @Date 2020/4/12 18:37
 * @Version 1.0
 */
class UserQuery(request: UserQueryReq) extends Query[List[UserListResp]]{
  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    Assert.assert(request.name.nonEmpty, dataIsEmpty("用户名不为空"))
  }

  /**
   * 具体业务逻辑处理
   *
   * @return
   */
  override def action: List[UserListResp] = {
    UserQuerySql.listUser(request)
  }
}
