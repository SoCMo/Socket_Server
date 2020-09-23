package Thread;

import Exception.*;
import Tool.ResultTool;
import Tool.SocketIOTool;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * program: UserMap
 * description: 当前在线用户相关操作的实现类
 * author: SoCMo
 * create: 2020/9/22 16:26
 */
public class UserMap {
    private Map<String, Socket> socketHashMap;
    private Map<String, Object> lockedUser;

    public UserMap(){
        socketHashMap = new ConcurrentHashMap<>();
        lockedUser = new ConcurrentHashMap<>();
    }

    public boolean contains(String name){
        return socketHashMap.containsKey(name);
    }

    public void login(String name, Socket socket) throws AllException {
        if(socketHashMap.containsKey(name)) throw new AllException(EmAllException.USER_ONLINE);
        socketHashMap.put(name, socket);
    }

    public void logout(String name) throws IOException, AllException {
        if(lockedUser.containsKey(name)) throw new AllException(EmAllException.SENDING_MESSAGE);
        if(!socketHashMap.containsKey(name)) throw new AllException(EmAllException.USER_OFFLINE);
        SocketIOTool.out(socketHashMap.get(name), ResultTool.success().toString());
        socketHashMap.get(name).close();
        socketHashMap.remove(name);
    }

    public boolean lock(String name){
        if(socketHashMap.containsKey(name)){
            lockedUser.put(name, 1);
            return true;
        } return false;
    }

    public boolean unlock(String name){
        if(!socketHashMap.containsKey(name)){
            lockedUser.remove(name);
        } return false;
    }

    public Socket getSocket(String name) throws AllException {
        if(!socketHashMap.containsKey(name)) throw new AllException(EmAllException.USER_OFFLINE);
        if(lockedUser.containsKey(name)) throw new AllException(EmAllException.SENDING_MESSAGE);
        lock(name);
        return socketHashMap.get(name);
    }
}
