package org.example.Socket_Server.Exception;

/**
 * program: CommonError
 * description: 统一报错接口
 * author: SoCMo
 * create: 2020/9/21
 */
public interface CommonError {
    Integer getErrCode();

    String getMsg();

    CommonError setErrMsg(String errMsg);
}
