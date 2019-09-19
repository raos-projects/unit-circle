package unitcircle;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Write a description of class CartesianSpan here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CartesianSpan extends AngleGraph
{
    public CartesianSpan(double angle){
        super(angle);
        setSize(880,102);
    }

    public void paint(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(2));
        int width = 880, height = 100;
        int period = getWidth()/4;
        for(int ind = -720; ind < 720; ind++){
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double((ind+720)*875/1440.0,50-(int)(20*Math.cos(ind*Math.PI/180)),(ind+721)*875/1440.0,50-(int)(20*Math.cos((ind+1)*Math.PI/180))));
            }
            //draw sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double((ind+720)*875/1440.0,50-(int)(20*Math.sin(ind*Math.PI/180)),(ind+721)*875/1440.0,50-(int)(20*Math.sin((ind+1)*Math.PI/180))));

            }
            //draw tangent.
            if(isTanOn()){
                g.setColor(Color.ORANGE);
                if((ind - 90) % 180 != 0 && 50-(int)(20*Math.tan(ind*Math.PI/180)) < 100) g.draw(new Line2D.Double((ind+720)*875/1440.0,50-(int)(20*Math.tan(ind*Math.PI/180)),(ind+721)*875/1440.0,50-(int)(20*Math.tan((ind+1)*Math.PI/180))));
            }

        }
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(0,100,880,100));
        g.setStroke(new BasicStroke(1));

        g.drawLine(436,0,436,100);
        g.drawLine(0,50,880,50);
        g.setColor(Color.RED);
        g.fill(new Ellipse2D.Double((getAngle()+720)*875/1440.0-4,47,6,6));
    }

    public static void main(String [] args){
        JFrame frame = new JFrame("Test");
        frame.add(new CartesianSpan(60));
        frame.setVisible(true);
    }
}
