package rcrypt;


//UI
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//FT
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javax.crypto.SecretKey;

public class RCrypt extends Application {
    //GLOBAL VARS
    static Scene SC;
    static String TYPE;
    static int PORT;
    static String ADDRESS;
    static String STORAGE_PATH;
    static User CURRENT_USER = null;
    static SecretKey USER_KEY;
  
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("openingPage.fxml"));
        primaryStage.setTitle("RCrypt: Encrypted File Storage Network");
        SC = new Scene(root, 800, 600);
        primaryStage.setScene(SC);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Stage is closing");
              if(RCrypt.CURRENT_USER == null || RCrypt.USER_KEY == null){
                  //Does not Save User because there is no login or account created//
                  System.out.println("Did not save.");
                  Platform.exit();
              }
              else {
                 //save user and exit//
                 System.out.println("User saved.");
                 Authentication.saveUser(RCrypt.CURRENT_USER, RCrypt.USER_KEY);
                 RCrypt.CURRENT_USER = null;
                 RCrypt.USER_KEY = null;
                 Platform.exit();
              }
          }
          
        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        
        
        //Load in Config File
        if(args[0]!=null)
        {
            getConfig(args[0]);
            
            if("SERVER".equals(TYPE)){
                Server s = new Server(PORT, STORAGE_PATH);
            }
            else {
               
                
                new File(STORAGE_PATH + "RCrypt" + File.separator + "TEMP").mkdirs();
                new File(STORAGE_PATH + "RCrypt" + File.separator + "DOWNLOADS").mkdirs();
                new File(STORAGE_PATH + "RCrypt" + File.separator + "DB").mkdirs();
                STORAGE_PATH += "RCrypt" + File.separator;
                
                //LAUNCH GUI
                launch();
                
                //SAVE USER
                
                
                //Create User
                    //CURRENT_USER = new User("root");
                    //CURRENT_USER.addKey("personalKey");
                    //CURRENT_USER.addKey("businessKey");
                    //CURRENT_USER.addKey("specialKey");
                        //Authentication.createUser("root", "password");
                        //CURRENT_USER = Authentication.login("root", "password");
                        //System.out.println(CURRENT_USER.getName());
                     
                

                        
                 /*       
                
                //Creating a Pool
                    CURRENT_USER.addPool("Personal","personalKey");
                    CURRENT_USER.addPool("Business", "businessKey");
                
                //Adding Servers to Pool
                    //Personal Pool
                    ServerNode mbhome = new ServerNode("MBHOME", "mbhomeserver.ddns.net", 5000 );
                    //ServerNode local = new ServerNode("LOCAL", "127.0.0.1", 5000 );
                    CURRENT_USER.addMemberToPool(mbhome, "Personal");
                    //root.addMemberToPool(local, "Personal");
                    
                    //Business Pool
                    ServerNode vps1 = new ServerNode("VPS1", "64.52.84.171", 5001); //PASSWORD: vps1password
                    ServerNode vps2 = new ServerNode("VPS2", "64.52.84.90", 5002); //PASSWORD: vps2password
                    ServerNode vps3 = new ServerNode("VPS3", "64.52.84.146", 5003); //PASSWORD: vps3password
                    CURRENT_USER.addMemberToPool(vps1, "Business");
                    CURRENT_USER.addMemberToPool(vps2, "Business");
                    CURRENT_USER.addMemberToPool(vps3, "Business");
                
                
                
                //Adding Files to Pools
                    //Personal Pool
                        ArrayList<FileObject> personalFiles = new ArrayList<>();
                        personalFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile.txt"));
                        personalFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile2.pl"));
                        CURRENT_USER.addFilesToPool(personalFiles,"Personal");

                        
                        //root.addFileToPool(f1, "Personal");
                        //root.addFileToPool(f2, "specialKey", "Personal");
                
                    //Business Pool
                        ArrayList<FileObject> buisnessFiles = new ArrayList<>();
                        //buisnessFiles.add(new FileObject("C:\\Users\\trish\\Downloads\\ubuntu-16.04.4-server-amd64.iso"));
                        buisnessFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile4.txt"));
                        CURRENT_USER.addFilesToPool(buisnessFiles,"Business");
                        
                        //root.addFileToPool(f3, "Business");
                        //root.addFileToPool(f4, "Business");
                    ArrayList<FileObject> retrivePersonalFiles = new ArrayList<>();
                        retrivePersonalFiles.add(CURRENT_USER.getPool("Personal").getFile("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile.txt"));
                        CURRENT_USER.getFilesFromPool(retrivePersonalFiles, "Personal");
                        
                */
//                //Backup Testing - MUHAMMAD
//                FileObject file1 = new FileObject("C:\\Users\\Ali_h\\Documents\\222.Project2");
//                FileObject file2 = new FileObject("C:\\Users\\Ali_h\\Documents\\222Project3");
//                //FileObject file3 = new FileObject("TestFile.txt");
//
//
//                String conflict1 = "Server 1";
//                String conflict2 = "Server 2";
//
//                String backupName = "Personal";
//                LinkedList<FileObject> file = new LinkedList<FileObject>();
//                ArrayList<String> conflict = new ArrayList<String>();
//                file.add(file1);
//                //file.add(file2);
//                conflict.add(conflict1);
//                conflict.add(conflict2);
//                int frequency = 0;
//
//                Calendar now = Calendar.getInstance();
//                now.clear(Calendar.MILLISECOND);
//                now.clear(Calendar.SECOND);
//                now.set(Calendar.MINUTE, 8);
//
//                //System.out.println(now.getTime());
//
//                Backup test = new Backup(file, conflict, backupName, frequency, now);
//                //test.start();
//                //System.out.println("Files from Backup class:");
//                test.addFileToBackUp(file2);
//                test.printFiles();
//                test.cancelTimer();


                
               

            }
        }
        
}
    
    private static void getConfig(String absolutePath){
        if(absolutePath.endsWith(".config")){
            // This will reference one line at a time
            String line = null;

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader = 
                    new FileReader(absolutePath);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = 
                    new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null) {
                    TYPE = line.substring(14).toUpperCase();
                    if("SERVER".equals(TYPE))
                    {
                        line = bufferedReader.readLine();
                        PORT = Integer.parseInt(line.substring(5));
                        line = bufferedReader.readLine();
                        STORAGE_PATH = line.substring(13);
                    }
                    else if("CLIENT".equals(TYPE))
                    {
                        line = bufferedReader.readLine();
                        STORAGE_PATH= line.substring(13);
//                        ADDRESS = line.substring(8);
//                        line = bufferedReader.readLine();
//                        PORT = Integer.parseInt(line.substring(5));
//                        System.out.println(PORT);
                    }
                    else{
                        System.out.println("Configuration File Corrupt");
                        break;
                    }
                }   

                // Always close files.
                bufferedReader.close();         
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open config file: '" + 
                    absolutePath + "'");                
            }
            catch(IOException ex) {
                System.out.println(
                    "Error reading config file: '" 
                    + absolutePath + "'");
            }
        }
        else
            System.out.println("Unable to open config file: '" + absolutePath + "'");    
    }
    //public void closeProgram(){
        //Do program exit operations here
     //  System.out.println("Program closed sucessfully");
      // Platform.exit();
   // }
}
