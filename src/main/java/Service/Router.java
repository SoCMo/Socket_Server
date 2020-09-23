package Service;

import Model.response.Result;
import Tool.ResultTool;
import Tool.SocketIOTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.net.Socket;

/**
 * program: Service
 * description: Socket信息处理层
 * author: SoCMo
 * create: 2020/9/21
 */
public class Router {
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
            System.out.println(e.getMessage());
            result = ResultTool.error(403, "发送的信息不是Json格式");
        }finally {
            SocketIOTool.out(socket, result.toString());
        }
        return isQuit;
    }

}
