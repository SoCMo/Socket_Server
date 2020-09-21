import Model.ConstRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * program: Runner
 * description: 启动socket服务层
 * author: SoCMo
 * create: 2020/9/21
 */
public class Runner {
    public static void main(String[] args){
        try{
            ServerSocket serverSocket = new ServerSocket(ConstRepository.port);
            System.out.println("服务器启动成功！");

            Socket socket = null;
            while(null != (socket = serverSocket.accept())){
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];

                StringBuilder stringBuilder = new StringBuilder();
                int len;
                while (-1 != (len = inputStream.read(bytes))){
                    stringBuilder.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                }
                inputStream.close();

                OutputStream out = socket.getOutputStream();
                out.write("I get it!".getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
                socket.close();

                if(stringBuilder.toString().equals("#stop")) break;
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO请求出错。");
        }
    }
}
