/**
 * Author: Curtis Noonan
 * Oct 9, 2018
 * COMP 585 GUI
 * FileFinderFrame.java
 * The purpose of this class is to create the
 * base frame.
 *
 */
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.BorderLayout;

import java.io.File;
import java.util.prefs.Preferences;

public class FileFinderFrame extends JFrame {

    //controls frame width and height
    private static final int FRAME_WIDTH = 490;
    private static final int FRAME_HEIGHT = 500;

////PANELS//////////////////////////////
    //panel that is added to frame
    private JPanel basePanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JPanel boxPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel displayPanel = new JPanel();

    ////Labels, buttons, and comboboxes//////////////////////////////
    JLabel find = new JLabel("Find what:" );
    JLabel replace = new JLabel("Replace with:" );
    JLabel filters = new JLabel("Filters:" );
    JLabel dir = new JLabel("Directory:" );
    JLabel directory = new JLabel("Directories: " );

    static JComboBox findBox = new JComboBox();
    static JComboBox replaceBox = new JComboBox();
    static JComboBox filtersBox = new JComboBox();
    static JComboBox dirBox = new JComboBox();
    static DefaultListModel directoryList = new DefaultListModel();
    static JList directoryBox = new JList(directoryList);

    static JButton findButton = new JButton("Find");
    static JButton findInFilesButton = new JButton("Find In Files");
    static JButton replaceButton = new JButton("Replace");
    static JButton replaceAllButton = new JButton("Replace in Files");
    static JButton directoryButton = new JButton("...");
    static JButton multDirectoryButton = new JButton("Choose Multiple Files");

    static JCheckBox wholeWordSearch = new JCheckBox("Whole Word Search");
    static JCheckBox matchWordCase = new JCheckBox("Match Word Case");
    static boolean wholeWordBoolean = false;
    static boolean matchWordBoolean = false;

    static JTextArea displayResults = new JTextArea();
    static final JFileChooser fileChooser = new JFileChooser();
    static final JFileChooser multipleFileChoose = new JFileChooser();

    static Boolean hasAppeared = false;
    static JScrollPane scrollPane = new JScrollPane(displayResults);

    static String enteredDirectory;

    static int prevFindInput = 0;
    static int prevDirectoryInput = 100;

    static Logger logger = Logger.getLogger(FinderMain.class);

    private static Preferences prefs;

    //Constructor called in main to create application
    public FileFinderFrame() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        BasicConfigurator.configure();

        //processes that create buttons and display
        createFrame();

        //sets frame to the size specified at the start
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    //Creates preferences to store user data and adds frames to the base panel
    private void createFrame() {
        if(Integer.parseInt(prefs.get("FindInputs",null)) > 0) {
            //prevFindInput = Integer.parseInt(prefs.get("FindInputs", null));
        }
        if(Integer.parseInt(prefs.get("PrevDirInput",null)) > 0) {
            //prevDirectoryInput = Integer.parseInt(prefs.get("PrevDirInput", null));
        }
        scrollPane.setPreferredSize(new Dimension(50,200));
        basePanel.setLayout(new BorderLayout(0,0));
        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
        basePanel.add(getLabelFrame(),BorderLayout.WEST);
        basePanel.add(getBoxFrame(),BorderLayout.CENTER);
        basePanel.add(getButtonFrame(),BorderLayout.EAST);
        basePanel.add(getDisplayFrame(),BorderLayout.SOUTH);
        setListeners();
        AutoCompleteDecorator.decorate(findBox);
        AutoCompleteDecorator.decorate(dirBox);
        add(basePanel);
    }
    //sets up the labels
    private JComponent getLabelFrame(){
        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
        labelPanel.add(Box.createVerticalStrut(5));
        labelPanel.add(find);
        labelPanel.add(Box.createVerticalStrut(50));
        labelPanel.add(replace);
        labelPanel.add(Box.createVerticalStrut(40));
        labelPanel.add(filters);
        labelPanel.add(Box.createVerticalStrut(10));
        labelPanel.add(dir);
        labelPanel.add(Box.createVerticalStrut(10));
        labelPanel.add(directory);
        return labelPanel;
    }

