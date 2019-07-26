/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalcKeyboardListeners.java
 * The purpose of this class is to create keyboard listeners. This
 * allows the user to input keyboard commands, in addition to the
 * GUI buttons.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EmptyStackException;

public class CalcKeyboardListeners extends CalcButtonFrame implements KeyListener {

    //constructor to connect equation display and keyboard listeners
    public CalcKeyboardListeners() {
        equationDisplayArea.addKeyListener(this);

    }

    //gets the panel that display is shown on and allows it to be focusable
    public void setFocusToKeyboard(){
        getDisplayPanel().setFocusable(true);
    }

    //not using, must be here for implements KeyListener
    public void keyTyped(KeyEvent e){
    }

    //If a key is pressed it adds the key to the display
    // or resets the display and adds the key if an answer
    // is already being displayed
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_0) {
            resetEquationArea(getEquationArea());
            setDisplay("0");
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            resetEquationArea(getEquationArea());
            setDisplay("1");
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            resetEquationArea(getEquationArea());
            setDisplay("2");
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            resetEquationArea(getEquationArea());
            setDisplay("3");
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            resetEquationArea(getEquationArea());
            setDisplay("4");
        } else if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_5) {
            resetEquationArea(getEquationArea());
            setDisplay(" % ");
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            resetEquationArea(getEquationArea());
            setDisplay("5");
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            resetEquationArea(getEquationArea());
            setDisplay("6");
        } else if (e.getKeyCode() == KeyEvent.VK_7) {
            resetEquationArea(getEquationArea());
            setDisplay("7");
        }
        //MUST APPEAR BEFORE 8
        else if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_8) {
            resetEquationArea(getEquationArea());
            setDisplay(" * ");
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            resetEquationArea(getEquationArea());
            setDisplay("8");
        } else if (e.getKeyCode() == KeyEvent.VK_9) {
            resetEquationArea(getEquationArea());
            setDisplay("9");
        } else if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_EQUALS) {
            resetEquationArea(getEquationArea());
            setDisplay(" + ");
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            resetEquationArea(getEquationArea());
            setDisplay(" - ");
        }else if (e.getKeyCode() == KeyEvent.VK_SLASH) {
            resetEquationArea(getEquationArea());
            setDisplay(" / ");
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            resetEquationArea(getEquationArea());
            clearDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            resetEquationArea(getEquationArea());
            backspaceDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_EQUALS ||
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String x = getDisplay();
                setDisplay(evaluateEquation.evaluate(x));
                setEquationAreaTrue(true);

            }
            //Number is of form 1 + , + 1 etc..
            catch (NumberFormatException ex) {
                resetEquationArea(getEquationArea());
                setNewDisplay("ERROR");
            }
            //Saves from backspace after display is empty
            catch (EmptyStackException ex) {
                resetEquationArea(getEquationArea());
                setNewDisplay("ERROR");
            }

        }
    }

    //not using, must be here for implements KeyListener
    public void keyReleased(KeyEvent e){
    }

}
