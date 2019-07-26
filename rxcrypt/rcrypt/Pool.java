/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcrypt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.DatatypeConverter;


public class Pool implements Serializable {
    private String NAME;
    private String DEFAULT_KEYNAME;
    private ArrayList<ServerNode> MEMBERS = new ArrayList<>();
    private HashMap<String, FileObject> FILE_MANIFEST = new HashMap<>();
    //Queue<FileObject> FILE_QUEUE = new LinkedList<>();
    private int FILECOUNTER;
    
    public Pool(String name, String keyName){
        NAME = name;
        DEFAULT_KEYNAME = keyName;
        FILECOUNTER = 0;
    }
    
    public String getName(){
        return NAME;
    }
    public void setName(String s){
        NAME = s;
    }
    public String getKeyName(){
        return DEFAULT_KEYNAME;
    }
    public ArrayList<ServerNode> getMembers(){
        return MEMBERS;
    }
    
    public void addMember(ServerNode node){
        MEMBERS.add(node);
    }
    public ServerNode getMember(String name){
        for(ServerNode s: MEMBERS){
            if(s.getName().equals(name))
                return MEMBERS.get(MEMBERS.indexOf(s));
        }
        return null;
    }
    public void removeMember(String serverName){
        for(ServerNode s : MEMBERS){
            if(s.getName().equals(serverName)){
                MEMBERS.remove(s);
                break;
            }
        }
            
    }
    public HashMap<String, FileObject> getFileManifest(){
        return FILE_MANIFEST;
    }
    public FileObject getFile(String absolutePath){
        String keyFound=null;
        for(String currentKey : FILE_MANIFEST.keySet()){
            if(FILE_MANIFEST.get(currentKey).getAbsolutePath().equals(absolutePath)){
                keyFound = currentKey;
                break;
            }
        }
        return FILE_MANIFEST.get(keyFound);
    }
    //Add file with default pool key
    public void addFile(FileObject file){
        file.setKeyName(DEFAULT_KEYNAME);
        //Obfuscating File Name
        file.setEncodedName(DatatypeConverter.printHexBinary(NAME.getBytes()) + Integer.toHexString(FILECOUNTER) + ".dat");
        FILECOUNTER++;
        
        FILE_MANIFEST.put(file.getEncodedName(), file);    //Adding File to manifest   
    }
    //Add file with unique key
    public void addFile(FileObject file, String keyName){
        file.setKeyName(keyName);
        //Obfuscating File Name
        file.setEncodedName(DatatypeConverter.printHexBinary(NAME.getBytes()) + Integer.toHexString(FILECOUNTER) + ".dat");
        FILECOUNTER++;
        
        FILE_MANIFEST.put(file.getEncodedName(), file);    //Adding File to manifest   
    }
    public void removeFile(FileObject file){
        FILE_MANIFEST.remove(file.getEncodedName());
    }
    
}

