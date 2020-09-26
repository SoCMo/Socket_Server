package org.example.Socket_Server.Thread;

import org.example.Socket_Server.Service.Router;
import org.example.Socket_Server.Tool.ResultTool;
import org.example.Socket_Server.Tool.SocketIOTool;
import com.alibaba.fastjson.JSONObject;

import java.net.Socket;

/**
 * program: ThreadTest
 * description: 线程实现类
 * author: SoCMo
 * create: 2020/9/21 19:07
 */
public class ThreadTask implements Runnable{
    private Socket socket;
    private StringBuilder userName;

    public ThreadTask(Socket socket) {
        this.socket = socket;
        this.userName = new StringBuilder();
    }

    public void run() {
        while (true){
            String input = SocketIOTool.in(socket);
            if(input == null) SocketIOTool.out(socket, JSONObject.toJSONString(ResultTool.error(403, "接收不到内容!")));
            else {
                System.out.println("服务端接收到了:" + input);
                if (Router.Analysis(input, this.socket, this.userName)) break;
            }
        }
    }
}
