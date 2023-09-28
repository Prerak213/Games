import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class paddle extends Rectangle{
    int id;
    int yVelocity;
    int speed = 10;
    paddle(int x, int y, int paddleWidth, int paddleHeight, int id){
        super(x,y,paddleWidth,paddleHeight);
        this.id = id;
    }
    public void move(){
        y+=yVelocity;
    }

    public void keyPressed(KeyEvent e){
        switch (id) {
            case 1 -> {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    yVelocity = -speed;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    yVelocity = speed;
                }
            }
            case 2 -> {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    yVelocity = -speed;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    yVelocity = speed;
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1 -> {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    yVelocity = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    yVelocity = 0;
                }
            }
            case 2 -> {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    yVelocity = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    yVelocity = 0;
                }
            }
        }
    }
    public void draw(Graphics g){
        if (id == 1){
            g.setColor(Color.blue);
        }
        else{
            g.setColor(Color.red);
        }
        g.fillRect(x,y,width,height);
    }
}
