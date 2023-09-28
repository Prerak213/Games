import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    Random random;
    final int SCREEN_WIDTH=1000;
    static final int SCREEN_HEIGHT=550;
     int DELAY = 15;
    final int PADDLE_WIDTH = 25;
    final int PADDLE_HEIGHT = 100;
    final int DIAMETER = 20;
    int score1;
    int score2;
    paddle paddle1;
    paddle paddle2;
    ball ball;
    boolean running;
    boolean changedTopDirection = true;
    boolean changedBottomDirection = true;
    Timer timer;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public static int getSCREEN_HEIGHT(){
        return SCREEN_HEIGHT;
    }
    public void startGame(){
        newPaddle();
        newBall();
        score1 = 0;
        score2 = 0;
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void newPaddle(){
        paddle1 = new paddle(0, SCREEN_HEIGHT/2-PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new paddle(SCREEN_WIDTH-PADDLE_WIDTH, SCREEN_HEIGHT/2-PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    public void newBall(){
        ball = new ball(SCREEN_WIDTH/2-DIAMETER/2, SCREEN_HEIGHT/2-DIAMETER/2, DIAMETER, DIAMETER);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        if(ball.checkMoving) {
            ball.draw(g);
        }
        g.setColor(Color.white);
        g.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
        g.setFont((new Font("Ink Free", Font.BOLD, 65)));
        g.setColor(Color.BLUE);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(String.valueOf(score1), SCREEN_WIDTH/2-metrics.stringWidth(String.valueOf(score1))-30, 65);
        g.setColor(Color.red);
        g.drawString(String.valueOf(score2), SCREEN_WIDTH/2+30, 65);
        Toolkit.getDefaultToolkit().sync();
    }
    public void move (){
        paddle1.move();
        paddle2.move();
        checkCollisions();
    }
    public void checkCollisions(){
        if(changedTopDirection) {
            if (ball.y < 3) {
                ball.yVelocity *= -1;
                changedTopDirection = false;
                changedBottomDirection = true;
            }
        }
        if(changedBottomDirection){
            if(ball.y > SCREEN_HEIGHT - DIAMETER - 3){
                ball.yVelocity *= -1;
                changedTopDirection = true;
                changedBottomDirection = false;
            }
        }

        if(paddle1.intersects(ball)|| paddle2.intersects(ball)){
            ball.xVelocity*=-1;
            DELAY-=3;
            changedTopDirection = true;
            changedBottomDirection = true;
            try {
                timer.setDelay(DELAY);
            }
            catch (Exception e) {
                if (DELAY == 0 || DELAY % 5 == 0) {
                    ball.initialSpeed++;
                    paddle1.speed += 4;
                    paddle2.speed += 4;
                }
            }

        }
        if(ball.x <=0) {
            score2 += 1;
            newBall();
            changedTopDirection = true;
            changedBottomDirection = true;
            newPaddle();
        }
        if(ball.x>= SCREEN_WIDTH-DIAMETER) {
            score1 += 1;
            changedTopDirection = true;
            changedBottomDirection = true;
            newBall();
            newPaddle();
        }
        if(paddle1.y<=0 || paddle1.y>=SCREEN_HEIGHT+paddle1.y){
            paddle1.y=0;
        }
        if(paddle2.y<=0|| paddle1.y>=SCREEN_HEIGHT-paddle1.y){
            paddle2.y=SCREEN_HEIGHT-paddle1.y;
        }

    }


    public void gameOver(Graphics g){

    }


    public void actionPerformed(ActionEvent e) {
        if(running){
            moveBall();
        }
        repaint();
    }

    private void moveBall() {
        ball.move();
        checkCollisions();
    }

    public void perform(){
        move();
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
            perform();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }
    }
}
