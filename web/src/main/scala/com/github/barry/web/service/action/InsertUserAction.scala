package com.github.barry.web.service.action

import com.github.barry.core.cqrs.{Action, Assert}
import com.github.barry.core.exception.CommonException.dataIsEmpty
import com.github.barry.web.domain.request.UserSaveReq
import com.github.barry.web.service.action.sql.UserActionSql
import org.springframework.util.StringUtils

/**
 * @ClassName InsertUserAction
 * @Description 添加用户
 * @Author wangxuexing
 * @Date 2020/4/12 18:36
 * @Version 1.0
 */
class InsertUserAction(request: UserSaveReq) extends Action[Long]{
  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    Assert.assert(!StringUtils.isEmpty(request.name), dataIsEmpty("用户名不为空"))
    Assert.assert(!StringUtils.isEmpty(request.telphone), dataIsEmpty("用户电话不为空"))
  }

  /**
   * 具体业务逻辑处理
   *
   * @return
   */
  override def action: Long = {
    UserActionSql.insertUser(request)
  }
}
