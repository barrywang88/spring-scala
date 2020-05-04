package com.github.barry.web.domain.request

import com.github.barry.core.domain.PageQuery

/**
 * @ClassName UserQueryReq
 * @Description TODO
 * @Author wangxuexing
 * @Date 2020/4/12 18:35
 * @Version 1.0
 */
case class UserQueryReq(  /**
                           * 用户角色,1:管理员(admin);2:部门经理(manager);3:普通员工(member)
                           */
                          role : Option[Int],

                          /**
                           * 用户名
                           */
                          name : Option[String],

                          /**
                           * 电话号码
                           */
                          telphone : Option[String],

                          /**
                           * 生日
                           */
                          birthday : Option[String],
                       ) extends PageQuery
