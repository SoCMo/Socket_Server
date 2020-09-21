import Model.ConstRepository;
import Service.UserServiceImpl;
import Thread.*;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static void main(String[] args){
        try{
            BasicConfigurator.configure();
            ServerSocket serverSocket = new ServerSocket(ConstRepository.port);
            ThreadPool threadPool = new ThreadPool();
            logger.info("服务器启动成功！");
            Socket socket = null;

            while(null != (socket = serverSocket.accept())){
                threadPool.submit(new ThreadTask(socket));
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IO请求出错。");
        }
    }
}
