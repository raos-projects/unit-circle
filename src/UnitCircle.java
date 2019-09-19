package unitcircle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

/**
 * <code>UnitCircle</code> is the graph of a unit circle displayed in unitcircle.UnitCircleApp.
 *
 * @author Saieesh Rao
 * @version 10/30/2011
 */

public class UnitCircle extends AngleGraph
{
    /**
     * Constructs a UnitCircle graph with initial angle value <code>angle</code>.
     * @param angle the initial angle value in degrees.
     */
    public UnitCircle(double angle){
        super(angle);
        setBounds(0,0,500,500);
    }
    /**
     * Paints the unit circle graph.
     * When tangent is visible, it paints a large scale graph, defined by <code>paintSmall(Graphics g)</code>.
     * When tangent is not visible, it paints a small scale graph, defined by <code>paintLarge(Graphics g)</code>.
     *
     * @param g the Graphics object to draw on.
     */
    public void paint(Graphics g){
        if(!isTanOn()){
            paintLarge(g);
        }
        else{
            paintSmall(g);
        }
    }
    /**
     * Paints a smaller unit circle with smaller gridlines on <code>Graphics g1</code>.
     * This method should be used when tangent is visible so more of the tangent graph can be seen.
     * When possible always call <code>paint(Graphics g)</code> instead of this method.
     *
     * @param g1 the Graphics object to draw on.
     */
    public void paintSmall(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(1));

        if(showGridlines()){
            g.setColor(Color.LIGHT_GRAY);
            for(int ind = 0; ind <= 500; ind+=10){
                g.draw(new Line2D.Double(ind,0,ind,500));
            }
            for(int ind = 0; ind <=500; ind+=10){
                g.draw(new Line2D.Double(0,ind,500,ind));
            }
        }
        g.setColor(Color.BLACK);
        g.draw(new Ellipse2D.Double(150,150,200,200));
        g.draw(new Line2D.Double(0,250,500,250));
        g.draw(new Line2D.Double(250,0,250,500));
        //sets a thick stroke for drawing the functions.
        g.setStroke(new BasicStroke(2));
        //Draws the angle measure.It is a red
        //line segment starting at the
        //origin and ending at its
        //intersection with the circle.
        g.setColor(Color.RED);
        g.draw(new Line2D.Double(250,250,250+(int)(100*Math.cos(radians())), 250-(int)(100*Math.sin(radians()))));
        //Draws the sine measure, or the
        //vertical line from the x-axis
        //to the circle.
        if(isSinOn()) g.setColor(Color.BLUE);
        else g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(250+(int)(100*Math.cos(radians())), 250,
                250+(int)(100*Math.cos(radians())), 250-(int)(100*Math.sin(radians()))));
        //Draws the tangent line.
        //It is a vertical line
        //tangent to the circle
        //at the x-axis.
        if(isTanOn()){
            if((getAngle()-90) % 180 != 0){
                g.setColor(Color.ORANGE);
                g.draw(new Line2D.Double(351,250,351,Math.min(250-(int)(100*Math.tan(radians())),500)));
                g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 15.0f, new float[] {4.0f,4.0f}, 5.0f));
                g.setColor(Color.RED);
                g.draw(new Line2D.Double(250,250,351,250-(int)(100*Math.tan(radians()))));
            }
        }
        //Draws the cosine measure.
        //It is a green line along the
        //x-axis, starting at the origin.
        g.setStroke(new BasicStroke(2));
        if(isCosOn()) g.setColor(Color.GREEN.darker());
        else g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(250,250,250+(int)(100*Math.cos(radians())), 250));
        //Draws a red dot on the
        //circumference referring
        //to the angle meausure.
        g.setColor(Color.RED);
        int dotRadius = 5;
        g.fill(new Ellipse2D.Double(250+(int)(100*Math.cos(radians()))-dotRadius, 250-(int)(100*Math.sin(radians()))-dotRadius, dotRadius*2, dotRadius*2));
    }
    /**
     * Paints a larger unit circle with larger gridlines on <code>Graphics g1</code>.
     * This method should be used when tangent is not visible so the sine and cosine graphs can be seen more clearly.
     * When possible always call <code>paint(Graphics g)</code> instead of this method.
     *
     * @param g1 the Graphics object to draw on.
     */
    public void paintLarge(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(1));
        //Draws the gridlines.
        if(showGridlines()){
            g.setColor(Color.LIGHT_GRAY);
            for(int ind = -10; ind <=500; ind+=20){
                g.draw(new Line2D.Double(ind,0,ind,500));
                g.draw(new Line2D.Double(0,ind,500,ind));
            }
        }
        //Draws the base of the graph.
        //It is the circle and the X-Y Axes.
        g.setColor(Color.BLACK);
        g.draw(new Ellipse2D.Double(50,50,401,401));
        g.draw(new Line2D.Double(0,250,500,250));
        g.draw(new Line2D.Double(250,0,250,500));
        //set the stroke thickness.
        g.setStroke(new BasicStroke(2));
        //Draws the angle measure.It is a red
        //line segment starting at the
        //origin and ending at its
        //intersection with the circle.
        g.setColor(Color.RED);
        g.draw(new Line2D.Double(250,250,250+(int)(201*Math.cos(radians())), 250-(int)(201*Math.sin(radians()))));
        //Draws the sine measure, or the
        //vertical line from the x-axis
        //to the circle.
        if(isSinOn())g.setColor(Color.BLUE);
        else g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(250+(int)(201*Math.cos(radians())), 250,
                250+(int)(201*Math.cos(radians())), 250-(int)(201*Math.sin(radians()))));
        //Draws the cosine measure.
        //It is a green line along the
        //x-axis, starting at the origin.
        if(isCosOn()) g.setColor(Color.GREEN.darker());
        else g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(250,250,250+(int)(201*Math.cos(radians())), 250));
        //Draws a red dot on the
        //circumference referring
        //to the angle meausure.
        g.setColor(Color.RED);
        int dotRadius = 7;
        g.fill(new Ellipse2D.Double(250+(int)(201*Math.cos(radians()))-dotRadius, 250-(int)(201*Math.sin(radians()))-dotRadius, dotRadius*2, dotRadius*2));
    }
}
