package com.github.barry.web.config

import java.util.Properties

import com.alibaba.druid.pool.DruidDataSource
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.{Qualifier, Value}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement

object DatasourceConfig {
  var myDatasource: DataSource = _
}

/**
 * @ClassName DatasourceConfig
 * @Description Datasource初始化
 * @Author wangxuexing
 * @Date 2020/3/17 14:51
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
class DatasourceConfig( @Value("${spring.datasource.url}") url:String,
                        @Value("${spring.datasource.username}") username:String,
                        @Value("${spring.datasource.password}")  password:String,
                        @Value("${spring.datasource.driver-class-name}")    driverClass: String,
                        @Value("${spring.datasource.initialSize}")   initialSize: Int,
                        @Value("${spring.datasource.min-idle}") minIdle: Int,
                        @Value("${spring.datasource.maxActive}") maxActive: Int,
                        @Value("${spring.datasource.max-wait}")  maxWait: Int,
                        @Value("${spring.datasource.timeBetweenEvictionRunsMillis}") timeBetweenEvictionRunsMillis: Int,
                        @Value("${spring.datasource.minEvictableIdleTimeMillis}") minEvictableIdleTimeMillis: Int,
                        @Value("${spring.datasource.validationQuery}") validationQuery: String,
                        @Value("${spring.datasource.testWhileIdle}")  testWhileIdle: Boolean,
                        @Value("${spring.datasource.testOnBorrow}")  testOnBorrow: Boolean,
                        @Value("${spring.datasource.testOnReturn}")  testOnReturn: Boolean,
                        @Value("${spring.datasource.poolPreparedStatements}") poolPreparedStatements: Boolean,
                        @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}") maxPoolPreparedStatementPerConnectionSize: Int,
                        @Value("${spring.datasource.filters}")   filters: String,
                        @Value("{spring.datasource.connectionProperties}") connectionProperties:  Properties,
                        @Value("${spring.datasource.useGlobalDataSourceStat}") useGlobalDataSourceStat: Boolean,
                        @Value("${spring.datasources.druidLoginName}") druidLoginName: String,
                        @Value("${spring.datasources.druidPassword}")  druidPassword: String) {

  /**
   *@Description: 数据源,也可以使用这个注解 @ConfigurationProperties(prefix = "")
   *@return:
   */
  @Bean(name = Array("masterDataSource"))
  def masterDataSource: DataSource= {
    val druidDataSource: DruidDataSource = new DruidDataSource
    druidDataSource.setUrl(this.url)
    druidDataSource.setUsername(this.username)
    druidDataSource.setPassword(this.password)
    druidDataSource.setDriverClassName(this.driverClass)
    druidDataSource.setInitialSize(this.initialSize)
    druidDataSource.setMaxActive(this.maxActive)
    druidDataSource.setMinIdle(this.minIdle)
    druidDataSource.setMaxWait(this.maxWait)
    druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
    druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
    druidDataSource.setValidationQuery(this.validationQuery);
    druidDataSource.setTestOnBorrow(this.testOnBorrow)
    druidDataSource.setTestOnReturn(this.testOnReturn)
    druidDataSource.setTestWhileIdle(this.testWhileIdle)
    druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements)
    druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize)
    druidDataSource.setConnectProperties(this.connectionProperties)
    druidDataSource.setUseGlobalDataSourceStat(this.useGlobalDataSourceStat)
    druidDataSource.setFilters(this.filters)
    println(druidDataSource)
    druidDataSource
  }

  @Bean
  def transactionAwareDataSourceProxy(@Qualifier("masterDataSource") dataSource: DataSource): TransactionAwareDataSourceProxy ={
    val dataSourceProxy =  new TransactionAwareDataSourceProxy(dataSource)
    DatasourceConfig.myDatasource = dataSourceProxy
    dataSourceProxy
  }

}
