package com.github.barry.web.service.impl.query.sql

import com.github.barry.core.`implicit`.Implicits._
import com.github.barry.core.sql.SQLWithArgs
import com.github.barry.core.sql.sql._
import com.github.barry.web.config.DatasourceConfig
import com.github.barry.web.domain.entity.User
import com.github.barry.web.domain.request.UserQueryReq
import com.github.barry.web.domain.response.UserListResp
import com.github.barry.web.utils.PageHelper

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
  def list(request: UserQueryReq)= {
    val select =
      sql"""
          SELECT SQL_CALC_FOUND_ROWS id, company_id, name, role, telphone, birthday, salary
          FROM user
          WHERE is_delete = 0
         """
    //可选条件拼接
    val optionSql = List[SQLWithArgs](
      request.role.optional(x => sql""" AND role = $x"""),
      request.name.optional(x => sql"""  AND name like concat('%',$x,'%')"""),
      request.telphone.optional(x => sql"""  AND telphone = $x"""),
      request.birthday.optional(x => sql"""  AND birthday = $x""")
    ).reduceLeft(_+_)

    val orderLimit =
      sql""" ORDER BY modified DESC
                LIMIT ${(request.getCurrentPage-1)*request.getPageSize}, ${request.getPageSize}"""
    PageHelper.queryWithTotal(DatasourceConfig.myDatasource.rows[UserListResp](select+optionSql+orderLimit))
  }

  /**
   * 获取用户详情
   * @param userId
   * @return
   */
  def detail(userId: Long)= {
    val select =
      sql"""
          SELECT *
          FROM user
          WHERE id = $userId
         """
    DatasourceConfig.myDatasource.row[User](select)
  }
}
