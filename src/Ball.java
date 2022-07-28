import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    Random random;
    int XVelocity,YVelocity;

    int initSpeed=2;
    Ball(int x,int y,int w,int h)
    {
        super(x,y,w,h);
        random=new Random();
        int randomX=random.nextInt(2);
        if(randomX==0)
        {
            randomX--;
        }
        setXDirection(randomX*initSpeed);

        int randomY=random.nextInt(2);
        if(randomY==0)
        {
            randomY--;
        }
        setYDirection(randomY*initSpeed);
    }
    public void setXDirection(int randXDirection)
    {
        XVelocity=randXDirection;
    }

    public void setYDirection(int randYDirection)
    {
        YVelocity=randYDirection;
    }
    public void move()
    {
        x+=XVelocity;
        y+=YVelocity;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillOval(x,y,width,height);
    }
}
