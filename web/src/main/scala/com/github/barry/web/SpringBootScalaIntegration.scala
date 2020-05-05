package com.github.barry.web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.{ComponentScan, EnableAspectJAutoProxy}
import org.springframework.scheduling.annotation.{EnableAsync, EnableScheduling}
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @ClassName SpringBootScalaIntegration
 * @Description SpringBoot启动类
 * @Author wangxuexing
 * @Date 2020/3/17 10:32
 * @Version 1.0
 */
@ServletComponentScan
@ComponentScan(basePackages = Array("com.github.barry"))
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
class SpringBootScalaIntegration extends SpringBootServletInitializer

object SpringBootScalaIntegration extends App{
  SpringApplication.run(classOf[SpringBootScalaIntegration])
}
