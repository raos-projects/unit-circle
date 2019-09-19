package unitcircle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
/**
 * A custom button that fires continuously as it is pressed. Was originally used
 * to change the angle, however was dropped in favor of using a scrollbar instead.
 *
 * @author Saieesh Rao
 * @version 10/30/2011
 */
public class JContinuousButton extends BasicArrowButton implements ActionListener
{
    private Timer timer;
    private static int n = 0;
    private static JTextField field;

    public JContinuousButton(int direction){
        super(direction);
        timer = new Timer(50,this);
        addMouseListener(   new MouseAdapter(){
            public void mousePressed(MouseEvent me){
                timer.start();
            }
            public void mouseReleased(MouseEvent me){
                timer.stop();
            }
        });
    }

    public void actionPerformed(ActionEvent ae){
        fireActionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"Being Pressed"));
    }

    public static void main(String[] args){
        field = new JTextField(6);
        field.setText(""+n);
        JContinuousButton jcbU = new JContinuousButton(SwingConstants.NORTH);
        jcbU.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                n++;
                field.setText(""+n);
            }
        });
        JContinuousButton jcbD = new JContinuousButton(SwingConstants.SOUTH);
        jcbD.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                n--;
                field.setText(""+n);
            }
        });
        JPanel p = new JPanel();
        p.add(jcbU);
        p.add(jcbD);
        p.add(field);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
}
