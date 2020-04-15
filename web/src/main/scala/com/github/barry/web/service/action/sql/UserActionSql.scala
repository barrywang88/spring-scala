package com.github.barry.web.service.action.sql

import com.github.barry.core.sql.sql._
import com.github.barry.web.config.DatasourceConfig
import com.github.barry.web.domain.request.UserSaveReq

/**
 * @ClassName UserActionSql
 * @Description 用户增删改操作
 * @Author wangxuexing
 * @Date 2020/4/12 20:11
 * @Version 1.0
 */
object UserActionSql {

  /**
   * 插入用户信息并返回主键
   * @param request
   * @return
   */
  def insertUser(request: UserSaveReq): Long ={
    DatasourceConfig.myDatasource.generateKey[Long](
      sql"""INSERT INTO user
                     SET name = ${request.name},
                         role = ${request.role},
                         company_id = ${request.companyId},
                         telphone = ${request.telphone},
                         birthday = ${request.birthday},
                         salary = ${request.salary}""")
  }
}
