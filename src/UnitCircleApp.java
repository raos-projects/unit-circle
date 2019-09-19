package unitcircle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.text.*;
import javax.imageio.*;
import java.net.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import java.util.concurrent.*;
/**
 * UnitCircleApp is a window for displaying an angle and trigonometric functions of that angle graphically on a Unit Circle and on a Cartesian Graph.
 *
 * @author Saieesh Rao
 * @version 10/30/2011
 */


public class UnitCircleApp extends JFrame implements AdjustmentListener, ActionListener, MouseListener, MouseWheelListener, ChangeListener
{
    //mathematical variables
    private static double angle;
    private String angleUnit;
    //autocycle variables
    private int autocycleLower;
    private int autocycleUpper;
    private boolean autocycleRepeat;
    private Timer autocycleTimer;
    //bottom panel values
    private JPanel valueComponents;
    private JScrollBar angleBar;
    private JTextField angleText;
    private JTextField sinText;
    private JTextField cosText;
    private JTextField tanText;
    private JCheckBox sinBox;
    private JCheckBox cosBox;
    private JCheckBox tanBox;
    private DecimalFormat df;
    private JPanel sinPanel;
    private JPanel cosPanel;
    private JPanel tanPanel;
    private JPanel anglePanel;
    private JPanel preferencePanel;
    private ButtonGroup angleMode;
    private JRadioButton degreeButton;
    private JRadioButton radianButton;
    private JCheckBox gridBox;
    private JTextArea valueArea;
    //progress bar
    private Timer progressTimer;
    private JProgressBar progressBar;
    //Menubar
    private JMenuBar menuBar;
    //angle menu
    private JMenu angleMenu;
    private JMenuItem setAngleItem;
    private JMenuItem viewTrigValuesItem;
    private JMenu autocycleSubmenu;
    private JMenuItem autocycleItem;
    private JMenuItem autocycleStopItem;
    private JMenuItem autocycleSpeedItem;
    private JMenuItem autocycleBoundsItem;
    private ButtonGroup angleModeMenu;
    private JRadioButtonMenuItem radianMenuButton;
    private JRadioButtonMenuItem degreeMenuButton;
    //display menu
    private JMenu displayMenu;
    private JCheckBoxMenuItem sinBoxMenu;
    private JCheckBoxMenuItem cosBoxMenu;
    private JCheckBoxMenuItem tanBoxMenu;
    private JCheckBoxMenuItem gridMenuBox;
    private JCheckBoxMenuItem dropVerticalsMenuBox;
    private JCheckBoxMenuItem bottomMenuBox;
    private JMenuItem passwordItem;
    //help menu
    private JMenu helpMenu;
    private JMenuItem howToItem;
    private JMenuItem funcItem;
    private JMenuItem aboutItem;
    //graphs
    private TrigFuncs tf;
    private CartesianSpan cs;
    private UnitCircle uc;
    private JPanel graphPanel;
    //constants
    private final static String DEGREE_SYMBOL = "�";
    private final static String RADIAN_SYMBOL = "r";
    private final static String IDENTIFY_FUNCTIONS_PASSWORD = "elliot";

