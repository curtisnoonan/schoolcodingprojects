/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcButtonFrame.java
 * The purpose of this class is to both create the calculator
 * buttons and the action listeners that control those buttons.
 */

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;

import java.util.EmptyStackException;

public class CalcButtonFrame extends CalcDisplay{

    //calculator buttons
    static JButton[] buttonArray = new JButton[20];

    //in order to call evaluate functions
    static CalcEval evaluateEquation = new CalcEval();

    //in order to connect keyboard listeners
    static CalcKeyboardListeners keyboardListeners = new CalcKeyboardListeners();

    //controls the reset of the display once an answer is shown
    static boolean answerShown = false;

    public CalcButtonFrame() {
    }

    //creates the buttons and adds them to the array and the panel
    public static JPanel createButtons() {

        JPanel buttonPanel;
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 6, 0, 0));
        String setButtons[] = {"+", "-", "%", "*", "/", " ", "C", "<", "=",
                            "1", "2", "3", "4", "5", "6", "7", "8", "9","", "0"};

        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i] = new JButton(setButtons[i]);
            buttonPanel.add(buttonArray[i]);
            buttonArray[i].setPreferredSize(new Dimension(50,50));
            buttonArray[i].setFont(new Font("Arial",Font.PLAIN,20));
            buttonArray[i].setBackground(Color.BLACK);
            buttonArray[i].setForeground(Color.WHITE);
            buttonArray[i].setOpaque(true);
            buttonArray[i].setBorderPainted(false);
        }

        return buttonPanel;
    }

    //Once user clicks equal, the answer shown is changed to true
    public static void setEquationAreaTrue(boolean isAnswered){
        answerShown = isAnswered;
    }

    //Method to check if answer is currently displayed
    public static boolean getEquationArea(){
        return answerShown;
    }

    //checks if the display is showing an equation if it is, clears the display
    //this is to start a new equation after an answer is shown
    public static void resetEquationArea(boolean equationArea){
        if(equationArea || getDisplay().equals("ERROR")){
            clearDisplay();
            answerShown = false;
        }
    }

    //Once a button is pressed it takes focus from keyboard
    //Allows keys to be pressed after a button has been pressed.
    public static void handleKeyboardListeners(){
        equationDisplayArea.requestFocus(true);
        keyboardListeners.setFocusToKeyboard();
    }

    //Adds the action listeners to the Jbuttons created earlier
    //Sets display to button pressed, as well as backspace, clear,
    // and when equals is pressed equation is evaluated.
    public static void setListeners() {
        buttonArray[0].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay(" + ");
            handleKeyboardListeners();
        });
        buttonArray[1].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay(" - ");
            handleKeyboardListeners();
        });
        buttonArray[2].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay(" % ");
            handleKeyboardListeners();
        });
        buttonArray[3].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay(" * ");
            handleKeyboardListeners();
        });
        buttonArray[4].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay(" / ");
            handleKeyboardListeners();
        });
        buttonArray[6].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            clearDisplay();
            handleKeyboardListeners();
        });
        buttonArray[9].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("1");
            handleKeyboardListeners();
        });
        buttonArray[10].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("2");
            handleKeyboardListeners();
        });
        buttonArray[11].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("3");
            handleKeyboardListeners();
        });
        buttonArray[12].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("4");
            handleKeyboardListeners();
        });
        buttonArray[13].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("5");
            handleKeyboardListeners();
        });
        buttonArray[14].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("6");
            handleKeyboardListeners();
        });
        buttonArray[15].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("7");
            handleKeyboardListeners();
        });
        buttonArray[16].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("8");
            handleKeyboardListeners();
        });
        buttonArray[17].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("9");
            handleKeyboardListeners();
        });
        buttonArray[19].addActionListener(e -> {
            resetEquationArea(getEquationArea());
            setDisplay("0");
            handleKeyboardListeners();
        });
        buttonArray[7].addActionListener(e -> {
            backspaceDisplay();
            handleKeyboardListeners();
        });
        buttonArray[8].addActionListener(e -> {
            try {
                setDisplay(evaluateEquation.evaluate(getDisplay()));
                setEquationAreaTrue(true);

            }
            //Number is of form 1 + , + 1 etc..
            catch(NumberFormatException ex){
                resetEquationArea(getEquationArea());
                setNewDisplay("ERROR");
            }

            //Saves from backspace after display is empty
            catch(EmptyStackException ex){
                resetEquationArea(getEquationArea());
                setNewDisplay("ERROR");
            }
            handleKeyboardListeners();
        });
    }



}
