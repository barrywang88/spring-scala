package com.github.barry.web.config

import javax.annotation.Resource
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.{PlatformTransactionManager, TransactionDefinition, TransactionStatus}
import org.springframework.transaction.support.{DefaultTransactionDefinition, TransactionTemplate}

/**
 * @ClassName ProgramTransactionManager
 * @Description 编程式事物管理器
 * @Author wangxuexing
 * @Date 2020/5/5 16:57
 * @Version 1.0
 */
object MyTransactionManager {
  var transactionManager: PlatformTransactionManager = _

  //以下定义编程式事物管理器
  //////////////////////编程式事物管理器 start//////////////////////////////////
  def getTransactionTemplate: TransactionTemplate ={
    new TransactionTemplate(transactionManager)
  }

  def getTransactionStatus() = {
    val definition = new DefaultTransactionDefinition
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED)
    transactionManager.getTransaction(definition)
  }

  def getTransactionStatus(propagation: Int) = {
    val definition = new DefaultTransactionDefinition
    definition.setPropagationBehavior(propagation)
    transactionManager.getTransaction(definition)
  }

  def commit(status: TransactionStatus): Unit = {
    transactionManager.commit(status)
  }

  def rollBack(status: TransactionStatus): Unit = {
    transactionManager.rollback(status)
  }
  //////////////////////编程式事物管理器 end//////////////////////////////////
}

@Configuration
@EnableTransactionManagement
class MyTransactionManager{

  /**
   * 定义声明式事物管理器
   * @param dataSource
   * @return
   */
  @Resource(name = "transactionAwareDataSourceProxy")
  @Bean(name = Array("transactionManager"))
  def transactionManager(@Qualifier("masterDataSource") dataSource: DataSource): PlatformTransactionManager = {
    MyTransactionManager.transactionManager = new DataSourceTransactionManager(dataSource)
    MyTransactionManager.transactionManager
  }
}
