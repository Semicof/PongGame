import java.awt.*;

public class Score extends Rectangle{
    static int GW;
    static int GH;
    int p1,p2;

    Score(int GW,int GH)
    {
        this.GW=GW;
        this.GH=GH;
    }
    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        g.drawString("P1's Score: "+Integer.toString(p1),80,60);
        g.drawString("P2's Score: "+Integer.toString(p2),660,60);
    }
}
