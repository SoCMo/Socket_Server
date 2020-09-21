import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

            out.write(msg.getBytes(StandardCharsets.UTF_8));
            out.flush();

            byte[] rsByte = readStream(in);

            if(rsByte!=null){
                rs = new String(rsByte, StandardCharsets.UTF_8);
            }


        } catch (Exception e) {
            System.out.println("tcpPost发送请求异常："+e.getMessage());
        }finally{
            System.out.println("tcpPost(rs)："+rs);
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

    /**
     * 读取输入流
     * @param in
     * @return
     */
    private static byte[] readStream(InputStream in){
        if(in==null){
            return null;
        }

        byte[] b = null;
        ByteArrayOutputStream outSteam = null;
        try {
            byte[] buffer = new byte[1024];
            outSteam = new ByteArrayOutputStream();

            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            b = outSteam.toByteArray();
        } catch (IOException e) {
            System.out.println("读取流信息异常"+e);
            e.printStackTrace();
        } finally{
            try {
                if(outSteam!=null){
                    outSteam.close();
                    outSteam = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}