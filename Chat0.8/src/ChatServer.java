import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {
        boolean started = false;
        try {
            ServerSocket ss = new ServerSocket(8888);
            started = true;
            while (started) {
                boolean bConnected = false;
                Socket s = ss.accept();
                System.out.println("a client connected");
                bConnected = true;
                DataInputStream dis = new DataInputStream(s.getInputStream()); //Chat0.7 Server增加接收流  接收Client发送的信息
                while (bConnected){
                    String str = dis.readUTF();
                    System.out.println(str); //并打印
                }
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
