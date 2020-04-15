package com.github.barry.core.utils

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

/**
  * 类功能描述: 配置文件读取
  *
  * Create by Barry
  *
  * @version 1.0.0
  * Create on 2018/6/25.
  */
object ConfigReader {

  /**
    * 读取配置文件
    * @return
    */
  def getConfig(): Config ={
    val configFileName = "config.conf"
    //读取Jar包内resources下的config.conf文件
    val configFile = s"${System.getProperty("user.dir")}${File.separator}core${File.separator}target${File.separator}classes${File.separator}${configFileName}"
    println(s"config file path:${configFile}")
    var databaseConfig: Config = null
    val file = new File(configFile)
    //读取Jar包外, 与Jar包平级目录/config/下的config.conf文件文件
    if(file.exists()){
      databaseConfig = ConfigFactory.parseFile(file)
    } else {
      databaseConfig = ConfigFactory.load(ConfigReader.getClass().getClassLoader, configFileName)
    }
    databaseConfig
  }
}
