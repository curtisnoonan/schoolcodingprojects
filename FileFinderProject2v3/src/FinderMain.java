/**
 * Author: Curtis Noonan
 * Oct 9, 2018
 * COMP 585 GUI
 * FinderMain.java
 * The purpose of this class is to create the
 * base frame.
 *
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class FinderMain {

    static FileFinderMenu fileFinderMenu = new FileFinderMenu();

    public static void main(String args[]){


        //main frame of application
        JFrame frame = new FileFinderFrame();

        frame.setTitle("File Finder");

        //sets at middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        //Closes the application safely
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adds the JMenuBar
        frame.setJMenuBar(fileFinderMenu);
        frame.pack();
        //allows the frame to be seen
        frame.setVisible(true);
    }
}
