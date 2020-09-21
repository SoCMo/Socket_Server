package Exception;

/**
 * program: EmAllException
 * description: 状态码
 * author: SoCMo
 * create: 2020/9/21
 */
public enum EmAllException implements CommonError {

    BAD_REQUEST(403, "错误的请求"),
    USER_OFFLINE(501, "用户不在线!"),
    SENDING_MESSAGE(502, "正在给该用户发送信息!"),
    USER_ONLINE(503, "用户已在线!");

    // 错误码
    private Integer code;

    // 错误信息
    private String msg;

    EmAllException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getErrCode() {
        return this.code;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.msg = errMsg;
        return this;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
