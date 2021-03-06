import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame {
    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }


    public void launchFrame() {
        setLocation(400, 300);
        setSize(300, 300);
        setVisible(true);
        this.addWindowListener( //窗口关闭
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        System.exit(0);
                    }
                }
        );
    }
}
