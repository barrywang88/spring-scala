package com.github.barry.web.tx;


import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @ClassName Db1Transactional
 * @Description 数据库1事物注解-使用默认事物管理器
 * @Author wangxuexing
 * @Date 2020/5/4 16:19
 * @Version 1.0
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional
public @interface Db1Transactional {
}
