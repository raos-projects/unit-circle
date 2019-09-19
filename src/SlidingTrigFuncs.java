package unitcircle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
/**
 * Write a description of class SlidingTrigFuncs here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SlidingTrigFuncs extends TrigFuncs
{
    public SlidingTrigFuncs(double angle){
        super(angle);
    }

//     public void paint(Graphics g){
//         if(isTanOn()){
//             //paintSmall(g);
//         }
//         else{
//             //paintLarge(g);
//         }
//     }

    public void paintLarge(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setStroke(new BasicStroke(1));
        if(showGridlines()){
            g.setColor(Color.LIGHT_GRAY);
            for(int ind = 0; ind <=500; ind+=20){
                g.draw(new Line2D.Double(ind,0,ind,500));
                g.draw(new Line2D.Double(0,ind-10,500,ind-10));
            }
        }
        //Draws the base of the graph.
        //It is the circle and the X-Y Axes.
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(180-getAngle(),0,180-getAngle(),500));
        g.draw(new Line2D.Double(0,250,360,250));
        //set the stroke thickness.
        g.setStroke(new BasicStroke(2));
        for(int ind = (int)getAngle()-180; ind <= (int)getAngle()+180; ind++){
            //draw cosine.
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(ind-getAngle()+180,250-(int)(200*Math.cos(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(200*Math.cos((ind+1)*Math.PI/180))));
            }
            //draw sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(ind-getAngle()+180,250-(int)(200*Math.sin(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(200*Math.sin((ind+1)*Math.PI/180))));

            }
            //draw tangent.
            if(isTanOn()){
                g.setColor(Color.ORANGE);
                if((ind - 90) % 180 != 0 && 250-(int)(200*Math.tan(ind*Math.PI/180)) < 500) g.draw(new Line2D.Double(ind-getAngle()+180,250-(int)(200*Math.tan(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(200*Math.tan((ind+1)*Math.PI/180))));
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
                g.draw(new Line2D.Double(180+cosShift,250,180+cosShift,250-(int)(200*Math.cos((getAngle())*Math.PI/180))));
            }
            //draw line dropping from sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(180+sinShift,250,180+sinShift,250-(int)(200*Math.sin((getAngle()+sinShift)*Math.PI/180))));
            }
        }
        //Draws red dot at angle measure.
        g.setColor(Color.RED);
        g.fill(new Ellipse2D.Double(176,246,8,8));
    }
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
        g.draw(new Line2D.Double(180-getAngle(),0,180-getAngle(),500));
        g.draw(new Line2D.Double(0,250,360,250));
        //make the stroke thicker
        g.setStroke(new BasicStroke(2));
        for(int ind = (int)getAngle()-180; ind <= (int)getAngle()+180; ind++){
            //draw cosine.
            if(isCosOn()){
                g.setColor(Color.GREEN.darker());
                g.draw(new Line2D.Double(ind-getAngle()+180,250-(int)(100*Math.cos(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(100*Math.cos((ind+1)*Math.PI/180))));
            }
            //draw sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(ind-getAngle()+181,250-(int)(100*Math.sin(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(100*Math.sin((ind+1)*Math.PI/180))));
            }
            //draw tangent.
            if(isTanOn()){
                g.setColor(Color.ORANGE);
                if((ind - 90) % 180 != 0 && 250-(int)(100*Math.tan(ind*Math.PI/180)) < 500) g.draw(new Line2D.Double(ind-getAngle()+180,250-(int)(100*Math.tan(ind*Math.PI/180)),ind-getAngle()+181,250-(int)(100*Math.tan((ind+1)*Math.PI/180))));
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
                g.draw(new Line2D.Double(180+cosShift,250,180+cosShift,250-(int)(100*Math.cos((getAngle())*Math.PI/180))));
            }
            //draw line dropping from sine.
            if(isSinOn()){
                g.setColor(Color.BLUE);
                g.draw(new Line2D.Double(180+sinShift,250,180+sinShift,250-(int)(100*Math.sin((getAngle()-1)*Math.PI/180))));
            }
            //draw line dropping from tangent.
            if(isTanOn() && (getAngle()-90)%180 != 0){
                g.setColor(Color.ORANGE);
                g.draw(new Line2D.Double(180+tanShift,250,180+tanShift,250-(int)(100*Math.tan((getAngle())*Math.PI/180))));//-1
            }
        }
        //Draws red dot at angle measure.
        g.setColor(Color.RED);
        g.fill(new Ellipse2D.Double(176,246,8,8));
    }
}
