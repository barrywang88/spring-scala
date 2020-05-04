package com.github.barry.web.service.query

import com.github.barry.core.cqrs.{Assert, Query}
import com.github.barry.core.exception.CommonException.dataIsEmpty
import com.github.barry.web.domain.entity.User
import com.github.barry.web.service.query.sql.UserQuerySql

/**
 * @ClassName UserDetail
 * @Description 查询用户详情
 * @Author wangxuexing
 * @Date 2020/5/2 15:22
 * @Version 1.0
 */
class UserDetail(userId: Long) extends Query[Option[User]]{
  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    Assert.assert(userId >0, dataIsEmpty("请传入用户ID"))
  }

  /**
   * 具体业务逻辑处理
   *
   * @return
   */
  override def action: Option[User] = {
    UserQuerySql.detail(userId)
  }
}
