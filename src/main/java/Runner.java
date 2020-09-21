import Model.ConstRepository;

import java.io.*;
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String temp = null;
                StringBuilder getInfo = new StringBuilder();
                while(null != (temp = bufferedReader.readLine())){
                    getInfo.append(temp);
                }

                System.out.println("服务器收到了" + getInfo.toString());

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("get it\n");
                bufferedWriter.flush();

                socket.close();
                if(getInfo.equals("#stop")) break;
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO请求出错。");
        }
    }
}
