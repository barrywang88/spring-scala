package com.github.barry.web.controller

import com.github.barry.web.domain.request.{UserQueryReq, UserSaveReq}
import com.github.barry.web.service.action.InsertUserAction
import com.github.barry.web.service.query.UserQuery
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

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
   * 查詢用戶信息
   * @param param
   */
  @PostMapping(value = Array("/list"))
  def list(@RequestBody param: UserQueryReq)={
     new UserQuery(param).execute
  }

  /**
   * 查詢用戶信息
   * @param param
   */
  @PostMapping(value = Array("/save"))
  def save(@RequestBody param: UserSaveReq)={
    new InsertUserAction(param).execute
  }
}
