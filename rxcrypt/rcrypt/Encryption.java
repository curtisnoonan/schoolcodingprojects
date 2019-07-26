package rcrypt;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.*;

public class Encryption {
	public static final byte[] SALT = {(byte)0x83, (byte)0x13, (byte)0x83, (byte)0x83, (byte)0xf2, (byte)0x13, (byte)0x14, (byte)0x69};
	static void fileOp(int cipherMode, Key key, File target, File dest) throws InvalidKeyException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {

            Cipher c = null;
            try {
                c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }

            c.init(cipherMode, key);
            FileOutputStream fos;
            try (FileInputStream fis = new FileInputStream(target)) {
                byte[] targetBytes = new byte[(int) target.length()];
                fis.read(targetBytes);
                byte[] outBytes = c.doFinal(targetBytes);
                fos = new FileOutputStream(dest);
                fos.write(outBytes);
            }
		fos.close();
		
		
	}
        public static void encrypt(Key key, FileObject file) {
            
            try {
                File input = new File(file.getAbsolutePath());
                File output = new File(RCrypt.STORAGE_PATH + "TEMP" + File.separator + file.getEncodedName());
                Encryption.fileOp(Cipher.ENCRYPT_MODE, key, input, output);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        public static void decrypt(Key key, FileObject file) throws FileNotFoundException{
            try {
                File input = new File(RCrypt.STORAGE_PATH + "TEMP" + File.separator + file.getEncodedName());
                File output = new File(RCrypt.STORAGE_PATH + "DOWNLOADS" + File.separator + file.getName());               
                Encryption.fileOp(Cipher.DECRYPT_MODE, key, input, output);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //User Management//
        
        public static void saveUser(SecretKey key, User usr, File output){
            try {
                ObjectOutputStream encryptedObject = null;
                Cipher c = null;
                try {
                    c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                    System.out.println(ex);
                    //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    c.init(Cipher.ENCRYPT_MODE, key);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                SealedObject usrObj = null;
                try {
                    usrObj = new SealedObject(usr, c);
                } catch (IOException | IllegalBlockSizeException ex) {
                    System.out.println(ex);
                    //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                CipherOutputStream cipherOutputStream = null;
                try {
                    cipherOutputStream = new CipherOutputStream( new BufferedOutputStream( new FileOutputStream( output ) ), c );
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                    //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    encryptedObject = new ObjectOutputStream( cipherOutputStream );
                } catch (IOException ex) {
                    System.out.println(ex);
                    //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    encryptedObject.writeObject(usrObj);
                    encryptedObject.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                    //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                }
                cipherOutputStream.close();
            }//save user
            catch (    IOException ex) {
                Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null,ex);
            }
        }
        public static User loadUser(SecretKey key, File input){
            User usr = null;
            Cipher c = null;
            try {
                c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                c.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            CipherInputStream cipherInputStream = null;
            try {
                cipherInputStream = new CipherInputStream(new BufferedInputStream( new FileInputStream(input) ), c );
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(cipherInputStream);
            } catch (IOException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            SealedObject userObj = null;
            try {
                userObj = (SealedObject)inputStream.readObject();
                inputStream.close();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                usr = (User)userObj.getObject(c);
            } catch (IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            return usr;
        }//load user
        
        //Utilities//
        
        public static SecretKey genKeyFromPass(char[] password){
            SecretKey key = null;
            try {
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(password, SALT, 65564, 128);
                SecretKey tmp = factory.generateSecret(spec);
                key = new SecretKeySpec(tmp.getEncoded(), "AES");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                System.out.println(ex);
                //Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            return key;
        }
}