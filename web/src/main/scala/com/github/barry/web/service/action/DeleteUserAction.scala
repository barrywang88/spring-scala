package com.github.barry.web.service.action

import com.github.barry.core.cqrs.{Action, Assert}
import com.github.barry.core.exception.CommonException.dataIsEmpty
import com.github.barry.web.service.action.sql.UserActionSql

/**
 * @ClassName DeleteUserAction
 * @Description 删除用户信息
 * @Author wangxuexing
 * @Date 2020/5/3 16:45
 * @Version 1.0
 */
class DeleteUserAction(userId: Long) extends Action[Int]{
  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    Assert.assert(userId > 0, dataIsEmpty("用户ID不为空"))
  }

  /**
   * 具体业务逻辑处理
   *
   * @return
   */
  override def action: Int = {
    UserActionSql.delete(userId)
  }
}
