package com.github.barry.web

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.{ComponentScan, EnableAspectJAutoProxy, FilterType, ImportResource}
import org.springframework.scheduling.annotation.{EnableAsync, EnableScheduling}

/**
 * @ClassName SpringBootScalaIntegration
 * @Description SpringBoot启动类
 * @Author wangxuexing
 * @Date 2020/3/17 10:32
 * @Version 1.0
 */
@ComponentScan(value = Array(
  "com.github.barry.web.controller",
))
@EntityScan(value = Array(
  "com.github.barry.web.domain"
))
@EnableAutoConfiguration(exclude=Array{classOf[DruidDataSourceAutoConfigure]})
@ServletComponentScan
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAsync
class SpringBootScalaIntegration

object SpringBootScalaIntegration extends App{
  SpringApplication.run(classOf[SpringBootScalaIntegration])
}
