package unitcircle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Angle Graph is an abstract superclass for trig graphs in package unitcircle. It provides basic functionality for turning on/off gridlines and trig functions.
 *
 * @author Saieesh Rao
 * @version 10/30/2011
 */

public abstract class AngleGraph extends JComponent
{
    private double angle;
    private boolean gridlines;
    private boolean sin;
    private boolean cos;
    private boolean tan;

    /**
     * Constructs an AngleGraph with gridlines off, and initial angle of 0, and only sine on.
     */
    public AngleGraph(){
        angle = 0;
        gridlines = false;
        sin = true;
        cos = false;
        tan = false;
    }
    /**
     * Constructs an AngleGraph with gridlines off, and initial angle of <code>angle</code>, and only sine on.
     */
    public AngleGraph(double angle){
        this.angle = angle;
        gridlines = false;
        sin = true;
        cos = false;
        tan = false;
    }
    /**
     * Sets the angle value of this <code>AngleGraph</code> instance to <code>a</code> in degrees.
     * This method does not check for whether the inputted angle is within the correct range of 0 to 359 degrees.
     * @param a The angle value to be used when drawing the graph
     */
    public void setAngle(double a){
        angle = a;
    }
    /**
     * Returns a <code>double</code> correspondig to the angle value of this <code>AngleGraph</code> instance in degrees.
     * @return the current angle value in degrees
     */

    public double getAngle(){
        return angle;
    }
    /**
     * Returns a <code>double</code> corresponding to the angle value of this <code>AngleGraph</code> instance in radians.
     * @return the current radian value in degrees
     */
    public double radians(){
        return angle*Math.PI/180;
    }
    /**
     * Returns whether or not gridlines are being drawn on the graph
     * @return a <code>boolean</code> value of if gridlines are being drawn
     */
    public boolean showGridlines(){
        return gridlines;
    }
    /**
     * Set whether or not gridlines are to be drawn on the graph.
     * @param g a <code>boolean</code> indicating whether or not to draw gridlines
     */
    public void setGridlines(boolean g){
        gridlines = g;
    }
    /**
     * Returns if the sine function is being drawn.
     * @return a boolean indicating if sine is drawn on the graph
     */
    public boolean isSinOn(){
        return sin;
    }
    /**
     * Returns if the cosine function is being drawn.
     * @return a boolean indicating if cosine is drawn on the graph
     */
    public boolean isCosOn(){
        return cos;
    }
    /**
     * Returns if the tangent function is being drawn.
     * @return a boolean indicating if tangent is drawn on the graph
     */
    public boolean isTanOn(){
        return tan;
    }
    /**
     * Change the drawing state of the sine function.
     * If sine is being drawn, then this function turns it off. If sine is not being drawn, then this function turns it on.
     */
    public void switchSine(){
        sin = !sin;
    }
    /**
     * Change the drawing state of the cosine function.
     * If cosine is being drawn, then this function turns it off. If cosine is not being drawn, then this function turns it on.
     */
    public void switchCosine(){
        cos = !cos;
    }
    /**
     * Change the drawing state of the tangent function.
     * If tangent is being drawn, then this function turns it off. If tangent is not being drawn, then this function turns it on.
     */
    public void switchTangent(){
        tan = !tan;
    }
    /**
     * Draw a graph of sine, cosine, and tangent.
     */
    public abstract void paint(Graphics g);

}
