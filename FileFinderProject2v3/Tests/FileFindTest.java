/**
 * Author: Curtis Noonan
 * September 18, 2018
 * COMP 585 GUI
 * FileFindTest.java
 * The purpose of this class is to test the
 * methods FindFile and ReplaceFile.
 */

 import org.junit.Test;

 import static org.junit.Assert.*;

 public class FileFindTest {

@Test
    public void initFileSearch() {
        FindFile findFile = new FindFile();
        StringBuilder test = new StringBuilder();
        test.append("Found on line: 1 of /Users/curtisnoonan/Desktop/big copy.txt: WORKS123S");
        assertNotSame(test,findFile.initFileSearch("/Users/curtisnoonan/Desktop/big copy.txt","WORKS123S",0));

        }

}