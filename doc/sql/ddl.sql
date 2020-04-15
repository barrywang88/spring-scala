CREATE TABLE user  (
      id  bigint  NOT NULL   AUTO_INCREMENT    COMMENT '数据库自增编号'
    ,  company_id  bigint  NOT NULL     COMMENT '金融机构ID'
    ,  role  int  NOT NULL     COMMENT '用户角色,1:管理员(admin);2:部门经理(manager);3:普通员工(member)'
    ,  name  varchar(20)  NOT NULL     COMMENT '用户名'
    ,  telphone  varchar(16)  NOT NULL     COMMENT '电话号码'
    ,  birthday  date  NOT NULL     COMMENT '生日'
    ,  salary  decimal(20,2)  NOT NULL     COMMENT '工资'
    ,  update_time  datetime  NOT NULL     COMMENT '数据变更时间'
    ,  created  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL     COMMENT '创建时间'
    ,  modified  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL     COMMENT '更新时间'
    ,  is_delete  int DEFAULT 1 NOT NULL     COMMENT '是否删除,0:否(no);1:是(yes)'
    ,  create_id  varchar(20) DEFAULT '-1' NOT NULL     COMMENT '创建人'
    ,  update_id  varchar(20) DEFAULT '-1' NOT NULL     COMMENT '更新人'
    , PRIMARY KEY ( ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';