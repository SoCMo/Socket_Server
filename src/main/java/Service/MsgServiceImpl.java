package Service;

import Thread.*;
import Exception.*;

import Model.response.Result;
import Tool.ResultTool;
import Tool.SocketIOTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * program: MsgServiceImpl
 * description: 消息传递实现类
 * author: SoCMo
 * create: 2020/9/21 20:22
 */
public class MsgServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static Result sendMsg(String name, String msg){
        try {
            Socket socket = ThreadPool.userMap.getSocket(name);
            if(socket == null){
                throw new AllException(EmAllException.USER_OFFLINE);
            }

            SocketIOTool.out(socket, msg);
            ThreadPool.userMap.unlock(name);
            return ResultTool.success();
        } catch (AllException e) {
            logger.error(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }
}
