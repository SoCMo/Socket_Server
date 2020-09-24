package Model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * program: MsgResponse
 * description: 消息发送类
 * author: SoCMo
 * create: 2020/9/24 12:49
 */
@Data
@AllArgsConstructor
public class MsgResponse {
    private String message;

    private String from;

//    @Override
//    public String toString() {
//        return "{\"message\":\"" + (message != null ? message : "") + "\",\"from\":\"" + (from != null ? from : "")
//                + "\"}";
//    }
}
