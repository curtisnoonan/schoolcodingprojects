package rcrypt;


//FT
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Headless {
    //GLOBAL VARS
    static String TYPE;
    static int PORT;
    static String ADDRESS;
    static String STORAGE_PATH;


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
                //Create User
                    User root = new User("root");
                    root.addKey("personalKey");
                    root.addKey("businessKey");
                    root.addKey("specialKey");
                        //Authentication.createUser("root", "password");
                
                //Creating a Pool
                    root.addPool("Personal","personalKey");
                    root.addPool("Business", "businessKey");
                
                //Adding Servers to Pool
                    //Personal Pool
                    ServerNode mbhome = new ServerNode("MBHOME", "mbhomeserver.ddns.net", 5000 );
                    ServerNode local = new ServerNode("LOCAL", "127.0.0.1", 5000 );
                    //root.addMemberToPool(mbhome, "Personal");
                    root.addMemberToPool(local, "Personal");
                    
                    //Business Pool
                    ServerNode vps1 = new ServerNode("VPS1", "64.52.84.171", 5001); //PASSWORD: vps1password
                    ServerNode vps2 = new ServerNode("VPS2", "64.52.84.90", 5002); //PASSWORD: vps2password
                    ServerNode vps3 = new ServerNode("VPS3", "64.52.84.146", 5003); //PASSWORD: vps3password
                    root.addMemberToPool(vps1, "Business");
                    root.addMemberToPool(vps2, "Business");
                    root.addMemberToPool(vps3, "Business");
                
                
                
                //Adding Files to Pools
                    //Personal Pool
                        ArrayList<FileObject> personalFiles = new ArrayList<>();
                        personalFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile.txt"));
                        personalFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile2.pl"));
                        root.addFilesToPool(personalFiles,"Personal");

                        
                        //root.addFileToPool(f1, "Personal");
                        //root.addFileToPool(f2, "specialKey", "Personal");
                
                    //Business Pool
                        ArrayList<FileObject> buisnessFiles = new ArrayList<>();
                        //buisnessFiles.add(new FileObject("C:\\Users\\trish\\Downloads\\ubuntu-16.04.4-server-amd64.iso"));
                        buisnessFiles.add(new FileObject("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile4.txt"));
                        //root.addFilesToPool(buisnessFiles,"Business");
                        
                        //root.addFileToPool(f3, "Business");
                        //root.addFileToPool(f4, "Business");
                    ArrayList<FileObject> retrivePersonalFiles = new ArrayList<>();
                        retrivePersonalFiles.add(root.getPool("Personal").getFile("C:\\Users\\trish\\Project Workspace\\GitHub\\COMP380_Project\\TESTFILES\\testFile.txt"));
                        root.getFilesFromPool(retrivePersonalFiles, "Personal");
                        
                
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
}
