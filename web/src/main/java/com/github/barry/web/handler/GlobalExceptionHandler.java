package com.github.barry.web.handler;

import com.github.barry.core.domain.ResultModel;
import com.github.barry.core.exception.BaseCodeInterface;
import com.github.barry.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResultModel handle(HttpServletRequest request, Exception ex) {
        log.error("全局异常堆栈信息=" ,ex);
        return ResultModel.failure();
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(CommonException.class)
    public ResultModel handleBusinessException(HttpServletRequest request, CommonException ex) {
        log.error("全局异常堆栈信息=" ,ex);
        log.error("【全局异常信息】, 业务异常信息 = {}, requestUrl = {}, 请求时间 = {}", ex.getLocalizedMessage(), request.getRequestURL(), new Date());
        return ResultModel.failureWithMsg(ex);
    }


    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultModel handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.error("全局异常堆栈信息=" ,ex);
        log.error("【全局异常信息】, 不支持 【{}】, 请求方式 , 详细的异常信息 = {}, requestUrl = {}, 请求时间 = {}", ex.getMethod(), ex.getLocalizedMessage(),request.getRequestURL(), new Date());
        BaseCodeInterface baseCodeInterface = new BaseCodeInterface() {
           @Override
           public String getCode() {
               return "500";
           }

           @Override
           public String getMsg() {
               return "请求方式不支持";
           }
       };
        return ResultModel.failureWithMsg(baseCodeInterface);
    }

    /**
     * 参数类型不匹配
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResultModel handleTypeMismatchException(HttpServletRequest request, TypeMismatchException ex) {
        log.error("全局异常堆栈信息=" ,ex);
        log.error("【全局异常信息】, 参数类型不匹配,参数值 = {} ,类型应该为 = {}, 详细的异常信息 = {}, requestUrl = {}, 请求时间 = {}", ex.getValue(), ex.getRequiredType(), ex.getLocalizedMessage(),request.getRequestURL(), new Date());
        BaseCodeInterface baseCodeInterface = new BaseCodeInterface() {
            @Override
            public String getCode() {
                return "500";
            }

            @Override
            public String getMsg() {
                return "参数类型不匹配";
            }
        };
        return ResultModel.failureWithMsg(baseCodeInterface);
    }

    /**
     * 缺少参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultModel handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("全局异常堆栈信息=" ,ex);
        log.error("【全局异常信息】, 缺少必要请求参数 = {}, 详细的异常信息 = {}, requestUrl = {}, 请求时间 = {}", ex.getParameterName(), ex.getLocalizedMessage(),request.getRequestURL(), new Date());
        BaseCodeInterface baseCodeInterface = new BaseCodeInterface() {
            @Override
            public String getCode() {
                return "500";
            }

            @Override
            public String getMsg() {
                return "缺少必要请求参数";
            }
        };
        return ResultModel.failureWithMsg(baseCodeInterface);
    }

    /**
     * JSON转换异常
     */
    /*@ExceptionHandler(JSONException.class)
    public ResultModel handleJSONException(HttpServletRequest request, JSONException ex) {
        log.error("全局异常堆栈信息=" ,ex);
        log.error("【全局异常信息】, JSON转换异常信息 = {}, requestUrl = {}, 请求时间 = {}" , ex.getLocalizedMessage(), request.getRequestURL(), new Date());
        return ResultModel.failureWithMsg(SysCodeEnum.FAILURE);
    }*/
}
