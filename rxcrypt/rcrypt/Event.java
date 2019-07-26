package rcrypt;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//Upon Creation, time is recorded
public class Event implements Serializable
{
    //Instance Variables
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss"); //Time Format
        private EventType TYPE;
        private Calendar OCCURANCE;
        private String LOG;
        private ArrayList<FileObject> ASSOCIATED_FILES = new ArrayList<>();
        private ServerNode ORIGINATING_NODE;
    
    //CONSTRUCTORS
    public Event(EventType e){
        logInitialization(e);
    }
    public Event(EventType e, ServerNode s){
        logInitialization(e);
        ORIGINATING_NODE = s;
    }    
    
    //METHODS
    private void logInitialization(EventType e){
        TYPE = e;
        OCCURANCE = Calendar.getInstance();
        
        //Preparing Event Log
        switch (TYPE){
            case FileUpload:
                LOG = "#Upload Request -  " ;
            break;
            
            case FileRequest:
                LOG = "#Download Request -  " ;
            break;
            case EchoEvent:
                LOG = "#Echo Event -  ";
            break;
                       
            
                
        }
        LOG += sdf.format(OCCURANCE.getTime());
    }
    
    public void copyEvent(Event e){
        LOG = e.getLog();
        TYPE = e.getEventType();
        OCCURANCE = e.getOccurance();
        ASSOCIATED_FILES = e.getAssociatedFiles();
        ORIGINATING_NODE = e.getOriginatingNode();
    }
    
    //Appends to the current event log adding timestamp
    public void appendToLog(String s)
    {
        Calendar cal = Calendar.getInstance();
        LOG += "\n" + sdf.format(cal.getTime()) + " - " + s;
    }
    
    //Returns the Event Log
    public String getLog()
    {
        return LOG;
    }
    
    public EventType getEventType(){
        return TYPE;
    }
    
    public Calendar getOccurance(){
        return OCCURANCE;
    }
    
    public ArrayList<FileObject> getAssociatedFiles(){
        return ASSOCIATED_FILES;
    }
    
    public void addAssociatedFile(String fName){
        FileObject f = new FileObject(fName);
        ASSOCIATED_FILES.add(f);
    }
    public void addAssociatedFile(FileObject f){
        ASSOCIATED_FILES.add(f);
    }
    
    public ServerNode getOriginatingNode(){
        return ORIGINATING_NODE;
    }
    
    
    
}