    //sets up comboboxes
    private JComponent getBoxFrame(){
        int numPrevInputs = Integer.parseInt(prefs.get("FindInputs",null));
        int numDirPrevInputs = Integer.parseInt(prefs.get("PrevDirInput",null));

        for(int i = 1; i < numPrevInputs+1; i ++){
            if(!prefs.get(Integer.toString(i),null).equals("")) {
                //findBox.addItem(prefs.get(Integer.toString(i), null));
            }
        }
        for(int j = 100; j < numDirPrevInputs+1; j ++){
            if(!prefs.get(Integer.toString(j),null).equals("")) {
               //dirBox.addItem(prefs.get(Integer.toString(j), null));

            }
        }
        //prevFindInput = 0;
        //prevDirectoryInput = 100;
        boxPanel.setLayout(new BoxLayout(boxPanel,BoxLayout.Y_AXIS));
        replaceBox.setEditable(true);
        filtersBox.setEditable(true);
        findBox.setEditable(true);
        dirBox.setEditable(true);
        boxPanel.add(findBox);
        boxPanel.add(Box.createVerticalStrut(40));
        boxPanel.add(replaceBox);
        boxPanel.add(Box.createVerticalStrut(30));
        filtersBox.addItem("*.*");
        boxPanel.add(filtersBox);
        boxPanel.add(dirBox);
        boxPanel.add(new JScrollPane(directoryBox));
        if(prefs.getBoolean("Whole Word Search",true)){
            wholeWordSearch.setSelected(true);
        }
        if(prefs.getBoolean("Match Case Search",true)){
            matchWordCase.setSelected(true);
        }
        boxPanel.add(wholeWordSearch);
        boxPanel.add(matchWordCase);
        return boxPanel;
    }

