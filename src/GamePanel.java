import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static final int GW=1000;
    static final int GH=600;
    static final int BALL_DIAMETER=20;
    static final int PADDLE_WIDTH=25;
    static final int PADDLE_HEIGHT=100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle p1,p2;
    Ball ball;
    Score score;
    GamePanel()
    {
        newPaddle();
        newBall();
        score=new Score(GW,GH);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(new Dimension(GW,GH));
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall()
    {
        random=new Random();
        ball=new Ball(GW/2-BALL_DIAMETER/2,GH/2-BALL_DIAMETER/2,BALL_DIAMETER,BALL_DIAMETER);
    }

    public void newPaddle()
    {
        p1=new Paddle(0,GH/2-PADDLE_HEIGHT/2,PADDLE_WIDTH,PADDLE_HEIGHT,1);
        p2=new Paddle(GW-PADDLE_WIDTH,GH/2-PADDLE_HEIGHT/2,PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }

    public void paint(Graphics g)
    {
        image=createImage(getWidth(),getHeight());
        graphics=image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    public void draw(Graphics g)
    {
        p1.draw(g);
        p2.draw(g);
        ball.draw(g);
        score.draw(g);
        g.setColor(Color.lightGray);
        g.fillRect(GW/2-10,0,1,600);
    }

    public void move()
    {
        p1.move();
        p2.move();
        ball.move();
    }

    public void checkCollision()
    {
        //stop paddle cross over game window
        if(p1.y<0)
            p1.y=0;
        if(p1.y>=GH-PADDLE_HEIGHT)
            p1.y=GH-PADDLE_HEIGHT;

        if(p2.y<0)
            p2.y=0;
        if(p2.y>=GH-PADDLE_HEIGHT)
            p2.y=GH-PADDLE_HEIGHT;

        //stop ball cross over top or bottom edge of game window
        if (ball.y<=0)
            ball.setYDirection(-ball.YVelocity);
        if(ball.y>=GH-BALL_DIAMETER)
            ball.setYDirection(-ball.YVelocity);

        //bounce ball of the paddle

        if(ball.intersects(p1))
        {
            ball.XVelocity=Math.abs(ball.XVelocity);
            ball.XVelocity++;
            if(ball.YVelocity<0)
                ball.YVelocity++;
            else ball.YVelocity--;
            ball.setXDirection(ball.XVelocity);
            ball.setYDirection(ball.YVelocity);
        }

        if(ball.intersects(p2))
        {
            ball.XVelocity=Math.abs(ball.XVelocity);
            ball.XVelocity++;
            if(ball.YVelocity<0)
                ball.YVelocity++;
            else ball.YVelocity--;
            ball.setXDirection(-ball.XVelocity);
            ball.setYDirection(ball.YVelocity);
        }

        //point counter

        if(ball.x<=0)
        {
            score.p2++;
            newPaddle();
            newBall();
        }

        if(ball.x>=GW-BALL_DIAMETER)
        {
            score.p1++;
            newPaddle();
            newBall();
        }

    }

    public void run()
    {
        long lastTime=System.nanoTime();
        double amountOfTicks=60.0;
        double ns=1000000000/amountOfTicks;
        double delta=0;
        while (true)
        {
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;
            if(delta>=1)
            {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter{

        public void keyPressed(KeyEvent e)
        {
            p1.keyPressed(e);
            p2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e)
        {
            p1.keyReleased(e);
            p2.keyReleased(e);
        }
    }

}
