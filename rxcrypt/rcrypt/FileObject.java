package rcrypt;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.util.Pair;


public class FileObject implements Serializable {
    private String NAME;
    private String PARENTPATH;
    private String ABSOLUTEPATH;
    private String ENCODEDNAME;
    private String OUTPUTABSOLUTEPATH;
    private String KEYNAME;
    private final long SIZE;
    private ArrayList<Pair<ServerNode,Boolean>> SERVERS = new ArrayList<>();
    
    public FileObject(String absolutePath) {
        //Get file size everytime FileObject is Intanciated
        File f = new File(absolutePath);
        NAME = f.getName();
        ABSOLUTEPATH = f.getAbsolutePath(); //this fixes differences in directory delimiters
        PARENTPATH = f.getParent() + "\\";
        SIZE = f.length();
    }
    public String getName(){
        return this.NAME;
    }
    public String getAbsolutePath(){
        return this.ABSOLUTEPATH;
    }
    public String getEncodedName(){
        return this.ENCODEDNAME;
    }
    public void setEncodedName(String encodedName){
        this.ENCODEDNAME = encodedName;
    }
    public String getParentPath(){
        return PARENTPATH;
    }
    public int getSize(){
        return (int) SIZE;
    }
    public void setKeyName(String keyName){
        KEYNAME = keyName;
    }
    public String getKeyName(){
        return KEYNAME;
    }
    public void setServers(ArrayList<ServerNode> s){
        for(ServerNode i: s){
            addServer(i);
        }
    }
    public void addServer(ServerNode s){
        SERVERS.add(new Pair(s,false));
    }
}
