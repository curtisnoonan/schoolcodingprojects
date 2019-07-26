/**
 * Author: Curtis Noonan
 * Oct 9, 2018
 * COMP 585 GUI
 * FindFile.java
 * The purpose of this class is to create find
 * a string in a file.
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindFile  {


    public FindFile() {
    }
    //File search
    public StringBuilder initFileSearch(String fileName, String findString,int specialSearch) {
        StringBuilder displayArea = new StringBuilder();

        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);

            //now read the file line by line...
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if (line.toLowerCase().contains(findString.toLowerCase()) && specialSearch == 0) {
                    displayArea.append("Found on line: " + lineNum + " of " + fileName + ": "+ line +"\n");
                }
                else if (specialSearch == 1) {
                    //boolean hasTerm = false;
                    for (String word : line.split("\\s+")){
                        String tempLine = word.toLowerCase();
                        if (tempLine.equals(findString)) {
                            displayArea.append("Found on line: " + lineNum + " of " + fileName + ": " + line + "\n");
                            break;
                        }
                    }
                }
                else if (specialSearch == 2) {
                    for (String word : line.split("\\s+")){
                        if (line.equals(findString)) {
                            displayArea.append("Found on line: " + lineNum + " of " + fileName + ": " + line + "\n");
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {

            return displayArea.append("Error: Invalid File Name");
        }
        return  displayArea;
    }

}