    //sets up buttons
    private JComponent getButtonFrame(){
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.add(findButton);
        buttonPanel.add(findInFilesButton);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(replaceButton);
        buttonPanel.add(replaceAllButton);
        buttonPanel.add(Box.createVerticalStrut(25));
        buttonPanel.add(directoryButton);
        buttonPanel.add(multDirectoryButton);
        return buttonPanel;
    }
    //sets up display
    private JComponent getDisplayFrame(){

        displayPanel.setLayout(new BoxLayout(displayPanel,BoxLayout.Y_AXIS));
        displayPanel.add(scrollPane);
        return displayPanel;
    }
    //sets the button listeners
    public static void setListeners() {

        directoryButton.addActionListener(e -> {
            try {
                hasAppeared = false;
                fileChooser.showOpenDialog(null);
                File f = fileChooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                //if(dirBox.getEditor().getItem() != null){
                    dirBox.setSelectedItem(filename);
                    logger.info(dirBox.getEditor().getItem().toString());
              //  }
                String x = dirBox.getEditor().getItem().toString();
                for (int i = 0; i < dirBox.getItemCount(); i++) {
                    if (dirBox.getItemAt(i).equals(x)) {
                        hasAppeared = true;
                    }
                }
                if (!hasAppeared) {
                    prevDirectoryInput++;
                    dirBox.addItem(x);
                    prefs.putInt("PrevDirInput", prevDirectoryInput);
                    prefs.put(Integer.toString(prevDirectoryInput), x);
                }


                hasAppeared = false;

            }

            catch(NullPointerException g){
                logger.info("No Directory Entered");
            }
        });
        multDirectoryButton.addActionListener(e -> {
            try {
                String filterString = filtersBox.getEditor().getItem().toString();
                for (int i = 0; i < filtersBox.getItemCount(); i++) {
                    if (filtersBox.getItemAt(i).equals(filterString)) {
                        hasAppeared = true;
                    }
                }
                if (!hasAppeared) {
                    filtersBox.addItem(filterString);

                }
                hasAppeared = false;
                if (filtersBox.getItemCount() > 1) {
                    multipleFileChoose.addChoosableFileFilter(new FileNameExtensionFilter(filterString,filterString.substring(2)));
                }
                multipleFileChoose.showOpenDialog(null);
                File f = multipleFileChoose.getSelectedFile();
                String filename = f.getAbsolutePath();
                directoryList.addElement(filename);
                logger.info(filename);
                directoryBox = new JList(directoryList);
                enteredDirectory = filename;
            }

            catch(NullPointerException g){
                logger.info("No Directory Entered");
            }
        });
        //Single search
        findButton.addActionListener(e -> {
            try {
                String singleDirSearch = dirBox.getEditor().getItem().toString();
                String x = findBox.getEditor().getItem().toString();
                for (int i = 0; i < findBox.getItemCount(); i++) {
                    if (findBox.getItemAt(i).equals(x)) {
                        hasAppeared = true;
                    }
                }

                if (!hasAppeared) {
                    prevFindInput++;
                    findBox.addItem(x);
                    prefs.putInt("FindInputs", prevFindInput);
                    prefs.put(Integer.toString(prevFindInput), x);
                }
                hasAppeared = false;
                if (wholeWordBoolean && !matchWordBoolean) {
                        DoWork swingWorker = new DoWork();
                        swingWorker.setValues(singleDirSearch, x, 1, 0, null);
                        swingWorker.execute();
                    } else if (!wholeWordBoolean && !matchWordBoolean) {
                        DoWork swingWorker = new DoWork();
                        swingWorker.setValues(singleDirSearch, x, 0, 0, null);
                        swingWorker.execute();
                    } else if (matchWordBoolean) {
                        DoWork swingWorker = new DoWork();
                        swingWorker.setValues(singleDirSearch, x, 2, 0, null);
                        swingWorker.execute();
                    } else if (wholeWordBoolean && matchWordBoolean) {
                        DoWork swingWorker = new DoWork();
                        swingWorker.setValues(singleDirSearch, x, 2, 0, null);
                        swingWorker.execute();
                    }
                logger.info(singleDirSearch);

            }

            catch (NullPointerException g){
                logger.info("No input");
            }
        });
        //Multiple file search
        findInFilesButton.addActionListener(e -> {
            try {
                String findString = findBox.getEditor().getItem().toString();

                for(int i = 0; i < findBox.getItemCount(); i++) {
                    if (findBox.getItemAt(i).equals(findString)) {
                        hasAppeared = true;
                    }
                }
                if(!hasAppeared){
                    findBox.addItem(findString);
                    //prevFindInput++;
                    //prefs.putInt("FindInputs",prevFindInput);
                    //prefs.put(Integer.toString(prevFindInput),findString);
                }
                hasAppeared = false;
                if (directoryList.getSize() > 1){
                    if(displayResults.getAccessibleContext() != null){
                        displayResults.setText("");
                    }
                    for(int i = 0; i < directoryList.getSize(); i ++){
                        if(wholeWordBoolean && !matchWordBoolean) {
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(directoryList.get(i).toString(),findString,1,1,null);
                            swingWorker.execute();
                        }
                        else if(!wholeWordBoolean){
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(directoryList.get(i).toString(),findString,0,1,null);
                            swingWorker.execute();
                        }
                        else if(matchWordBoolean){
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(directoryList.get(i).toString(),findString,2,1,null);
                            swingWorker.execute();
                        }
                        else if(wholeWordBoolean && matchWordBoolean){
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(directoryList.get(i).toString(),findString,2,1,null);
                            swingWorker.execute();
                        }
                        logger.info(directoryList.toString());
                    }
                }
                else{
                    displayResults.setText("ERROR: Please select 2 or more files");
               }
                hasAppeared = false;
            }
            catch(NullPointerException f){
                logger.info("No input");
            }
        });
        //Single file replacement
        replaceButton.addActionListener(e -> {
            // System.out.println(prefs.getInt("ReplaceInputs",prevInput));
            try {
                String replaceString = replaceBox.getEditor().getItem().toString();
                String findString = findBox.getEditor().getItem().toString();
                String dirString = dirBox.getEditor().getItem().toString();
                for (int i = 0; i < replaceBox.getItemCount(); i++) {
                    if (replaceBox.getItemAt(i).equals(replaceString)) {
                        hasAppeared = true;
                    }
                }
                if (!hasAppeared) {
                    replaceBox.addItem(replaceString);

                }

                hasAppeared = false;

                if (replaceBox.getItemCount() > 0) {
                    String[] options = {"YES", "NO"};
                    int temp = JOptionPane.showOptionDialog(null, "Are you sure you want to replace: " + findString + "\n" + "with: " + replaceString, "Confirmation Dialogue", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (temp == 0 && replaceString != null && dirString != null && findString != null) {
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(dirString, findString, 0, 1, replaceString);
                            swingWorker.execute();

                    }

                }
            }
            catch(NullPointerException f){
                logger.info("No Input");
            }

        });
        //multiple file replacement
        replaceAllButton.addActionListener(e -> {
           // System.out.println(prefs.getInt("ReplaceInputs",prevInput));
            try {
                String replaceString = replaceBox.getEditor().getItem().toString();
                String findString = findBox.getEditor().getItem().toString();
                System.out.println(replaceString);
                for (int i = 0; i < replaceBox.getItemCount(); i++) {
                    if (replaceBox.getItemAt(i).equals(replaceString)) {
                        //System.out.println("item duplicated");
                        hasAppeared = true;
                    }
                }
                if (!hasAppeared) {
                    replaceBox.addItem(replaceString);
                    //prevInput++;
                    //prefs.putInt("ReplaceInputs",prevInput);
                    //prevInput++;
                }

                hasAppeared = false;

                if (replaceBox.getItemCount() > 0) {
                    String[] options = {"YES", "NO"};
                    int temp = JOptionPane.showOptionDialog(null, "Are you sure you want to replace: " + findString + "\n" + "with: " + replaceString, "Confirmation Dialogue", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (temp == 0 && replaceString != null && directoryList.getSize() > 0 && findString != null) {
                        for (int j = 0; j < directoryList.getSize(); j++) {
                            DoWork swingWorker = new DoWork();
                            swingWorker.setValues(directoryList.get(j).toString(), findString, 0, 1, replaceString);
                            swingWorker.execute();
                            //replaceFile.initReplace(directoryList.get(j).toString(), findString, replaceString);
                        }
                    }
                    //handleKeyboardListeners();

                }
            }
            catch(NullPointerException f){
                logger.info("No input");
            }

        });
        wholeWordSearch.addActionListener(e -> {
            if(wholeWordBoolean || !wholeWordSearch.isSelected()){
                prefs.putBoolean("Whole Word Search",false);
                wholeWordBoolean = false;
            }
            else{
                wholeWordBoolean = true;
                prefs.putBoolean("Whole Word Search",true);
            }
        });
        matchWordCase.addActionListener(e -> {
            if(matchWordBoolean || !matchWordCase.isSelected()){
                matchWordBoolean = false;
                prefs.putBoolean("Match Case Search",false);
            }
            else{
                matchWordBoolean = true;
                prefs.putBoolean("Match Case Search",true);
            }
        });
    }
    //sets the bottom text area
    public static void setDisplay(String toDisplay){
        displayResults.setText(toDisplay);
    }
    //appends the bottom text area
    public static void appendDisplay(String toDisplay){
        displayResults.append(toDisplay);
    }

}
