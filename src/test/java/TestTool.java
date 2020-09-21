import Model.ConstRepository;
import Tool.SocketIOTool;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestTool{
    public static void main(String[] args) throws IOException {
        String readyPut = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Socket socket = tcpPost(ConstRepository.address, String.valueOf(ConstRepository.port));
        while(!"done".equals(readyPut = bufferedReader.readLine())){
            System.out.println("发送了:" + readyPut);

            assert socket != null;
            SocketIOTool.out(socket, readyPut);
            System.out.println("服务器返回:" + SocketIOTool.in(socket));
        }
    }

    /**
     * 发送socket请求
     * @param clientIp
     * @param clientPort
     * @return
     */
    private static synchronized Socket tcpPost(String clientIp,String clientPort){
        try {
            if(clientIp==null||"".equals(clientIp)||clientPort==null||"".equals(clientPort)){
                System.out.println("Ip或端口不存在...");
                return null;
            }

            int clientPortInt = Integer.parseInt(clientPort);

            System.out.println("clientIp："+clientIp+" clientPort："+clientPort);

            Socket s = new Socket(clientIp, clientPortInt);
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            s.setSoTimeout(60*1000);
            s.setKeepAlive(true);
            return s;

        } catch (Exception e) {
            System.out.println("tcpPost发送请求异常："+e.getMessage());
        }
        return null;
    }
}