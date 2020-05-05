package com.github.barry.web.service.impl.action

import com.github.barry.core.cqrs.{Action, Assert}
import com.github.barry.core.exception.CommonException.dataIsEmpty
import com.github.barry.web.domain.request.UserSaveReq
import com.github.barry.web.service.impl.action.sql.UserActionSql
import org.apache.commons.lang3.StringUtils
import org.springframework.transaction.annotation.Transactional

/**
 * @ClassName UpdateUserAction
 * @Description 更新用户信息
 * @Author wangxuexing
 * @Date 2020/5/3 16:44
 * @Version 1.0
 */
class UpdateUserAction(request: UserSaveReq) extends Action[Int]{
  /**
   * 前置检查
   */
  override def perCheck: Unit = {
    Assert.assert(StringUtils.isNoneBlank(request.name), dataIsEmpty("用户名不为空"))
    Assert.assert(StringUtils.isNoneBlank(request.telphone), dataIsEmpty("用户电话不为空"))
    Assert.assert(request.role > 0, dataIsEmpty("用户角色不为空"))
    Assert.assert(request.birthday != null, dataIsEmpty("生日不为空"))
    Assert.assert(request.salary > 0, dataIsEmpty("工资不为0"))
    Assert.assert(request.companyId>0, dataIsEmpty("金融机构ID不能为0"))
  }

  /**
   * 具体业务逻辑处理
   *
   * @return
   */
  override def action: Int = {
    UserActionSql.update(request)
  }
}
