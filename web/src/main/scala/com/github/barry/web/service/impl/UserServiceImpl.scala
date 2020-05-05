package com.github.barry.web.service.impl

import com.github.barry.core.domain.ResultModel
import com.github.barry.web.config.MyTransactionManager
import com.github.barry.web.domain.entity.User
import com.github.barry.web.domain.request.{UserQueryReq, UserSaveReq}
import com.github.barry.web.domain.response.UserListResp
import com.github.barry.web.service.UserService
import com.github.barry.web.service.impl.action.{DeleteUserAction, InsertUserAction, UpdateUserAction}
import com.github.barry.web.service.impl.query.{UserDetail, UserQuery}
import com.github.pagehelper.PageInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.{TransactionDefinition, TransactionStatus}
import org.springframework.transaction.annotation.Transactional

import scala.util.control.NonFatal

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author wangxuexing
 * @Date 2020/5/4 21:24
 * @Version 1.0
 */
@Service
class UserServiceImpl extends UserService {

  /**
   * 添加用户信息
   * @param request
   * @return
   */
  @Transactional(rollbackFor = Array(classOf[Throwable]))//声明式事物管理
  override def insert(request: UserSaveReq): ResultModel[Long] ={
    /*编程式事物管理使用
    var result = new ResultModel[Long]
    val status = MyTransactionManager.getTransactionStatus(TransactionDefinition.PROPAGATION_REQUIRED)
    try{
      result = new InsertUserAction(request).execute
      MyTransactionManager.commit(status)
    } catch {
      case NonFatal(e) => MyTransactionManager.rollBack(status)
    }
    result*/
    new InsertUserAction(request).execute
  }

  /**
   * 分页查询用户列表
   *
   * @param request
   * @return
   */
  override def list(request: UserQueryReq): ResultModel[PageInfo[UserListResp]] = {
    new UserQuery(request).execute
  }

  /**
   * 获取用户详情
   *
   * @param userId
   * @return
   */
  override def detail(userId: Long): ResultModel[Option[User]] = {
    new UserDetail(userId).execute
  }

  /**
   * 更新用戶信息
   *
   * @param request
   */
  @Transactional(rollbackFor = Array(classOf[Throwable]))
  override def update(request: UserSaveReq): ResultModel[Int] = {
    new UpdateUserAction(request).execute
  }

  /**
   * 删除用戶信息
   *
   * @param userId
   */
  @Transactional(rollbackFor = Array(classOf[Throwable]))
  override def delete(userId: Long): ResultModel[Int] = {
    new DeleteUserAction(userId).execute
  }
}
