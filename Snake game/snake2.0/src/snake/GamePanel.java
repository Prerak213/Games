package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 10;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(10*10);
    static final int DELAY = 80;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 10;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw (Graphics g){
        if(running){
            if(applesEaten%5 == 0 && applesEaten!=0){
                g.setColor(new Color(212, 175, 55));
            }
            else{
                g.setColor(Color.red);}
            g.fillOval(appleX, appleY, UNIT_SIZE*2, UNIT_SIZE*2);
            g.setColor(Color.green);
            g.fillArc(appleX+7, appleY-16, 8, 40, 30, 60);


            for(int i=0; i<bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.PINK);
                    g.fillRect(x[i], y[i], UNIT_SIZE*2, UNIT_SIZE*2);
                    g.fillRect(x[i+1], y[i+1], UNIT_SIZE*2, UNIT_SIZE*2);



                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i+1], y[i+1], UNIT_SIZE*2, UNIT_SIZE*2);
                }
            }
            g.setFont((new Font("Ink Free", Font.BOLD, 25)));
            g.setColor(Color.red);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+(applesEaten), (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten)), 25);
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

    }
    public void move (){
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        switch (direction) {
            case 'W' -> y[0] = y[0] - UNIT_SIZE;
            case 'S' -> y[0] = y[0] + UNIT_SIZE;
            case 'A' -> x[0] = x[0] - UNIT_SIZE;
            case 'D' -> x[0] = x[0] + UNIT_SIZE;
        }
    }
    public void checkApple(){
        if(x[0]==appleX && y[0]==appleY) {
            if (applesEaten % 5 == 0 && applesEaten != 0) {
                bodyParts += 6;
                newApple();
                applesEaten += 1;
            } else {
                bodyParts += 2;
                newApple();
                applesEaten += 1;
            }
        }

    }
    public void checkCollisions(){
        for(int i=bodyParts; i>0; i--){
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
            if(x[0]<0 || y[0]<0 || x[0]>SCREEN_WIDTH || y[0]>SCREEN_HEIGHT ){
                running=false;
            }
            if(!running){
                timer.stop();
            }
        }
    }
    public void gameOver(Graphics g){
        if(!running)
        g.setColor(Color.CYAN);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2-50);
        g.setFont((new Font("Ink Free", Font.BOLD, 40)));
        g.setColor(Color.red);
        metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2, (SCREEN_HEIGHT/2)+50);
    }
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_D:
                    if(direction!='A'){
                        direction='D';
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction!='D'){
                        direction ='A';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction!='S'){
                        direction='W';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction!='W'){
                        direction ='S';
                    }
                    break;
            }

        }
    }
}