    private UnitCircleApp(){
        super("Unit Circle");
        //set layout to BorderLayout
        setLayout(new BorderLayout());
        //set angle value (in degrees)
        angle = 60;
        //set units to degrees
        angleUnit = DEGREE_SYMBOL;
        //initialize decimal formatter to permit 5 digits after the decimal point
        df = new DecimalFormat("###.#####");
        //initialize autocycle bounds, autocycle repeats
        autocycleLower = 0;
        autocycleUpper = 359;
        autocycleRepeat = false;
        //make progress bar
        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        add(progressBar,BorderLayout.CENTER);
        progressBar.setBounds(150,280,580,35);
        progressBar.setVisible(true);
        //make the menu bar
        menuBar = new JMenuBar();
        //make the angle menu, initialize menu options
        //contains options to set the angle, view values of trig functions, change the angle unit of measurement, and a submenu for autocycling
        angleMenu = new JMenu("Angle");
        angleMenu.setToolTipText("Change the Angle, Angle Mode, and see the values of Trig Functions");
        setAngleItem = new JMenuItem("Set Angle");
        setAngleItem.addActionListener(this);
        setAngleItem.setAccelerator(KeyStroke.getKeyStroke('Z',ActionEvent.CTRL_MASK));
        setAngleItem.setToolTipText("Set the angle to a specifc value in degrees");
        angleMenu.add(setAngleItem);
        viewTrigValuesItem = new JMenuItem("View Values");
        viewTrigValuesItem.addActionListener(this);
        viewTrigValuesItem.setAccelerator(KeyStroke.getKeyStroke('N',ActionEvent.CTRL_MASK));
        viewTrigValuesItem.setToolTipText("See the value of Sine, Cosine, and Tangent at "+angleMeasure());
        angleMenu.add(viewTrigValuesItem);
        //make the autocycle submenu
        autocycleSubmenu = new JMenu("Autocycle");
        autocycleItem = new JMenuItem("Start Autocycle");
        autocycleItem.addActionListener(this);
        autocycleItem.setAccelerator(KeyStroke.getKeyStroke('A',ActionEvent.CTRL_MASK));
        autocycleItem.setToolTipText("Automatically increase the angle from "+autocycleLower+DEGREE_SYMBOL+" to "+autocycleUpper+DEGREE_SYMBOL);
        autocycleSubmenu.add(autocycleItem);
        autocycleStopItem = new JMenuItem("Stop Autocycle");
        autocycleStopItem.setAccelerator(KeyStroke.getKeyStroke('A',ActionEvent.CTRL_MASK));
        autocycleStopItem.addActionListener(this);
        autocycleStopItem.setToolTipText("Quit autocycling the angle");
        //doesn't add the stop autocycle option to the menu until autocycling begins
        autocycleSpeedItem = new JMenuItem("Set Autocycle Speed");
        autocycleSpeedItem.addActionListener(this);
        autocycleSpeedItem.setAccelerator(KeyStroke.getKeyStroke('S',ActionEvent.CTRL_MASK));
        autocycleSpeedItem.setToolTipText("Adjust the speed of the autocycle command");
        autocycleSubmenu.add(autocycleSpeedItem);
        autocycleBoundsItem = new JMenuItem("Set Autocycle Bounds");
        autocycleBoundsItem.addActionListener(this);
        autocycleBoundsItem.setAccelerator(KeyStroke.getKeyStroke('B',ActionEvent.CTRL_MASK));
        autocycleBoundsItem.setToolTipText("Adjust the start and end points of the autocycle in degrees");
        autocycleSubmenu.add(autocycleBoundsItem);
        angleMenu.add(autocycleSubmenu);
        angleMenu.addSeparator();
        degreeMenuButton = new JRadioButtonMenuItem("Degrees");
        degreeMenuButton.setAccelerator(KeyStroke.getKeyStroke('D',ActionEvent.CTRL_MASK));
        degreeMenuButton.setToolTipText("Change the unit of angle measurement to degrees");
        degreeMenuButton.addActionListener(this);
        degreeMenuButton.setSelected(true);
        angleMenu.add(degreeMenuButton);
        radianMenuButton = new JRadioButtonMenuItem("Radians");
        radianMenuButton.addActionListener(this);
        radianMenuButton.setAccelerator(KeyStroke.getKeyStroke('R',ActionEvent.CTRL_MASK));
        radianMenuButton.setSelected(false);
        radianMenuButton.setToolTipText("Change the unit of angle measurement to radians");
        angleModeMenu = new ButtonGroup();
        angleModeMenu.add(radianMenuButton);
        angleModeMenu.add(degreeMenuButton);
        angleMenu.add(radianMenuButton);
        //make the display menu, initialize the menu options
        //contains options to turn on/off trig functions, show gridlines, drop verticals, and hide the manual feautures at the bottom
        //also contains option to reveal the names of the Mystery Funcs A,B,C with password
        displayMenu = new JMenu("Display");
        displayMenu.setToolTipText("Set preferences for how the graphs are drawn");
        sinBoxMenu = new JCheckBoxMenuItem("Graph Mystery Func A");
        sinBoxMenu.addActionListener(this);
        sinBoxMenu.setAccelerator(KeyStroke.getKeyStroke('1',ActionEvent.CTRL_MASK));
        sinBoxMenu.setSelected(true);
        sinBoxMenu.setToolTipText("Draw Mystery Func A in the unit circle and the Mystery Func A curve in the graph");
        displayMenu.add(sinBoxMenu);
        cosBoxMenu = new JCheckBoxMenuItem("Graph Mystery Func B");
        cosBoxMenu.addActionListener(this);
        cosBoxMenu.setAccelerator(KeyStroke.getKeyStroke('2',ActionEvent.CTRL_MASK));
        cosBoxMenu.setSelected(false);
        cosBoxMenu.setToolTipText("Draw Mystery Func B in the unit circle and the Mystery Func B curve in the graph");
        displayMenu.add(cosBoxMenu);
        tanBoxMenu = new JCheckBoxMenuItem("Graph Mystery Func C");
        tanBoxMenu.addActionListener(this);
        tanBoxMenu.setAccelerator(KeyStroke.getKeyStroke('3',ActionEvent.CTRL_MASK));
        tanBoxMenu.setSelected(false);
        tanBoxMenu.setToolTipText("Draw Mystery Func C in the unit circle and the Mystery Func C curve in the graph");
        displayMenu.add(tanBoxMenu);
        displayMenu.addSeparator();
        gridMenuBox = new JCheckBoxMenuItem("Show Gridlines");
        gridMenuBox.addActionListener(this);
        gridMenuBox.setAccelerator(KeyStroke.getKeyStroke('G',ActionEvent.CTRL_MASK));
        gridMenuBox.setSelected(false);
        gridMenuBox.setToolTipText("Draw gridlines in the unit circle and the graph");
        displayMenu.add(gridMenuBox);
        dropVerticalsMenuBox = new JCheckBoxMenuItem("Drop Verticals");
        dropVerticalsMenuBox.addActionListener(this);
        dropVerticalsMenuBox.setAccelerator(KeyStroke.getKeyStroke('V',ActionEvent.CTRL_MASK));
        dropVerticalsMenuBox.setSelected(false);
        dropVerticalsMenuBox.setToolTipText("Draw lines from curves to the axis on the cartesian graph");
        displayMenu.add(dropVerticalsMenuBox);
        bottomMenuBox = new JCheckBoxMenuItem("Hide Display");
        bottomMenuBox.addActionListener(this);
        bottomMenuBox.setAccelerator(KeyStroke.getKeyStroke('H',ActionEvent.CTRL_MASK));
        bottomMenuBox.setSelected(false);
        bottomMenuBox.setToolTipText("Hide the accessibility features below the graphs");
        displayMenu.add(bottomMenuBox);
        displayMenu.addSeparator();
        passwordItem = new JMenuItem("Identify Functions");
        passwordItem.addActionListener(this);
        passwordItem.setToolTipText("Reveal the names of Mystery Functions A,B, and C");
        displayMenu.add(passwordItem);
        //make the help menu, initialize menu options
        helpMenu = new JMenu("Help");
        helpMenu.setToolTipText("Instructions and About sections");
        howToItem = new JMenuItem("Instructions");
        howToItem.setAccelerator(KeyStroke.getKeyStroke('I',ActionEvent.CTRL_MASK));
        howToItem.setToolTipText("How to use the features in this program");
        howToItem.addActionListener(this);
        helpMenu.add(howToItem);
        funcItem = new JMenuItem("Functions List");
        funcItem.setToolTipText("List of functions and constants that can be used when specifying the angle");
        funcItem.setAccelerator(KeyStroke.getKeyStroke('F',ActionEvent.CTRL_MASK));
        funcItem.addActionListener(this);
        helpMenu.add(funcItem);
        aboutItem = new JMenuItem("About");
        aboutItem.setToolTipText("About this Program");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);
        //add angle, display, and help menus to the menu bar
        //don't add menu bar until progress bar finishes
        menuBar.add(angleMenu);
        menuBar.add(displayMenu);
        menuBar.add(helpMenu);
        menuBar.setVisible(true);
        //make the upper level JPanel to contain the manual accessibility feautures at the bottom of the window
        valueComponents = new JPanel(new FlowLayout());
        //initialize the scrollbar that changes the angle value
        angleBar = new JScrollBar(JScrollBar.HORIZONTAL, (int)angle, 1, -720, 720);
        angleBar.setPreferredSize(new Dimension(180,20));
        angleBar.addAdjustmentListener(this);
        //initialize the text field displaying the value of the angle
        angleText = new JTextField(df.format(angle)+".0"+angleUnit, 6);
        angleText.setEditable(false);
        angleText.addMouseListener(this);
        //make the JPanel holding the angle bar and the angle text field
        //add the components, title the JPanel, and add the JPanel to the upper level JPanel
        anglePanel = new JPanel();
        anglePanel.add(angleBar);
        anglePanel.add(angleText);
        anglePanel.setBorder(BorderFactory.createTitledBorder("Angle"));
        ((TitledBorder)anglePanel.getBorder()).setTitleColor(Color.RED);
        valueComponents.add(anglePanel);
        //initialize the sine checkbox
        sinBox = new JCheckBox();
        sinBox.setForeground(Color.BLUE);
        sinBox.setMnemonic(KeyEvent.VK_S);
        sinBox.setSelected(true);
        sinBox.addActionListener(this);
        //initialize the sine text field
        sinText = new JTextField(df.format(Math.sin(radians())), 5);
        valueComponents.add(sinText, BorderLayout.SOUTH);
        sinText.setEditable(false);
        //make and title the sine JPanel, add it to the upper level JPanel
        sinPanel = new JPanel();
        sinPanel.add(sinBox);
        sinPanel.add(sinText);
        sinPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func A"));
        ((TitledBorder)sinPanel.getBorder()).setTitleColor(Color.BLUE);
        valueComponents.add(sinPanel);
        //do the same with the cosine features
        cosBox = new JCheckBox();
        cosBox.setForeground(Color.GREEN.darker());
        cosBox.setMnemonic(KeyEvent.VK_C);
        cosBox.setSelected(false);
        cosBox.addActionListener(this);
        cosText = new JTextField(df.format(Math.cos(radians())), 5);
        cosText.setEditable(false);
        cosPanel = new JPanel();
        cosPanel.add(cosBox);
        cosPanel.add(cosText);
        cosPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func B"));
        ((TitledBorder)cosPanel.getBorder()).setTitleColor(Color.GREEN.darker());
        valueComponents.add(cosPanel);
        //do the same with the tangent feautures
        tanBox = new JCheckBox();
        tanBox.setForeground(Color.ORANGE.darker());
        tanBox.setMnemonic(KeyEvent.VK_T);
        tanBox.setSelected(false);
        tanBox.addActionListener(this);
        tanText = new JTextField(df.format(Math.tan(radians())), 5);
        tanText.setEditable(false);
        tanPanel = new JPanel();
        tanPanel.add(tanBox);
        tanPanel.add(tanText);
        tanPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func C"));
        ((TitledBorder)tanPanel.getBorder()).setTitleColor(Color.ORANGE);
        valueComponents.add(tanPanel);
        //make the preference panel that will contain angle unit radio buttons
        //add a checkbox to turn on/off gridlines, and add to the upper level JPanel
        degreeButton = new JRadioButton("Degrees");
        degreeButton.addActionListener(this);
        degreeButton.setSelected(true);
        radianButton = new JRadioButton("Radians");
        radianButton.addActionListener(this);
        radianButton.setSelected(false);
        gridBox = new JCheckBox("Gridlines");
        gridBox.addActionListener(this);
        angleMode = new ButtonGroup();
        angleMode.add(degreeButton);
        angleMode.add(radianButton);
        JPanel preferencePanel = new JPanel();
        preferencePanel.add(degreeButton);
        preferencePanel.add(radianButton);
        preferencePanel.add(gridBox);
        preferencePanel.setBorder(BorderFactory.createTitledBorder("Preferences"));
        ((TitledBorder)preferencePanel.getBorder()).setTitleColor(Color.BLACK);
        valueComponents.add(preferencePanel);
        //add the upper level JPanel to the bottom of the window (BorderLayout.SOUTH)
        add(valueComponents, BorderLayout.SOUTH);
        valueComponents.setVisible(false);
        cs = new CartesianSpan(angle);
        cs.setVisible(false);
        add(cs,BorderLayout.CENTER);
        cs.setLocation(0,0);
        //make the upper panel to hold the unit circle and cartesian graphs
        graphPanel = new JPanel(null);
        graphPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(),BorderFactory.createRaisedBevelBorder()));
        //make,add, and position the graphs in the JPanel
        uc = new UnitCircle(angle);
        tf = new TrigFuncs(angle);
        graphPanel.add(uc);
        graphPanel.add(tf);
        uc.setLocation(0,100);
        tf.setLocation(510,100);
        graphPanel.setLocation(0,100);
        graphPanel.setVisible(false);
        //add the graph panel to the center/top of the window
        add(graphPanel, BorderLayout.CENTER);

        //allow the window to close upon clicking the red x at the top right of the window
        addWindowListener(  new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        //make the JTextArea that will dynamically display the values of the trig functions
        //when viewing them in the JDialog made by clicking "View Values" under the Angle Menu
        valueArea = new JTextArea("Angle\t= "+angleMeasure()+"\nSine\t= "+sinText.getText()+"\nCosine\t= "+cosText.getText()+"\nTangent\t= "+tanText.getText());
        valueArea.setEditable(false);
        //initialize the autocycle timer, don't start it
        autocycleTimer = new Timer(50,this);
        //allow the window to pick up on mouse wheel scrolling to change the angle
        addMouseWheelListener(this);
        //set the window size
        setSize(880,725);
        //make the window size fixed
        setResizable(false);
        //showthe window
        setVisible(true);
        //start the progress bar with its own timer
        progressTimer = new Timer(30,this);
        progressTimer.start();
    }
    /**
     *
     */
    public void adjustmentValueChanged(AdjustmentEvent ae){
        angle = angleBar.getValue();
        updateFields();
    }
    public void stateChanged(ChangeEvent ce){
        repaint();
    }
    public void updateFields(){
        angleText.setText(angleMeasure());
        sinText.setText(df.format(Math.sin(radians())));
        cosText.setText(df.format(Math.cos(radians())));
        if((angle-90) % 180 == 0){
            tanText.setText("undefined");
        }
        else{
            tanText.setText(df.format(Math.tan(radians())));
        }
        if(tanText.getText().equals("-0")){
            tanText.setText("0");
        }
        valueArea.setText("Angle\t= "+angleMeasure()+"\nSine\t= "+sinText.getText()+"\nCosine\t= "+cosText.getText()+"\nTangent\t= "+tanText.getText());
        uc.setAngle(angle);
        tf.setAngle(angle);
        cs.setAngle(angle);

        repaint();
    }
    public void actionPerformed(ActionEvent ae){
        Object source = ae.getSource();
        if(source.equals(degreeButton) || source.equals(degreeMenuButton)){
            angleUnit = DEGREE_SYMBOL;
            degreeButton.setSelected(true);
            degreeMenuButton.setSelected(true);
            angleText.setText(angleMeasure());
        }
        else if(source.equals(radianButton) || source.equals(radianMenuButton)){
            angleUnit = RADIAN_SYMBOL;
            radianButton.setSelected(true);
            radianMenuButton.setSelected(true);
            angleText.setText(angleMeasure());
        }
        else if(source.equals(gridBox) || source.equals(gridMenuBox)){
            if(uc.showGridlines()){
                uc.setGridlines(false);
                tf.setGridlines(false);
                gridBox.setSelected(false);
                gridMenuBox.setSelected(false);
            }
            else{
                uc.setGridlines(true);
                tf.setGridlines(true);
                gridBox.setSelected(true);
                gridMenuBox.setSelected(true);
            }
            repaint();
        }
        else if(source.equals(setAngleItem)){
            setAngle();
        }
        else if(source.equals(viewTrigValuesItem)){
            String [] options;
            int choice = -1;
            do{
                if(autocycleTimer.isRunning()){
                    options = new String [] {"Stop Autocycle","OK"};
                }
                else{
                    options = new String [] {"Autocycle","OK"};
                }
                choice = JOptionPane.showOptionDialog(this,valueArea,"Trigonometric Values",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,"Close");

                if(choice == 0){
                    if(autocycleTimer.isRunning()){
                        autocycleTimer.stop();
                    }
                    else if(angle <= 360){
                        angle = 0;
                        autocycleTimer.start();
                    }
                }
            }while(choice == 0);
        }
        else if(source.equals(bottomMenuBox)){
            valueComponents.setVisible(!bottomMenuBox.isSelected());
            if(bottomMenuBox.isSelected()) setSize(getWidth(),getHeight()-71);
            else setSize(getWidth(),getHeight()+71);
        }
        else if(source.equals(sinBox)||source.equals(sinBoxMenu)){
            uc.switchSine();
            tf.switchSine();
            cs.switchSine();
            sinBox.setSelected(uc.isSinOn());
            sinBoxMenu.setSelected(uc.isSinOn());
            repaint();
        }
        else if(source.equals(cosBox)||source.equals(cosBoxMenu)){
            uc.switchCosine();
            tf.switchCosine();
            cs.switchCosine();
            cosBox.setSelected(uc.isCosOn());
            cosBoxMenu.setSelected(uc.isCosOn());
            repaint();
        }
        else if(source.equals(tanBox)||source.equals(tanBoxMenu)){
            uc.switchTangent();
            tf.switchTangent();
            cs.switchTangent();
            tanBox.setSelected(uc.isTanOn());
            tanBoxMenu.setSelected(uc.isTanOn());
            repaint();
        }
        else if(source.equals(howToItem)){
            String helpText = "This program contains two graphs; the one on the\n"+
                    "left is of the unit circle, and the one on the right\n"+
                    "is of the trig functions on a cartesian corrdinate plane.\n"+
                    "Green represents cosine, blue represents sine, orange\n"+
                    "represents tangent and the red line is the angle measure.\n\n"+
                    "To change the angle use the scrollbar at the bottom of\n"+
                    "the screen. To switch between radians and degrees use\n"+
                    "the button on the other side. To set the angle manually,\n"+
                    "click on the unit angle measure to open the angle entry form.\n\n"+
                    "The unit circle is centered on the origin and is one unit high\n"+
                    "by one unit wide. In the cartesian graph, each square is 0.1 units\n"+
                    "high by 20 degrees wide.\n\n"+
                    "You may notice that the pictures don't draw perfectly with respect\n"+
                    "to the gridlines; this is because the computer can't calculate values\n"+
                    "of trigonometric functions with perfect precision. So as Dr. Matsko\n"+
                    "says, you should be using your head instead of your calculator!";
            JOptionPane.showMessageDialog(this,helpText,"Instructions",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(source.equals(funcItem)){
            String funcText = "pi\t= pi\n"+
                    "e\t= e\n\n"+
                    "plus\t= +\n"+
                    "minus\t= -\n"+
                    "times\t= *\n"+
                    "divide\t= /\n"+
                    "sine\t= sin(_)\n"+
                    "cosine\t= cos(_)\n"+
                    "tangent\t= tan(_)\n"+
                    "inverse sine\t= arcsin(_)\n"+
                    "inverse cosine\t= arccos(_)\n"+
                    "inverse tangent\t= arctan(_)\n"+
                    "square root\t= sqrt(_)\n"+
                    "natural log\t= ln(_)\n";
            JTextArea funcArea = new JTextArea(funcText);
            funcArea.setEditable(false);
            JOptionPane.showMessageDialog(this,funcArea,"List of Mathematical Operators",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(source.equals(aboutItem)){
            String infoText = "Dr. Vincent J. Matsko's Amazing Unit Circle App!\n"+
                    "(C)Saieesh Rao '13\n"+
                    "Illinois Mathematics and Science Academy\n"+
                    "vincematsko.com";
            JOptionPane.showMessageDialog(this,infoText,"About",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(source.equals(autocycleItem)){
            angle = autocycleLower;
            angleBar.setValue(autocycleLower);
            autocycleSubmenu.remove(autocycleItem);
            autocycleSubmenu.insert(autocycleStopItem,0);
            updateFields();
            autocycleTimer.start();
        }
        else if(source.equals(autocycleStopItem)){
            autocycleTimer.stop();
            autocycleSubmenu.remove(autocycleStopItem);
            autocycleSubmenu.insert(autocycleItem,0);
        }
        else if(source.equals(autocycleTimer)){
            inc();
            repaint();
            viewTrigValuesItem.setToolTipText("See the value of Sine, Cosine, and Tangent at "+angleMeasure());
            if(angle>=autocycleUpper && !autocycleRepeat){
                autocycleTimer.stop();
                autocycleSubmenu.remove(autocycleStopItem);
                autocycleSubmenu.insert(autocycleItem,0);
            }
            else if(angle>=autocycleUpper && autocycleRepeat){
                angle = autocycleLower;
                angleBar.setValue(autocycleLower);
                updateFields();
            }
        }
        else if(source.equals(progressTimer)){
            progressBar.setValue(progressBar.getValue()+1);
            if(progressBar.getValue()>=100){
                progressBar.setVisible(false);
                graphPanel.setVisible(true);
                cs.setVisible(true);
                valueComponents.setVisible(true);
                setJMenuBar(menuBar);
                progressTimer.stop();
                repaint();
            }
            if(progressBar.getValue()<12){
                progressBar.setString("LOADING FONTS...");
                progressTimer.setDelay(10);
            }
            else if(progressBar.getValue()<25){
                progressBar.setString("LOADING GRAPHICS...");
                progressTimer.setDelay(25);
            }
            else if(progressBar.getValue()<46){
                progressBar.setString("LOADING ALGEBRA...");
                progressTimer.setDelay(35);
            }
            else if(progressBar.getValue()<66){
                progressBar.setString("LOADING TRIG...");
                progressTimer.setDelay(65);
            }
            else if(progressBar.getValue()<81){
                progressBar.setString("LOADING TEXT...");
                progressTimer.setDelay(15);
            }
            else if(progressBar.getValue()<88){
                progressBar.setString("LOADING INTERFACE...");
                progressTimer.setDelay(30);
            }
            else if(progressBar.getValue()<100){
                progressBar.setString("PREPARING...");
                progressTimer.setDelay(10);
            }
            repaint();
        }
        else if(source.equals(dropVerticalsMenuBox)){
            tf.setVerticals(dropVerticalsMenuBox.isSelected());
        }
        else if(source.equals(passwordItem)){
            JPasswordField passField = new JPasswordField(10);
            JOptionPane.showMessageDialog(this,passField,"Password Protection",JOptionPane.QUESTION_MESSAGE);
            if(new String(passField.getPassword()).equals(IDENTIFY_FUNCTIONS_PASSWORD)){
                sinPanel.setBorder(BorderFactory.createTitledBorder("Sine"));
                ((TitledBorder)sinPanel.getBorder()).setTitleColor(Color.BLUE);
                cosPanel.setBorder(BorderFactory.createTitledBorder("Cosine"));
                ((TitledBorder)cosPanel.getBorder()).setTitleColor(Color.GREEN.darker());
                tanPanel.setBorder(BorderFactory.createTitledBorder("Tangent"));
                ((TitledBorder)tanPanel.getBorder()).setTitleColor(Color.ORANGE);
                sinBoxMenu.setText("Graph Sine");
                sinBoxMenu.setToolTipText("Draw sine in the unit circle and the sine curve in the graph");
                cosBoxMenu.setText("Graph Cosine");
                cosBoxMenu.setToolTipText("Draw cosine in the unit circle and the cosine curve in the graph");
                tanBoxMenu.setText("Graph Tangent");
                tanBoxMenu.setToolTipText("Draw tangent in the unit circle and the tangent curve in the graph");
                displayMenu.remove(displayMenu.getItemCount()-1);//remove password option
                displayMenu.remove(displayMenu.getItemCount()-1);//remove separator
            }
            else{
                JOptionPane.showMessageDialog(this,"Access Denied\nAsk your instructor for authorization","Access Denied",JOptionPane.ERROR_MESSAGE);
                sinPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func A"));
                ((TitledBorder)sinPanel.getBorder()).setTitleColor(Color.BLUE);
                cosPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func B"));
                ((TitledBorder)cosPanel.getBorder()).setTitleColor(Color.GREEN.darker());
                tanPanel.setBorder(BorderFactory.createTitledBorder("Mystery Func C"));
                ((TitledBorder)tanPanel.getBorder()).setTitleColor(Color.ORANGE);
                sinBoxMenu.setText("Graph Mystery Func A");
                sinBoxMenu.setToolTipText("Draw Mystery Func A in the unit circle and the Mystery Func A curve in the graph");
                cosBoxMenu.setText("Graph Mystery Func B");
                cosBoxMenu.setToolTipText("Draw Mystery Func B in the unit circle and the Mystery Func B curve in the graph");
                tanBoxMenu.setText("Graph Mystery Func C");
                tanBoxMenu.setToolTipText("Draw Mystery Func C in the unit circle and the Mystery Func C curve in the graph");
            }
        }
        else if(source.equals(autocycleSpeedItem)){
            JSlider speedSlider = new JSlider(0,300,300-autocycleTimer.getDelay());
            JPanel pane = new JPanel();
            pane.add(new JLabel("Slowest"));
            pane.add(speedSlider);
            pane.add(new JLabel("Fastest"));
            JOptionPane.showMessageDialog(this,pane,"Set Autocycle Speed",JOptionPane.QUESTION_MESSAGE);
            autocycleTimer.setDelay(300-speedSlider.getValue());
        }
        else if(source.equals(autocycleBoundsItem)){
            JLabel lowerLabel = new JLabel("Lower");
            SpinnerModel lowerModel = new SpinnerNumberModel(autocycleLower,-720,720,1);
            JSpinner lowerSpinner = new JSpinner(lowerModel);
            JLabel upperLabel = new JLabel("Upper");
            SpinnerModel upperModel = new SpinnerNumberModel(autocycleUpper,-720,720,1);
            JSpinner upperSpinner = new JSpinner(upperModel);
            JPanel spinnerPanel = new JPanel();
            spinnerPanel.add(lowerLabel,BorderLayout.NORTH);
            spinnerPanel.add(lowerSpinner,BorderLayout.NORTH);
            spinnerPanel.add(upperLabel,BorderLayout.SOUTH);
            spinnerPanel.add(upperSpinner,BorderLayout.SOUTH);
            JCheckBox loopBox= new JCheckBox("Repeat the autocycle in a loop");
            loopBox.setSelected(autocycleRepeat);
            JPanel checkboxPanel = new JPanel();
            checkboxPanel.add(loopBox);
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(spinnerPanel,BorderLayout.NORTH);
            mainPanel.add(checkboxPanel,BorderLayout.SOUTH);
            JOptionPane.showMessageDialog(this,mainPanel,"Autocycle Bounds",JOptionPane.QUESTION_MESSAGE);
            if(((Integer)lowerSpinner.getValue()).intValue()>=((Integer)upperSpinner.getValue()).intValue()){
                JOptionPane.showMessageDialog(this,"The Upper Bound must be greater than the Lower Bound","Math Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                autocycleLower = ((Integer)lowerSpinner.getValue()).intValue();
                autocycleUpper = ((Integer)upperSpinner.getValue()).intValue();
                autocycleRepeat = loopBox.isSelected();
            }
        }
    }
    private void inc(){
        angle++;
        angleBar.setValue((int)angle);
        updateFields();
    }

    private double radians(){
        return angle/180.0*Math.PI;
    }
    private String angleMeasure(){
        if(angleUnit.equals(DEGREE_SYMBOL)){
            df.format(angle);
            return df.format(angle)+DEGREE_SYMBOL;
        }
        else{
            return df.format(radians()/Math.PI)+"*PI";//+RADIAN_SYMBOL;
        }
    }

    public static void main(String [] args){
        UnitCircleApp app = new UnitCircleApp();
    }

    public void mousePressed(MouseEvent me){
        Object source = me.getSource();
        if(source.equals(angleText)) setAngle();
    }
    private void setAngle(){
        String unit;
        if(angleUnit.equals(DEGREE_SYMBOL)){
            unit = "Degrees";
        }
        else{
            unit = "Radians";
        }
        String tempInput = JOptionPane.showInputDialog(this,null,"Set Angle in "+unit,JOptionPane.QUESTION_MESSAGE);
        if(tempInput==null) return;
        try{
            Double intermediate = null;
            if(angleUnit.equals(DEGREE_SYMBOL)){
                intermediate = new Double((parse(tempInput).doubleValue()));

                if(intermediate.equals(Double.NaN)){
                    JOptionPane.showMessageDialog(this,"Result is not a real number","Math Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    angle = intermediate;
                }
            }
            else{

                intermediate = new Double((180.0*(parse(tempInput).doubleValue()/Math.PI)));

                if(intermediate.equals(Double.NaN)){
                    JOptionPane.showMessageDialog(this,"Result is not a real number","Math Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    angle = intermediate;
                }
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Invalid Math Input","Math Error",JOptionPane.ERROR_MESSAGE);
        }
        finally{
            uc.setAngle(angle);
            tf.setAngle(angle);
            cs.setAngle(angle);
            angleText.setText(angleMeasure());
            angleBar.setValue((int)angle);
            repaint();
        }
    }
    private Double parse(String foo) throws Exception{
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("JavaScript");
        engine.put("PI",Math.PI);
        engine.put("E",Math.E);
        StringBuffer expression = new StringBuffer(foo.toUpperCase());
        if(angleUnit.equals(RADIAN_SYMBOL)){
            replaceAll(expression,"SIN(","Math.sin(","ARC");
            replaceAll(expression,"COS(","Math.cos(","ARC");
            replaceAll(expression,"TAN(","Math.tan(","ARC");
            replaceAll(expression,"ARCSIN(","Math.asin(");
            replaceAll(expression,"ARCCOS(","Math.acos(");
            replaceAll(expression,"ARCTAN(","Math.atan(");
        }
        else{
            replaceAll(expression,"SIN(","Math.sin(PI/180*","ARC");
            replaceAll(expression,"COS(","Math.cos(PI/180*","ARC");
            replaceAll(expression,"TAN(","Math.tan(PI/180*","ARC");
            replaceAll(expression,"ARCSIN(","180/PI*Math.asin(");
            replaceAll(expression,"ARCCOS(","180/PI*Math.acos(");
            replaceAll(expression,"ARCTAN(","180/PI*Math.atan(");
        }
        replaceAll(expression,"SQRT","Math.sqrt");
        replaceAll(expression,"LN","Math.log");

        return ((Double)(engine.eval(expression.toString()))).doubleValue();
    }
    private void replaceAll(StringBuffer s, String key, String value){
        int ind = 0;
        while(ind < s.length()-key.length()){
            if(s.substring(ind,ind+key.length()).equals(key)){
                s.delete(ind,ind+key.length());
                s.insert(ind,value);
                ind+=value.length();
            }
            ind++;
        }
    }
    private void replaceAll(StringBuffer s, String key, String value, String unlessPrecededBy){
        int ind = 0;
        while(ind < s.length()-key.length()){
            if(s.substring(ind,ind+key.length()).equals(key)){
                String preceding = s.substring(ind-unlessPrecededBy.length(),ind);
                if(!preceding.toUpperCase().equals(unlessPrecededBy.toUpperCase())){
                    s.delete(ind,ind+key.length());
                    s.insert(ind,value);
                    ind+=value.length();
                }
            }
            ind++;
        }
    }
    public void mouseReleased(MouseEvent me){

    }
    public void mouseEntered(MouseEvent me){

    }
    public void mouseExited(MouseEvent me){

    }
    public void mouseClicked(MouseEvent me){

    }
    public void mouseWheelMoved(MouseWheelEvent mwe){
        angle += mwe.getWheelRotation();
        angleBar.setValue((int)(angle));
        updateFields();
        angle = Math.max(angle,-720);
        angle = Math.min(angle,719);
        repaint();
    }
}
