package Service;

import Model.response.MsgResponse;
import Thread.*;
import Exception.*;

import Model.response.Result;
import Tool.ResultTool;
import Tool.SocketIOTool;

import java.net.Socket;

/**
 * program: MsgServiceImpl
 * description: 消息传递实现类
 * author: SoCMo
 * create: 2020/9/21 20:22
 */
public class MsgServiceImpl {
    public static Result sendMsg(String name, String msg, String from) {
        try {
            if (from == null || from.isBlank()) return ResultTool.error(403, "您还未登录");

            MsgResponse msgResponse = new MsgResponse(msg, from);
            Socket socket = ThreadPool.userMap.getSocket(name);
            if (socket == null) {
                throw new AllException(EmAllException.USER_OFFLINE);
            }

            SocketIOTool.out(socket, ResultTool.success(201, msgResponse).toString());
            ThreadPool.userMap.unlock(name);
            return ResultTool.success();
        } catch (AllException e) {
            System.out.println(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }
}
