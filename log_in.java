import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class log_in extends JFrame {
    LogPanel panel;
    public int width = 400;
    public int height = 400;

    public log_in() {
        panel = new LogPanel(this); 
        this.add(panel);
        this.addKeyListener(panel);
        this.setSize(width, height);
        this.setTitle("log in");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class LogPanel extends JPanel implements KeyListener{
    int current = 0;
    log_in log;
    Image[] log_inImages = {
        new ImageIcon("登入頁面\\進入畫面第二版1.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版2.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版3.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版4.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版5.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版6.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版7.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版8.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版9.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版10.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版11.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版12.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版13.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版14.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版15.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版16.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版17.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版18.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版19.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版20.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版21.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版22.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版23.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版24.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版25.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版26.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版27.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版28.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版29.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版30.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版31.png").getImage(),
        new ImageIcon("登入頁面\\進入畫面第二版32.png").getImage(),
    };

    public LogPanel(log_in log) {
        this.log=log;
        Timer timer = new Timer(100, (ActionEvent e) -> {
            current++;
            if (current >= log_inImages.length) {
                current = 0;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(log_inImages[current], 0, 0, 400, 400, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode==KeyEvent.VK_SPACE){
            log.dispose();
            new Move();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
