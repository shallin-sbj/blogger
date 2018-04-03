package com.blogger.blogger.vo;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 返回对象信息
 */
@Data
public class Response {
    /**
     * 相应状态
     */
    private boolean success;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object body;

    /**
     * 响应处理是否成功
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 响应处理的消息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 响应处理的返回内容
     */
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }


}
