/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcDisplay.java
 * The purpose of this class is to create the calculator's display,
 * as well as, modify the display, and retrieve the entered equation.
 */

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public class CalcDisplay{

    //DISPLAY
    protected static JTextField equationDisplayArea = new JTextField();

    public CalcDisplay(){
    }

    //display is added to this panel
    static JPanel displayPanel;

    //Sets up and creates the display, returns a JPanel with an attached JTextField
    public static JPanel createDisplay(){
        displayPanel = new JPanel();
        equationDisplayArea.setColumns(25);
        Font font = new Font("Times New Roman",Font.BOLD,40);
        equationDisplayArea.setForeground(Color.WHITE);
        equationDisplayArea.setBackground(Color.BLACK);
        equationDisplayArea.setEditable(false);
        equationDisplayArea.setFont(font);
        equationDisplayArea.setHorizontalAlignment(JTextField.RIGHT);
        displayPanel.add(equationDisplayArea);
        return displayPanel;
    }

    //returns the text in the JTextField (equation displayed)
    public static String getDisplay(){
        return equationDisplayArea.getText();
    }

    //returns the JPanel the display is contained on
    public static JPanel getDisplayPanel(){
        return displayPanel;
    }

    //adds to the display a String of the button that is pressed
    public static void setDisplay(String newDisplay){
        equationDisplayArea.setText(equationDisplayArea.getText() + newDisplay);
    }

    //sets the display with a Double when a button is pressed, for displaying final result
    public static void setDisplay(double newDisplay){
        equationDisplayArea.setText(Double.toString(newDisplay));
    }

    //sets the display with the a new String for starting an equation
    public static void setNewDisplay(String newDisplay){
        equationDisplayArea.setText(newDisplay);
    }

    //clears the display
    public static void clearDisplay(){
        equationDisplayArea.setText("");
    }

    //removes 1 character from the display
    public static void backspaceDisplay(){
        if(getDisplay().length() > 0) {
            equationDisplayArea.setText(equationDisplayArea.getText().substring(0, equationDisplayArea.getText().length() - 1));
        }
    }

}