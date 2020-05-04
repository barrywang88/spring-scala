package com.github.barry.web.utils

import com.github.barry.core.sql.sql._
import com.github.barry.web.config.DatasourceConfig

/**
 * @ClassName PageHelper
 * @Description 分页工具类
 * @Author wangxuexing
 * @Date 2020/5/3 17:11
 * @Version 1.0
 */
object PageHelper {

  /**
   * 分页查询
   * @param codeBlock
   * @tparam T
   * @return
   */
  def queryWithTotal[T](codeBlock: => List[T]): (List[T], Int) = {
    (codeBlock, DatasourceConfig.myDatasource.queryInt(sql"""SELECT FOUND_ROWS()"""))
  }
}
