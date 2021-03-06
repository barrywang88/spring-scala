package com.github.barry.web.service.impl.action.sql

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
  def insert(request: UserSaveReq): Long ={
    DatasourceConfig.myDatasource.generateKey[Long](
      sql"""INSERT INTO user
                     SET name = ${request.name},
                         role = ${request.role},
                         company_id = ${request.companyId},
                         telphone = ${request.telphone},
                         birthday = ${request.birthday},
                         salary = ${request.salary},
                         update_time = now(),
                         is_delete = 0
                         """)
  }

  /**
   * 更新用户信息
   * @param request
   * @return
   */
  def update(request: UserSaveReq): Int ={
    DatasourceConfig.myDatasource.executeUpdate(
      sql"""UPDATE user
                     SET name = ${request.name},
                         role = ${request.role},
                         company_id = ${request.companyId},
                         telphone = ${request.telphone},
                         birthday = ${request.birthday},
                         salary = ${request.salary},
                         update_time = now(),
                         is_delete = 0
                    WHERE id = ${request.id}
                         """)

  }

  /**
   * 删除用户信息
   * @param userId
   * @return
   */
  def delete(userId: Long): Int ={
    DatasourceConfig.myDatasource.executeUpdate(
      sql""" DELETE
                    FROM
                      user
                    WHERE
                      id = $userId""")

  }
}
