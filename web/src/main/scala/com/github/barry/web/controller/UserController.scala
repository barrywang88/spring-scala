package com.github.barry.web.controller

import com.github.barry.web.domain.request.{UserQueryReq, UserSaveReq}
import com.github.barry.web.service.action.{DeleteUserAction, InsertUserAction, UpdateUserAction}
import com.github.barry.web.service.query.{UserDetail, UserQuery}
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
class UserController {

  /**
   * 分页用戶信息
   * @param param
   */
  @PostMapping(value = Array("/list"))
  def list(@RequestBody param: UserQueryReq)={
     new UserQuery(param).execute
  }

  /**
   * ready
   */
  @GetMapping(value = Array("/detail"))
  def detail(@RequestParam userId: Long)={
    new UserDetail(userId).execute
  }

  /**
   * 查詢用戶信息
   * @param param
   */
  @PostMapping(value = Array("/save"))
  def save(@RequestBody param: UserSaveReq)={
    new InsertUserAction(param).execute
  }

  /**
   * 更新用戶信息
   * @param param
   */
  @PostMapping(value = Array("/update"))
  def update(@RequestBody param: UserSaveReq)={
    new UpdateUserAction(param).execute
  }

  /**
   * 删除用戶信息
   * @param userId
   */
  @PostMapping(value = Array("/delete"))
  def delete(@RequestParam userId: Long)={
    new DeleteUserAction(userId).execute
  }

  /**
   * ready
   */
  @GetMapping(value = Array("/ready"))
  def ready={
    "ok"
  }
}
