package Service;
import Exception.*;
import Thread.*;
import Model.response.Result;
import Tool.ResultTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * program: UserServiceImpl
 * description: 用户身份实现类
 * author: SoCMo
 * create: 2020/9/21 19:53
 */
public class UserServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static Result Login(String name, Socket socket){
        try {
            if(name.isBlank()) {
                throw new AllException(EmAllException.BAD_REQUEST);
            }

            if(ThreadPool.userMap.contains(name)){
                return ResultTool.error(401, "你已经登录！");
            }else {
                ThreadPool.userMap.login(name, socket);
                logger.info(name + "登录成功");
                return ResultTool.success();
            }
        } catch (AllException e) {
            logger.error(name + "登录失败,因为:" + e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }

    public static Result Logout(String name){
        try {
            if(name.isBlank()) {
                throw new AllException(EmAllException.BAD_REQUEST);
            }

            if(!ThreadPool.userMap.contains(name)){
                return ResultTool.error(401, "该用户未登录！");
            }else {
                ThreadPool.userMap.logout(name);
                return ResultTool.success();
            }
        } catch (AllException e) {
            logger.error(e.getMsg());
            return ResultTool.error(e.getErrCode(), e.getMsg());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResultTool.error(500, "系统运行错误!");
        }
    }
}
