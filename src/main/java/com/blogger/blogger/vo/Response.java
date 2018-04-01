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
    private Object boby;


}
