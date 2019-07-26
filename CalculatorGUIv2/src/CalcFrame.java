/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcFrame.java
 * The purpose of this class is to call the display and button creation
 * classes, this class is called in called in CalcMain.java to create
 * needed swing objects.
 */

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

public class CalcFrame extends JFrame {

    //controls frame width and height
    private static final int FRAME_WIDTH = 490;
    private static final int FRAME_HEIGHT = 500;

    //panel that is added to frame
    private JPanel basePanel = new JPanel();

    //adds buttons
    private JPanel buttonPanel;

    //adds display
    private JPanel displayPanel;

    //to create  JTextField that displays output
    CalcDisplay calcDisplay = new CalcDisplay();

    //to create the JButtons and ActionListeners that control the display
    CalcButtonFrame calcButtons = new CalcButtonFrame();

    //Constructor called in CalcMain's main to create application
    public CalcFrame() {

        //sets the panel color and layout
        basePanel.setBackground(Color.BLACK);
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

        //processes that create buttons and display
        createFrame();

        //sets frame to the size specified at the start
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    //returns the JPanel that displays the equation
    public JPanel getDisplayFrame(){
        return displayPanel;
    }

    //Creates the display and buttons as well as
    private void createFrame() {

        //Adds a JTextArea to a panel and adds to the base panel
        displayPanel = calcDisplay.createDisplay();
        displayPanel.setLayout(new GridLayout(1,1));
        basePanel.add(displayPanel);

        //Adds a gridlayout of JButtons to the frame
        buttonPanel = calcButtons.createButtons();
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //Adds the listeners to the buttons
        calcButtons.setListeners();

        //Sets the buttons to black
        buttonPanel.setBackground(Color.BLACK);

        //adds buttons to the basepanel
        basePanel.add(buttonPanel);

        //adds base panel to base frame
        add(basePanel);

    }

}
