/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcMain.java
 * This class controls the "Simple Calculator" application which includes
 * basic arithmetic operations (*,/,+,-) and the mod (modulo) operation (%),
 * including the use of "(" and ")". The purpose of this class is to create the frame
 * and set the frame to visible.
 */

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;

public class CalcMain {

    //for JMenuBar including the exit JMenuItem which exits the application
    static CalculatorMenu calculatorMenu = new CalculatorMenu();

    public static void main(String args[]){

        //main frame of application
        JFrame frame = new CalcFrame();

        frame.setTitle("Simple Calculator");

        //sets at the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        //Closes the application safely
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adds the JMenuBar
        frame.setJMenuBar(calculatorMenu);

        //sets the background to black
        ((CalcFrame) frame).getDisplayFrame().setBackground(Color.BLACK);

        //allows the frame to be seen
        frame.setVisible(true);
    }
}