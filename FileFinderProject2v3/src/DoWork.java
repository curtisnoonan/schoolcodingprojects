/**
 * Author: Curtis Noonan
 * Oct 9, 2018
 * COMP 585 GUI
 * DoWork.java
 * The purpose of this class is to create a
 * swingworker and find or replace a string.
 *
 */
import javax.swing.SwingWorker;
import java.util.List;

public class DoWork extends SwingWorker<Boolean,Integer> {
     String enteredDirectory;
     String findWord;
     int specialParam;
     String displayArea;
     int multipleFiles;
     String replaceWord;


    FindFile k = new FindFile();
    ReplaceFile r = new ReplaceFile();

    public DoWork() {
        super();
    }

    @Override
    protected void process(List chunks) {
        super.process(chunks);
    }

    @Override
    protected void done() {
        if(displayArea.equals("")){
            FileFinderFrame.setDisplay(findWord + " Not Found");
        }
        if(multipleFiles == 0) {

                FileFinderFrame.setDisplay(displayArea);
                if(displayArea.equals("")){
                    FileFinderFrame.setDisplay(findWord + " Not Found");
                }
        }
        if(multipleFiles == 1){
            System.out.println(enteredDirectory);
            FileFinderFrame.appendDisplay(displayArea);
        }
        if(replaceWord != null){
            FileFinderFrame.setDisplay("Success, Replaced: " + replaceWord + " with " + findWord);
        }
        super.done();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        if(replaceWord != null){
            r.initReplace(enteredDirectory,findWord,replaceWord);
        }
        else{
            displayArea = k.initFileSearch(enteredDirectory, findWord, specialParam).toString();
        }
        return true;
    }

    public void setValues(String enteredD, String findW, int specialP, int multipleF, String replaceW){
        enteredDirectory = enteredD;
        findWord = findW;
        specialParam = specialP;
        multipleFiles = multipleF;
        replaceWord = replaceW;
    }
}
