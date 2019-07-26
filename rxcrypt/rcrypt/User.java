package rcrypt;


import java.io.FileNotFoundException;
import java.io.File;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.crypto.KeyGenerator;


public final class User implements java.io.Serializable {
    private String NAME = "";
    private HashMap<String, Key> KEYS = new HashMap<>();
    private HashMap<String, Pool> POOLS = new HashMap<>();
    private ArrayList<Event> EVENTS = new ArrayList<>();
    private ArrayList<Backup> BACKUPS = new ArrayList<>();
    public int numTasks, tasksRemaining;

    //CONSTRUCTOR
    public User(String name){
        NAME = name;
        addKey("DEFAULT");
        POOLS.put("ALL", new Pool("ALL","DEFAULT"));
    }
    public void setName(String s){
        NAME = s;
    }
    public String getName(){
        return NAME;
    }
    public void addBackup(Backup a){
        BACKUPS.add(a);
    }
    public Backup getBackup(String name){
        Backup found = null;
        for(Backup b: BACKUPS){
            if(b.getBackUpName().equals(name)){
               found = b; 
            }
        }
        return found;
    }
    public ArrayList<Event> getEvents(){
        return EVENTS;
    }
    public Backup getBackup(int i){
        return BACKUPS.get(i);
    }
    public ArrayList<Backup> getBackup(){
        return BACKUPS;
    }
    public void removeBackup(int i){
        BACKUPS.remove(i);
    }
    public void addKey(String keyname) {
        if(KEYS.containsKey(keyname)){
            return;
        }
        try{
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128); 
            KEYS.put(keyname, keygen.generateKey());
        }
        catch(NoSuchAlgorithmException ex){
            System.out.println(ex);
            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeKey(User usr, String keyname){
        KEYS.remove(keyname);       
    }
    public String[] getKeySet(){
        return KEYS.keySet().toArray(new String[0]);
    }
    public Key getKey(String keyName){
        return KEYS.get(keyName);
    }
    public void addPool(String name, String keyName){
        POOLS.put(name, new Pool(name, keyName));
    }
    public Pool getPool(String name){
        return POOLS.get(name);
    }
    public ArrayList <Pool> getPools(){
        ArrayList<Pool> pools = new ArrayList<>();
        for(String currentKey : POOLS.keySet()){
            pools.add(POOLS.get(currentKey));
            
        }
        return pools;
    }
    public Pool findMemberInPools(String serverName){
        Pool s = null;
        for(Pool p : getPools()){
            
            if(p.getMember(serverName)!= null && (!p.getName().equals("ALL"))){
                s = p;
                break;
            }
        }
        return s;
    }
    public void addMemberToPool(ServerNode n, String pool){
        POOLS.get(pool).addMember(n);
        POOLS.get("ALL").addMember(n);
        //for(Entry<String, FileObject> f : POOLS.get(pool).getFileManifest())
        
    }
    public void removeMemberFromPools(String serverName){
        for(Pool p : getPools()){
            p.removeMember(serverName);
        }
    }
   /* 
    public void addFileToPool(FileObject f, String poolName){
        //Add file to pool
        Pool p;
        p = POOLS.get(poolName);
        f.setServers(p.getMembers());
        p.addFile(f);
        
        //Encrypt File
        Encryption.encrypt(this.KEYS.get(f.getKeyName()), f);
        
        Event e = new Event(EventType.FileUpload);
        e.addAssociatedFile(f);
    }
    */
    public void addFilesToPool(ArrayList<FileObject> files, String poolName){
        //Init Progress
        numTasks = files.size() * 2; //Encryption + Sending File
        tasksRemaining = numTasks;
        
        Event e = new Event(EventType.FileUpload);  //Create Event
        ArrayList <Client> clients = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        //Add files to pool, encrypt, encodename, and send
        Pool p;
        p = POOLS.get(poolName);
        for(FileObject f: files ){
            f.setServers(p.getMembers());   //Set servers
            p.addFile(f);   //Add to pool
            //System.out.println(f.getParentPath()+ f.getEncodedName());
            Encryption.encrypt(this.KEYS.get(f.getKeyName()), f);   //Encrypt File
            e.appendToLog("File Encrypted: " + f.getName());
            e.addAssociatedFile(f);
            incrementProgress();
        }
        
        //Spawn multiple threads, one per Event
        for(ServerNode s: p.getMembers()){
            Client c = (new Client(s,e));
            clients.add(c);
            executorService.execute(c);
        }
               
        
        executorService.shutdown();
        try {
            executorService.awaitTermination(12, TimeUnit.HOURS);
            System.out.println();
            for(FileObject f: e.getAssociatedFiles()){
                File file = new File(RCrypt.STORAGE_PATH + "TEMP" + File.separator + f.getEncodedName());
                file.delete();
            }
            for(Client c: clients){
                Event ev = c.getEvent();
                System.out.println(c.getRemoteNodeName() + ":\n" + ev.getLog()+"\n");
                EVENTS.add(e);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
        
    }
    
    public void getFilesFromPool(ArrayList<FileObject> files, String poolName){
        //Init Progress
        numTasks = files.size() * 2; //Decryption + Sending File
        tasksRemaining = numTasks;
        
        Event e = new Event(EventType.FileRequest);  //Create Event
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        //Add files to pool, encrypt, encodename, and send
        Pool p;
        p = POOLS.get(poolName);
        
        for(FileObject f: files ){
            e.addAssociatedFile(p.getFile(f.getAbsolutePath()));
            //e.addAssociatedFile(POOLS.get(p).getFile(f.getEncodedName()));
        }
        
        
        Client client = new Client(p.getMembers().get(0), e);   //get optimal server
        executorService.execute(client);
        
        executorService.shutdown();
        try {
                executorService.awaitTermination(12, TimeUnit.HOURS);
                
                System.out.println();
                Event ev = client.getEvent();
                
                for(FileObject f: ev.getAssociatedFiles()){
                    
                    try{
                    Encryption.decrypt(KEYS.get(f.getKeyName()), f);
                    }catch(FileNotFoundException ex){
                        System.out.println(ex);
                    }
                    ev.appendToLog("File Decrypted: " + f.getName());
                    incrementProgress();
                }
                
                
                System.out.println(client.getRemoteNodeName() + ":\n" + ev.getLog()+"\n");
                EVENTS.add(e);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
    }
    
    public void removeFilesFromPool(ArrayList<FileObject> files, String poolName){
        Event e = new Event(EventType.DeleteFiles);  //Create Event
        ArrayList <Client> clients = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        //Add files to pool, encrypt, encodename, and send
        Pool p;
        p = POOLS.get(poolName);
        for(FileObject f: files ){
            e.addAssociatedFile(p.getFile(f.getAbsolutePath()));
        }
        
        //Spawn multiple threads, one per Event
        for(ServerNode s: p.getMembers()){
            Client c = (new Client(s,e));
            clients.add(c);
            executorService.execute(c);
        }
        
        executorService.shutdown();
        try {
            executorService.awaitTermination(12, TimeUnit.HOURS);
            
            for(FileObject f: e.getAssociatedFiles()){
                p.removeFile(f);
            }
            
            System.out.println();
            for(Client c: clients){
                Event ev = c.getEvent();
                System.out.println(c.getRemoteNodeName() + ":\n" + ev.getLog()+"\n");
                EVENTS.add(e);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public void addFileToPool(FileObject f, String keyName, String poolName){
        //Add file to pool
        POOLS.get(poolName).addFile(f, keyName);
        //Encrypt File
        Encryption.encrypt(this.KEYS.get(f.getKeyName()), f);
    }
    
    public void incrementProgress(){
        tasksRemaining --;
        //fileBrowserController.setProgressBar((tasksRemaining/numTasks));
    }
}
