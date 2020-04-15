package com.github.barry.web.domain.response

/**
 * @ClassName UserListResp
 * @Description TODO
 * @Author wangxuexing
 * @Date 2020/4/12 18:36
 * @Version 1.0
 */
case class UserListResp(   /**
                            * 数据库自增编号
                            */
                           id : Long,

                           /**
                            * 金融机构ID
                            */
                           companyId : Long,

                           /**
                            * 用户角色,1:管理员(admin);2:部门经理(manager);3:普通员工(member)
                            */
                           role : Int,

                           /**
                            * 用户名
                            */
                           name : String,

                           /**
                            * 电话号码
                            */
                           telphone : String,

                           /**
                            * 生日
                            */
                           birthday : java.util.Date,

                           /**
                            * 工资
                            */
                           salary : BigDecimal)
