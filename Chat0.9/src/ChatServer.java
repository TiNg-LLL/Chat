import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {
        boolean started = false;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream dis = null;
        try {
            ss = new ServerSocket(8888);
        } catch (BindException e) {
            System.out.println("宽口被占用");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            started = true;
            while (started) {
                boolean bConnected = false;
                s = ss.accept();
                System.out.println("a client connected");
                bConnected = true;
                dis = new DataInputStream(s.getInputStream()); //Chat0.7 Server增加接收流  接收Client发送的信息
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str); //并打印
                }
                //dis.close();
            }
        } catch (EOFException e) {
            System.out.println("Client closed");
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Client closed");
        } finally {
            try {
                if (dis != null) dis.close();
                if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
