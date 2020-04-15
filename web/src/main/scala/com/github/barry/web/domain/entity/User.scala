package com.github.barry.web.domain.entity

/**
  * @author dapeng-tool
  */
case class User (
   /**
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
salary : BigDecimal,

   /**
   * 数据变更时间
   */
updateTime : java.util.Date,

   /**
   * 创建时间
   */
created : java.util.Date,

   /**
   * 更新时间
   */
modified : java.util.Date,

   /**
   * 是否删除,0:否(no);1:是(yes)
   */
isDelete : Int,

   /**
   * 创建人
   */
createId : String,

   /**
   * 更新人
   */
updateId : String,

)
