package Tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * program: SocketIO
 * description: Socket信息交互实现类
 * author: SoCMo
 * create: 2020/9/21 20:45
 */
public class SocketIOTool {
    private static Logger logger = LoggerFactory.getLogger(SocketIOTool.class);

    public static void out(Socket socket, String msg) {
        try {
            BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(msg);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static String in(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder getInfo = new StringBuilder();
            String str;

            while ((str = bufferedReader.readLine()) != null) {
                return str;
            }

        } catch (IOException e) {
            logger.error("与客户端信息交互失败。接收不到内容。");
            return null;
        }

        return null;
    }
}
