package com.github.barry.web.controller

import com.github.barry.core.domain.ResultModel
import com.github.barry.web.domain.request.{UserQueryReq, UserSaveReq}
import com.github.barry.web.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

/**
 * @ClassName UserController
 * @Description 用戶Controller
 * @Author wangxuexing
 * @Date 2020/4/12 18:23
 * @Version 1.0
 */
@RestController
@RequestMapping(Array("/user"))
class UserController @Autowired()(val userService : UserService)  {

  /**
   * 分页用戶信息
   * @param param
   */
  @PostMapping(value = Array("/list"))
  def list(@RequestBody param: UserQueryReq)={
    userService.list(param)
  }

  /**
   * ready
   */
  @GetMapping(value = Array("/detail"))
  def detail(@RequestParam userId: Long)={
    userService.detail(userId)
  }

  /**
   * 查詢用戶信息
   * @param param
   */
  @PostMapping(value = Array("/save"))
  def save(@RequestBody param: UserSaveReq)={
    userService.insert(param)
  }

  /**
   * 更新用戶信息
   * @param param
   */
  @PostMapping(value = Array("/update"))
  def update(@RequestBody param: UserSaveReq)={
    userService.update(param)
  }

  /**
   * 删除用戶信息
   * @param userId
   */
  @PostMapping(value = Array("/delete"))
  def delete(@RequestParam userId: Long)={
    userService.delete(userId)
  }

  /**
   * ready
   */
  @GetMapping(value = Array("/ready"))
  def ready={
    new ResultModel[Unit]
  }
}
