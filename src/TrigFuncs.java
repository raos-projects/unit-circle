package unitcircle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * <code>TrigFuncs</code> is a cartesian graph of trigonometric functions between 0 and 359 degrees used in unitcircle.UnitCircleApp.
 *
 * @author Saieesh Rao
 * @version 10/30/2011
 */

public class TrigFuncs extends AngleGraph
{
    protected boolean dropVerticals;

    /**
     * Constructs a cartesian graph with initial angle value <code>angle</code> and no dropped verticals.
     * @param angle the initial angle value in degrees.
     */
    public TrigFuncs(double angle){
        super(angle);
        setBounds(0,0,360,500);
        dropVerticals = false;
    }
    /**
     * Set whether or not the graph should draw vertical lines from the trigonometric curves to the x-axis at theta = <code>angle</code>.
     * This method causes the <code>TrigFuncs</code> graphics to repaint.
     * @param b a <code>boolean</code> representing whether or not to drop verticals.
     */
    public void setVerticals(boolean b){
        dropVerticals = b;
        repaint();
    }
    /**
     * Returns whether or not verticals are already being drawn.
     * @return whether or not verticals are drawn.
     */
    public boolean areVerticals(){
        return dropVerticals;
    }
    public void setAngle(double a){
        a %= 360;
        if(a < 0) a+=360;
        super.setAngle(a);
    }
    /**
     * Paints the cartesian graph of the trigonometric functions.
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
     * Paints a larger cartesian graph with larger gridlines on <code>Graphics g1</code>.
     * This method should be used when tangent is not visible so the sine and cosine graphs can be seen more clearly.
     * This method doesn't contain functionality for dropping verticals from tangent. Use <code>paintSmall(Graphics g1)</code> instead.
     * When possible always call <code>paint(Graphics g)</code> instead of this method.
     *
     * @param g1 the Graphics object to draw on.
     */
    public void paintLarge(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(1));
        if(showGridlines()){
            g.setColor(Color.LIGHT_GRAY);
            for(int ind = 20; ind <=500; ind+=20){
                g.draw(new Line2D.Double(ind,0,ind,500));
                g.draw(new Line2D.Double(0,ind-10,500,ind-10));
            }
        }
        //Draws the base of the graph.
        //It is the circle and the X-Y Axes.
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(0,0,0,500));
        g.draw(new Line2D.Double(0,250,360,250));
        //set the stroke thickness.
        g.setStroke(new BasicStroke(2));
        for(int ind = 0; ind < 360; ind++){
            //draw cosine.
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(ind,250-(int)(200*Math.cos(ind*Math.PI/180)),ind+1,250-(int)(200*Math.cos((ind+1)*Math.PI/180))));
            }
            //draw sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(ind,250-(int)(200*Math.sin(ind*Math.PI/180)),ind+1,250-(int)(200*Math.sin((ind+1)*Math.PI/180))));

            }
            //draw tangent.
            if(isTanOn()){
                g.setColor(Color.ORANGE);
                if((ind - 90) % 180 != 0 && 250-(int)(200*Math.tan(ind*Math.PI/180)) < 500) g.draw(new Line2D.Double(ind,250-(int)(200*Math.tan(ind*Math.PI/180)),ind+1,250-(int)(200*Math.tan((ind+1)*Math.PI/180))));
            }
        }
        if(dropVerticals){
            //calculate the horizontal shifts of the verticals.
            int cosShift = 0, sinShift = 0;
            if(isCosOn() && isSinOn()){
                cosShift = 1;
                sinShift = -1;
            }
            //draw line dropping from cosine
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(getAngle()+cosShift,250,getAngle()+cosShift,250-(int)(200*Math.cos((getAngle())*Math.PI/180))));
            }
            //draw line dropping from sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(getAngle()+sinShift,250,getAngle()+sinShift,250-(int)(200*Math.sin((getAngle()+sinShift)*Math.PI/180))));
            }
        }
        //Draws red dot at angle measure.
        g.setColor(Color.RED);
        g.fill(new Ellipse2D.Double(getAngle()-4,246,8,8));
    }
    /**
     * Paints a smaller cartesian graph with smaller gridlines on <code>Graphics g</code>.
     * This method should be used when tangent is visible so more of the tangent graph can be seen.
     * When possible always call <code>paint(Graphics g)</code> instead of this method.
     *
     * @param g1 the Graphics object to draw on.
     */
    public void paintSmall(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(1));
        //Draw the gridlines
        //in gray.
        if(showGridlines()){
            g.setColor(Color.LIGHT_GRAY);
            for(int ind = 0; ind <= 360; ind+=10){
                g.draw(new Line2D.Double(ind,0,ind,500));
            }
            for(int ind = 0; ind <=500; ind+=10){
                g.draw(new Line2D.Double(0,ind,360,ind));
            }
        }
        //Draws the Axes in black.
        //Dimensions of the graph
        //are 360 wide, 500 high.
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(0,0,0,500));
        g.draw(new Line2D.Double(0,250,360,250));
        //make the stroke thicker
        g.setStroke(new BasicStroke(2));
        for(int ind = 0; ind < 360; ind++){
            //draw cosine.
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(ind,250-(int)(100*Math.cos(ind*Math.PI/180)),ind+1,250-(int)(100*Math.cos((ind+1)*Math.PI/180))));
            }
            //draw sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(ind,250-(int)(100*Math.sin(ind*Math.PI/180)),ind+1,250-(int)(100*Math.sin((ind+1)*Math.PI/180))));
            }
            //draw tangent.
            if(isTanOn()){
                g.setColor(Color.ORANGE);
                if((ind - 90) % 180 != 0 && 250-(int)(100*Math.tan(ind*Math.PI/180)) < 500) g.draw(new Line2D.Double(ind,250-(int)(100*Math.tan(ind*Math.PI/180)),ind+1,250-(int)(100*Math.tan((ind+1)*Math.PI/180))));
            }
        }
        if(dropVerticals){
            //draw line dropping from cosine.
            int cosShift = 0, sinShift = 0, tanShift = 0;
            if(isSinOn() && isCosOn()){
                cosShift = 2;
                sinShift = -2;
            }
            else if(isSinOn() && !isCosOn()){
                sinShift = -2;
            }
            else if(isCosOn() && !isSinOn()){
                cosShift = 2;
            }
            //draw line dropping from cosine
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(getAngle()+cosShift,250,getAngle()+cosShift,250-(int)(100*Math.cos((getAngle())*Math.PI/180))));
            }
            //draw line dropping from sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(getAngle()+sinShift,250,getAngle()+sinShift,250-(int)(100*Math.sin((getAngle()-1)*Math.PI/180))));
            }
            //draw line dropping from tangent.
            if(isTanOn() && (getAngle()-90)%180 != 0){
                g.setColor(Color.ORANGE);
                g.draw(new Line2D.Double(getAngle()+tanShift,250,getAngle()+tanShift,250-(int)(100*Math.tan((getAngle())*Math.PI/180))));//-1
            }
        }
        //Draws red dot at angle measure.
        g.setColor(Color.RED);
        g.fill(new Ellipse2D.Double(getAngle()-4,246,8,8));
    }
}