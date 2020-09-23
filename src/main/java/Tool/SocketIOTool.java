package Tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * program: SocketIO
 * description: Socket信息交互实现类
 * author: SoCMo
 * create: 2020/9/21 20:45
 */
public class SocketIOTool {

    public static void out(Socket socket, String msg) {
        try {
            BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(msg);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String in(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String str;

//            StringBuilder temp = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                try {
                    Object object = JSON.parse(str);
                } catch (JSONException e) {
                    continue;
                }
                return str;
            }

        } catch (IOException e) {
            System.out.println("与客户端信息交互失败。接收不到内容。");
            return null;
        }

        return null;
    }
}
