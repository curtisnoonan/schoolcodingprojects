/**
 * Author: Curtis Noonan
 * Oct 9, 2018
 * COMP 585 GUI
 * ReplaceFile.java
 * The purpose of this class is to replace
 * a string in a file.
 *
 */
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReplaceFile {

    public ReplaceFile() {
    }

    public static void initReplace(String fileName,String toFind, String toReplace) {
        Path path = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        try {

            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll(toFind, toReplace);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            System.out.println("FILE DOES NOT EXIST");
        }

    }
}
