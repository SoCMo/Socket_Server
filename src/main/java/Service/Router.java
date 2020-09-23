package Service;

import Model.response.Result;
import Tool.ResultTool;
import Tool.SocketIOTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Dictionary;

/**
 * program: Service
 * description: Socket信息处理层
 * author: SoCMo
 * create: 2020/9/21
 */
public class Router {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static boolean Analysis(String input, Socket socket){
        boolean isQuit = false;
        Result result = ResultTool.error(500, "未知错误");
        try {
            JSONObject json = JSON.parseObject(input);
            switch (json.getString("method")){
                case "login":
                    result = UserServiceImpl.Login(json.getString("name"), socket);
                    break;
                case "logout":
                    result = UserServiceImpl.Logout(json.getString("name"));
                    isQuit = true;
                    break;
                case "sendMsg":
                    result = MsgServiceImpl.sendMsg(json.getString("name"), json.getString("msg"));
                    break;
                default:
                    result = ResultTool.error(403, "未找到对应的方法!");
            }
        }catch (JSONException e){
            logger.error(e.getMessage());
            result = ResultTool.error(403, "发送的信息不是Json格式");
        }finally {
            SocketIOTool.out(socket, result.toString());
        }
        return isQuit;
    }

}