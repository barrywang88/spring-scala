package com.github.barry.core.domain;

import com.github.barry.core.exception.BaseCodeInterface;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局Result结果存放，用于Service方法返回，外部服务提供统一结果
 */
public class ResultModel<T> implements Serializable {
    private static final long serialVersionUID = 3607161617741729654L;

    /**
     * 是否成功标识
     */
    protected boolean success;

    /**
     * 状态代码
     */
    protected String code;

    /**
     * 消息
     */
    protected String message;

    /**
     * k-v 消息键值对
     */
    protected Map<String, String> messageMap = new LinkedHashMap<String, String>();

    /**
     * 实体对象
     */
    private T data;

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("\\{\\}");

    public ResultModel() {
        this.success = true;
        this.code = "200";
    }

    public ResultModel(boolean success, String code, T data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public ResultModel(T data) {
        this();
        this.data = data;
    }

    public ResultModel(BaseCodeInterface base){
        this.code = base.getCode();
        this.message = base.getMsg();
    }

    public ResultModel(BaseCodeInterface base, T data){
        this.code = base.getCode();
        this.message = base.getMsg();
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static ResultModel failure() {
        ResultModel resultModel = new ResultModel(false, "500", null);
        return resultModel;
    }

    public ResultModel failure(String code, String message) {
        this.setSuccess(false);
        this.code = code;
        this.message = message;
        return this;
    }

    public static ResultModel failureWithMsg(BaseCodeInterface base) {
        ResultModel result = new ResultModel(base);
        return result;
    }

    public ResultModel addMessage(String key, String message) {
        this.messageMap.put(key, message);
        return this;
    }

    public static ResultModel success() {
        ResultModel resultModel = new ResultModel();
        resultModel.success = true;
        return resultModel;
    }

    public static ResultModel fail(String meaage) {
        ResultModel resultModel = new ResultModel();
        resultModel.success = false;
        resultModel.message = meaage;
        return resultModel;
    }

    /**
     * 消息格式化
     *
     * @param messageFormat
     * @param vars
     * @return
     */
    private String createMessageByFormat(String messageFormat, Object... vars) {
        Matcher m = MESSAGE_PATTERN.matcher(messageFormat);
        int varLength = vars.length;
        varLength = varLength > 5 ? 5 : varLength;
        int i = 0;
        while (m.find() && i < varLength) {
            messageFormat = messageFormat.replaceFirst(m.pattern().toString(), vars[i].toString());
            i++;
        }
        return messageFormat;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", messageMap=" + messageMap +
                ", data=" + data +
                '}';
    }
}
