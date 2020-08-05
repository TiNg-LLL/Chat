import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    boolean started = false;
    ServerSocket ss = null;

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(8888);
            started = true;
        } catch (BindException e) {
            System.out.println("宽口被占用");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (started) {
                //boolean bConnected = false;
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("a client connected");
                new Thread(c).start();
                //dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Client closed");
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            //dis = new DataInputStream(s.getInputStream()); //Chat0.7 Server增加接收流  接收Client发送的信息
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str); //并打印
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
}
