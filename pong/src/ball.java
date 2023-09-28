import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ball extends Rectangle{
    int initialSpeed=2;
    int xVelocity;
    int yVelocity;
    int xDirection;
    int yDirection;
    int direction;
    public boolean checkMoving = false;
    Random random = new Random();

    ball(int x, int y, int width, int height){
        super(x,y,width,height);
    }
    public void setDirection(){
        direction = random.nextInt(3);
        y = random.nextInt(GamePanel.getSCREEN_HEIGHT()-width);
        if (direction == 1) {
            xVelocity = initialSpeed * -1;
            xDirection = -1;
        } else {
            xVelocity = initialSpeed;
            xDirection = 1;
        }
        direction = random.nextInt(3);
        if (direction == 1) {
            yVelocity = initialSpeed * -1;
            yDirection = -1;
        } else {
            yVelocity = initialSpeed;
            yDirection = 1;
        }
    }
    public void move(){
        if(!checkMoving) {
            checkMoving = true;
            setDirection();
        }

        x+=xVelocity*initialSpeed;
        y+=yVelocity*initialSpeed;
    }
    public void draw(Graphics g){
        g.setColor(Color.lightGray);
        g.fillOval(x,y,width,height);
    }

}
