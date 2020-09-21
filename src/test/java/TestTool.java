import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestTool{
    public static void main(String[] args) {
        tcpPost("127.0.0.1", "8084", "#stop");
    }

    /**
     * 发送socket请求
     * @param clientIp
     * @param clientPort
     * @param msg
     * @return
     */
    private static synchronized String tcpPost(String clientIp,String clientPort,String msg){
        String rs = "";

        if(clientIp==null||"".equals(clientIp)||clientPort==null||"".equals(clientPort)){
            System.out.println("Ip或端口不存在...");
            return null;
        }

        int clientPortInt = Integer.parseInt(clientPort);

        System.out.println("clientIp："+clientIp+" clientPort："+clientPort);

        Socket s = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            s = new Socket(clientIp, clientPortInt);
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            s.setSoTimeout(60*1000);
            s.setKeepAlive(true);
            out = s.getOutputStream();
            in = s.getInputStream();

            //准备报文msg
            System.out.println("准备发送报文："+msg);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            //向服务器端发送一条消息
            bw.write("#stop");
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String mess = br.readLine();
            System.out.println("服务器返回："+mess);

        } catch (Exception e) {
            System.out.println("tcpPost发送请求异常："+e.getMessage());
        }finally{
            try {
                if(out!=null){
                    out.close();
                    out = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
                if(s!=null){
                    s.close();
                    s = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rs;
    }
}