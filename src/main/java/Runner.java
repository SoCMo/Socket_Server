import Model.ConstRepository;
import Thread.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            ThreadPool threadPool = new ThreadPool();
            System.out.println("服务器启动成功！");
            Socket socket;

            while(null != (socket = serverSocket.accept())){
                threadPool.submit(new ThreadTask(socket));
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO请求出错。");
        }
    }
}
