package com.github.barry.web.service.query.sql

import com.github.barry.core.`implicit`.Implicits._
import com.github.barry.core.sql.SQLWithArgs
import com.github.barry.core.sql.sql._
import com.github.barry.web.config.DatasourceConfig
import com.github.barry.web.domain.request.UserQueryReq
import com.github.barry.web.domain.response.UserListResp

/**
 * @ClassName UserActionSql
 * @Description 用户查询SQL
 * @Author wangxuexing
 * @Date 2020/4/12 20:12
 * @Version 1.0
 */
object UserQuerySql {

  /**
   * 查询用户列表
   * @param request
   * @return
   */
  def listUser(request: UserQueryReq)= {
    val select =
      sql"""
          select id, company_id, name, role, telphone, birthday
          from user
         """
    //可选条件拼接
    val optionSql = List[SQLWithArgs](
      request.role.optional(x => sql""" AND role = $x"""),
      request.name.optional(x => sql"""  AND name like concat('%',$x,'%')"""),
      request.telphone.optional(x => sql"""  AND telphone = $x"""),
      request.birthday.optional(x => sql"""  AND birthday = $x""")
    ).reduceLeft(_+_)
    DatasourceConfig.myDatasource.rows[UserListResp](select+optionSql)
  }
}
