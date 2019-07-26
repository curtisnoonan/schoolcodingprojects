package rcrypt;

import java.net.*;
import java.io.*;
import java.util.Arrays;


public class Client extends Thread {
    private Thread t;
    private int BUFFER_SIZE = 100;
    private Socket SOCKET;
    private String SERVERADDRESS;
    private String SERVERNAME;
    private int PORT;
    private Event EVENT;

    public Client(ServerNode n, Event e){
        SERVERNAME = n.getName();
        SERVERADDRESS = n.getAddress();
        PORT = n.getPort();
        EVENT = e;
        
    }
    
    @Override
    public void start () {
      if (t == null) {
         t = new Thread (this);
         t.start ();
      }
      
   }
    
    @Override
    public void run() {
        InputStream is;
        OutputStream os;
        ObjectOutputStream oos;
        ObjectInputStream ois;
    
        try {
            System.out.println("Connecting to " + SERVERADDRESS + " on port " + PORT);
            SOCKET = new Socket(SERVERADDRESS, PORT);
            
            System.out.println("Now connected to " + SOCKET.getRemoteSocketAddress());
            
   
            os = SOCKET.getOutputStream();
            is = SOCKET.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);


            //Event Handling                
            switch (EVENT.getEventType()){
                case FileUpload:
                    sendEvent(oos, EVENT);
                    sendFile(oos, EVENT);
                    break;
                case FileRequest:
                    sendEvent(oos, EVENT);
                    recieveFile(ois, EVENT);
                    break;
                case DeleteFiles:
                    sendEvent(oos, EVENT);
                    break;
                case EchoEvent:
                    //Send Event
                    sendEvent(oos, EVENT);
                    break;
            }
            
            //Recieve Event
            EVENT = recieveEvent(ois);
            
            
            //Operations Complete, Close Socket
            SOCKET.close();

        } catch (Exception ex) {
           ex.toString();
           EVENT.appendToLog(ex.toString());
        }
        //System.out.println(SERVERNAME + ":\n" + EVENT.getLog()+"\n");
    }
    
    
    private void sendEvent(ObjectOutputStream oos, Event e) throws IOException{
        //Send Event
            e.appendToLog("Sending event to Server @ " + SOCKET.getRemoteSocketAddress());
            oos.writeObject(e);
    }
    
    private Event recieveEvent(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        Event e;
        e = (Event) ois.readObject();
        e.appendToLog("Recieved event from Server @ " + SOCKET.getRemoteSocketAddress());
        return e;
    }
    
    private void sendFile(ObjectOutputStream oos, Event e) throws FileNotFoundException, IOException{
        
        for(FileObject f: e.getAssociatedFiles()){
            //Send f
            //File file = new File(f.getParentPath()+ f.getEncodedName());
            File file = new File(RCrypt.STORAGE_PATH + "TEMP" + File.separator + f.getEncodedName());
            FileInputStream fis = new FileInputStream(file);
            byte [] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            
            while((bytesRead = fis.read(buffer)) > 0){
                oos.writeObject(bytesRead);
                oos.writeObject(Arrays.copyOf(buffer, buffer.length));
            }            
            file.delete();
            RCrypt.CURRENT_USER.incrementProgress();
        }
        
    }
    
    private void recieveFile(ObjectInputStream ois, Event e)throws IOException, Exception{
        for(FileObject f : e.getAssociatedFiles()){
                //Recieve f
                FileOutputStream fos = new FileOutputStream(RCrypt.STORAGE_PATH + "TEMP" + File.separator + f.getEncodedName());
                byte [] buffer = new byte[BUFFER_SIZE];
                int bytesRead = 0;
                Object o;
                do{
                    o = ois.readObject();
                    
                    if (!(o instanceof Integer)) {
                    throw new Exception("Something is wrong");
                    }
                    
                    bytesRead = (Integer)o;
                    
                    o = ois.readObject();
                    
                    if (!(o instanceof byte[])) {
                        throw new Exception("Something is wrong");
                    }
                    
                    buffer = (byte[])o;
                    
                    //Write data to output file
                    fos.write(buffer, 0, bytesRead);
                }while(bytesRead == BUFFER_SIZE);
                
                System.out.println("File Transfer success");
                e.appendToLog("Recieved file from Server @ " + SOCKET.getRemoteSocketAddress());
                
                fos.close();
                RCrypt.CURRENT_USER.incrementProgress();
            }
    }
    
    public String getRemoteNodeName(){
        return SERVERNAME;
    }
    public Event getEvent(){
        return EVENT;
    }
}