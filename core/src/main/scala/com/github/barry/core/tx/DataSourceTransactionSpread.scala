package com.github.barry.core.tx

import java.sql.Connection

import javax.sql.DataSource
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionDefinition

/**
 * @ClassName DataSourceTransactionSpread
 * @Description 除了只读数据库，其他都添加事物启动
 * @Author wangxuexing
 * @Date 2020/5/4 17:56
 * @Version 1.0
 */
class DataSourceTransactionSpread(dataSource: DataSource) extends DataSourceTransactionManager{
  override protected def prepareTransactionalConnection(con: Connection, definition: TransactionDefinition): Unit = {
    if (isEnforceReadOnly && definition.isReadOnly) {
      super.prepareTransactionalConnection(con, definition)
      logger.debug("CONNECTION NOT START TRANSACTION ")
    } else {
      val stmt = con.createStatement
      try {
        logger.debug("CONNECTION START TRANSACTION START")
        stmt.execute("START TRANSACTION")
        logger.debug("CONNECTION START TRANSACTION")
      } finally stmt.close()
    }
  }
}
