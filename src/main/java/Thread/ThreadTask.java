package Thread;

import Service.Router;
import Tool.ResultTool;
import Tool.SocketIOTool;
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

    public ThreadTask(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        while (true){
            String input = SocketIOTool.in(socket);
            if(input == null) SocketIOTool.out(socket, JSONObject.toJSONString(ResultTool.error(403, "接收不到内容!")));
            else {
                System.out.println("服务端接收到了:" + input);
                if (Router.Analysis(input, this.socket)) break;
            }
        }
    }
}
