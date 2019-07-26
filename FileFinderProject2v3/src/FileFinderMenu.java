/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * CalculatorMenu.java
 * The purpose of this class is to create and add a JMenuBar
 * to the calculator application.
 */
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;

public class FileFinderMenu extends JMenuBar{

    static JMenu menu;

    //constructor to create JMenu and add the exit item
    //sets "exit" JMenuItem to safely exit the calculator
    public FileFinderMenu(){
        menu = new JMenu("File");
        JMenuItem exit = new JMenuItem(new AbstractAction("EXIT") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(exit);
        this.add(menu);
    }

}