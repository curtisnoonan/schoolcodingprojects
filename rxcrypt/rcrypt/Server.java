package rcrypt;


import java.net.*;
import java.io.*;
import java.util.Arrays;


public class Server extends Thread {
    public static String STORAGEPATH;
    //Constructor
    public Server(int PORT, String STORAGEPATH){
        this.STORAGEPATH = STORAGEPATH;
        try{
            //Listen for incomming connections
            try (ServerSocket listener = new ServerSocket(PORT)) {
                System.out.println("Server Started:\nListening on port " + PORT + ":");
                while(true){
                    new Handler(listener.accept()).start();
                }
            }//listener is closed
        
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

//Created upon client connection
   private static class Handler extends Thread{
       
        int BUFFER_SIZE = 100;
        Socket socket;
        

        //Constructor
        public Handler(Socket socket){
            this.socket = socket;
            System.out.println("Connected to: " + socket.getRemoteSocketAddress());
        }

        
        public void run() {
        InputStream is;
        OutputStream os;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        Event e_request = null;
        
            try{
                //Create Input & Output Stream
                os = socket.getOutputStream();
                is = socket.getInputStream();
                ois = new ObjectInputStream(is);
                oos = new ObjectOutputStream(os);



                //Event Handling
                
                    //Recieve Event
                    e_request = recieveEvent(ois);
                    
                    switch (e_request.getEventType()){
                        case FileUpload:
                            recieveFile(ois, e_request);
                            sendEvent(oos, e_request);
                            break;
                        case FileRequest:
                            sendFile(oos, e_request);
                            sendEvent(oos, e_request);
                            break;
                        case DeleteFiles:
                            deleteFiles(e_request);
                            sendEvent(oos, e_request);
                            break;
                        case EchoEvent:
                            //Send Event
                            sendEvent(oos, e_request);
                            break;
                        
                        
                    }
                   
            }catch(IOException ex){
                System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            } catch (Exception ex) {
                System.out.println(ex);
            }finally{
                try{
                    socket.close();
                }catch (IOException ex){
                    System.out.println(ex);
                }
                System.out.println("Disconnected from" + socket.getRemoteSocketAddress() + "\n" + "Killing thread\n ");
            }
        }
        
        
        private void sendEvent(ObjectOutputStream oos, Event e) throws IOException{
        //Send Event
            e.appendToLog("Sending event to Client @ " + socket.getRemoteSocketAddress());
            oos.writeObject(e);
        }

        private Event recieveEvent(ObjectInputStream ois) throws IOException, ClassNotFoundException{
            Event e;
            e = (Event) ois.readObject();
            e.appendToLog("Recieved event from Client @ " + socket.getRemoteSocketAddress());
            return e;
        }
        
        private void sendFile(ObjectOutputStream oos, Event e) throws FileNotFoundException, IOException{
        
        for(FileObject f: e.getAssociatedFiles()){
            //Send f
            File file = new File(STORAGEPATH + f.getEncodedName());
            FileInputStream fis = new FileInputStream(file);
            byte [] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            
            while((bytesRead = fis.read(buffer)) > 0){
                oos.writeObject(bytesRead);
                oos.writeObject(Arrays.copyOf(buffer, buffer.length));
            }            
                    
        }
        }

        private void recieveFile(ObjectInputStream ois, Event e) throws IOException, ClassNotFoundException, Exception{
            for(FileObject f : e.getAssociatedFiles()){
                //Recieve f
                
                FileOutputStream fos = new FileOutputStream(STORAGEPATH + f.getEncodedName());
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
                
                System.out.println("File Downloaded: " + STORAGEPATH + f.getEncodedName());
                e.appendToLog("Recieved file from Client @ " + socket.getRemoteSocketAddress());
                
                fos.close();
            }
        }
        private void deleteFiles(Event e){
            File file;
            for(FileObject f : e.getAssociatedFiles()){
                file = new File(STORAGEPATH + f.getEncodedName());
                file.delete();
                e.appendToLog("Deleted File: " + f.getEncodedName() + " from Server");
                System.out.println("File Deleted: " + STORAGEPATH + f.getEncodedName());
            }
        }
        
        
        

   }











}