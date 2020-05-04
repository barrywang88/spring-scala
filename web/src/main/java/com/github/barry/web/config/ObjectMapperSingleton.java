package com.github.barry.web.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName ObjectMapperSingleton
 * @Description ObjectMapper单例
 * @Author wangxuexing
 * @Date 2020/4/8 16:18
 * @Version 1.0
 */
public class ObjectMapperSingleton {
    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.registerModule(new com.fasterxml.jackson.module.scala.DefaultScalaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ObjectMapperSingleton(){}

    public static ObjectMapper getObjectMapperInstance(){
        return mapper;
    }
}
