package org.example.Socket_Server.Model.response;

import lombok.Data;

/**
 * program: Result
 * description: 统一返回类
 * author: SoCMo
 * create: 2020/9/21
 */
@Data
public class Result<T> {
    //状态码
    private Integer code;
    //错误数据
    private String message;
    //返回信息
    private T data;

    @Override
    public String toString() {
        return "{\"code\":\"" + (code != null ? code : "") + "\",\"message\":\"" + (message != null ? message : "")
                + "\",\"data\":\"" + (data != null ? data : "") + "\"}";
    }
}
