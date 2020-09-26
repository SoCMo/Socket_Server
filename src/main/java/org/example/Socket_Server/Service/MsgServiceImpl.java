package org.example.Socket_Server.Service;

import org.example.Socket_Server.Model.response.MsgResponse;
import org.example.Socket_Server.Thread.*;
import org.example.Socket_Server.Exception.*;

import org.example.Socket_Server.Model.response.Result;
import org.example.Socket_Server.Tool.ResultTool;
import org.example.Socket_Server.Tool.SocketIOTool;

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

            if (!name.equals("*")) {
                Socket socket = ThreadPool.userMap.getSocket(name);
                if (socket == null) {
                    throw new AllException(EmAllException.USER_OFFLINE);
                }

                SocketIOTool.out(socket, ResultTool.success(201, msgResponse).toString());
                ThreadPool.userMap.unlock(name);
            } else {
                for (Socket socket : ThreadPool.userMap.allUser()) {
                    SocketIOTool.out(socket, ResultTool.success(201, msgResponse).toString());
                    ThreadPool.userMap.unlock(name);
                }
            }

            return ResultTool.success();
        } catch (AllException e) {
            System.out.println(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }
}
