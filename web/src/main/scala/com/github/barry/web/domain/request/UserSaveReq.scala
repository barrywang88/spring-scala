package com.github.barry.web.domain.request

/**
 * @ClassName UserSaveReq
 * @Description TODO
 * @Author wangxuexing
 * @Date 2020/4/12 22:15
 * @Version 1.0
 */
case class UserSaveReq(
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
                        birthday : String,

                        /**
                         * 工资
                         */
                        salary : BigDecimal)
