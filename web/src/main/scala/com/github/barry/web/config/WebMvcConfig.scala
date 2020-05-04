package com.github.barry.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.{ByteArrayHttpMessageConverter, HttpMessageConverter}
import org.springframework.http.{HttpHeaders, MediaType}
import org.springframework.web.servlet.config.annotation.{ContentNegotiationConfigurer, CorsRegistry, DefaultServletHandlerConfigurer, WebMvcConfigurationSupport}

/**
 * @ClassName WebMvcConfig
 * @Description web过滤器
 * @Author wangxuexing
 * @Date 2020/5/2 16:40
 * @Version 1.0
 */
@Configuration
class WebMvcConfig extends WebMvcConfigurationSupport{

  override def configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer): Unit = {
    configurer.enable()
  }

  override def configureContentNegotiation(configurer: ContentNegotiationConfigurer): Unit = {
    configurer.mediaType("json", MediaType.APPLICATION_JSON)
  }

  override def addCorsMappings(registry: CorsRegistry): Unit = {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedHeaders("*")
      .allowedMethods("*")
      .allowCredentials(true)
      .exposedHeaders(HttpHeaders.SET_COOKIE)
  }

  override def configureMessageConverters(converters: java.util.List[HttpMessageConverter[_]]): Unit = { //添加二进制流导出转换
    converters.add(new ByteArrayHttpMessageConverter)
    //json 字符串转scala case class
    converters.add(new MappingJackson2HttpMessageConverter(ObjectMapperSingleton.getObjectMapperInstance))
    // 新增 x-www-form-urlencoded 消息转换器
    converters.add(new FormMappingJackson2HttpMessageConverter)
  }
}
