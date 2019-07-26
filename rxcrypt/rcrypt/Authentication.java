package rcrypt;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;


public class Authentication {
    public static User login(String username, SecretKey key){
        User usr = null;
        String userFile = getUserFile(username);
        if (checkUserExists(userFile)){
            usr = Encryption.loadUser(key, new File(userFile));
            if (usr != null){
                System.out.println("User " + usr.getName() + " loaded successfully!");
                return usr;
            }
            else{
                System.out.println("User login: " + username + " failed!");
                return null;
            }
        }
        return null;
    }
    public static SecretKey getUserKey(String password){
        return Encryption.genKeyFromPass(password.toCharArray());
    }
    
    public static void createUser(String username, SecretKey key){
        String userFile = getUserFile(username);
        if (!checkUserExists(userFile)){
            User usr = new User(username);
            Encryption.saveUser(key, usr, new File(userFile));
            return;
        }
        System.out.println("User " + username + " already exists!");   
    }
    
    public static void saveUser(User usr, SecretKey key){
        String userFile = getUserFile(usr.getName());
        if (checkUserExists(userFile)){
            Encryption.saveUser(key, usr, new File(userFile));
            return;
        }
        System.out.println("User " + usr.getName() + " does not exist!"); 
    }
    
    public static void removeUser(String username, String password){
        String userFile = getUserFile(username);
        if (!checkUserExists(userFile)){
            
        }
    }
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
    
    public static String getUserFile(String username){
        String userFile = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] stuff = md.digest(username.getBytes());
            userFile = RCrypt.STORAGE_PATH + "DB" + File.separator + toHexString(stuff) + ".dat";
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userFile;
    }
    public static Boolean checkUserExists(String userFile){
        File file = new File(userFile);
        return file.exists();
    }

    
}//Authentication Class
