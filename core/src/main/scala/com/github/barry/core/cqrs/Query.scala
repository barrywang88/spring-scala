package com.github.barry.core.cqrs

/**
 * @ClassName Query
 * @Description Query定义
 * Command Query Responsibility Segregation，CQRS
 * @Author wangxuexing
 * @Date 2020/3/12 10:53
 * @Version 1.0
 */
trait Query[Output] extends Action[Output]
