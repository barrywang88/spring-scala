package com.github.barry.web.service

import com.github.barry.core.domain.ResultModel
import com.github.barry.web.domain.entity.User
import com.github.barry.web.domain.request.{UserQueryReq, UserSaveReq}
import com.github.barry.web.domain.response.UserListResp
import com.github.pagehelper.PageInfo
import org.springframework.web.bind.annotation.RequestParam

/**
 * @ClassName UserService
 * @Description 用户接口
 * @Author wangxuexing
 * @Date 2020/5/4 21:27
 * @Version 1.0
 */
trait UserService {
  /**
   * 分页查询用户列表
   * @param request
   * @return
   */
  def list(request: UserQueryReq): ResultModel[PageInfo[UserListResp]]

  /**
   * 获取用户详情
   * @param userId
   * @return
   */
  def detail(userId: Long): ResultModel[Option[User]]

  /**
   * 添加用戶信息
   * @param request
   */
  def insert(request: UserSaveReq): ResultModel[Long]

  /**
   * 更新用戶信息
   * @param request
   */
  def update(request: UserSaveReq): ResultModel[Int]

  /**
   * 删除用戶信息
   * @param userId
   */
  def delete(userId: Long): ResultModel[Int]
}
