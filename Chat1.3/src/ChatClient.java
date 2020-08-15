import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient extends Frame {
    Socket s = null; //Chat0.7 把Socket放到成员变量里 使得TFListener可以访问到
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();

    Thread tRecv = new Thread(new RecvThread());

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }


    public void launchFrame() {
        setLocation(400, 300);
        setSize(300, 300);
        add(tfText, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);
        pack();
        this.addWindowListener(   //窗口关闭
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        if (dos != null || s != null) {
                            disconnect();
                        }
                        setVisible(false);
                        System.exit(0);
                    }
                }
        );
        tfText.addActionListener(new TFListener());
        setVisible(true);
        connect();

        tRecv.start();
    }

    public void connect() {  //Chat0.6 增加了Client端的网络连接
        try {
            s = new Socket("127.0.0.1", 8887);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            bConnected = true;
            System.out.println("connected!");
        } catch (IOException e) {
            System.out.println("端口不可用");
            e.printStackTrace();
        }
    }

    public void disconnect() { //关闭流 关闭连接的方法 在关闭窗口时调用
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


/*        try {
            bConnected = false;
            tRecv.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                dis.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private class TFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = tfText.getText().trim(); //trim()方法 去除左右两边的空格
            //taContent.setText(str);
            tfText.setText("");
            try {
                //DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //Chat0.7 Client增加发送流 发送信息给Server
                dos.writeUTF(str);
                dos.flush();
                //dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class RecvThread implements Runnable {
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    taContent.setText(taContent.getText() + str + '\n');
                }
            } catch (SocketException e) {
                System.out.println("窗口关闭");
            } catch (EOFException e) {
                System.out.println("窗口关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}