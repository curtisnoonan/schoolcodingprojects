/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcrypt;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Ali_h
 */

public class Backup implements Serializable{
    
    private ArrayList<FileObject> files;
    private ArrayList<String> conflict;
    private Calendar lastOccurance;
    private Calendar specifiedTime;
    private Calendar currentTime;
    private String backupName;
    private String poolName;
    private int frequency;
    
    //where will the server go, choose server 
        
    Timer myTimer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run(){
            currentTime = Calendar.getInstance();
            currentTime.clear(Calendar.MILLISECOND);
            currentTime.clear(Calendar.SECOND);
            switch (frequency){
                case 0:
                    if (specifiedTime.compareTo(currentTime) == 0){     
                        RCrypt.CURRENT_USER.addFilesToPool(files, poolName);    //should maybe also add && frequency == 0 etc incase freq is changed. Need GUI to test
                        System.out.println("They have the same time.");             
                        myTimer.cancel();
                    }
                    else{
                        System.out.println("They do not have the same time.");
                    }
                    break;
                case 1:
                    if (specifiedTime.compareTo(currentTime) == 0){
                        RCrypt.CURRENT_USER.addFilesToPool(files, poolName);
                        System.out.println("They have the same time.");
                        specifiedTime.add(Calendar.DATE, 1);
                    }
                    else{
                        System.out.println("They do not have the same time.");
                    }
                    break;
                case 2:
                    if (specifiedTime.compareTo(currentTime) == 0){
                        RCrypt.CURRENT_USER.addFilesToPool(files, poolName);
                        System.out.println("They have the same time.");
                        specifiedTime.add(Calendar.DATE, 7);
                    }
                    else{
                        System.out.println("They do not have the same time.");
                    }
                    break;
                case 3:
                    if (specifiedTime.compareTo(currentTime) == 0){
                        RCrypt.CURRENT_USER.addFilesToPool(files, poolName);
                        System.out.println("They have the same time.");
                        specifiedTime.add(Calendar.DATE, 30);
                    }
                    else{
                        System.out.println("They do not have the same time.");
                    }
                    break;
                default:
                    System.out.println("No frequency choice was picked.");
                    myTimer.cancel();                                           //should it be myTimer.cancel or timer.cancel
            }
        }  
    };
    
    public void start() {
        myTimer.scheduleAtFixedRate(task, 0, 10000);
    }
    
    public Backup(){
        files = new ArrayList<>();
    }
    
    public Backup (ArrayList<FileObject> file, ArrayList<String> c, String bName, int freq, Calendar time){
        files = new ArrayList<FileObject>();
        this.conflict = c;
        this.backupName = bName;
        this.frequency = freq;
        this.specifiedTime = time;
       
        for (int i = 0; i < file.size(); i++){
            walk(file.get(i).getAbsolutePath());
        }
        
    }
    public void setPoolName(String name){
        poolName = name;
    }
    public String getPoolName (){
        return this.poolName;
    }
    
    public void setTime(Calendar time){
        this.specifiedTime = time;
    }
    
    public Date getTime(){
        return specifiedTime.getTime();
    }
    
    public void walk(String path){
        System.out.println("Hello from walk");
        File root = new File(path);
        File[] list = root.listFiles();
        
        FileObject enter;
        
        if (list == null)
        {
            System.out.println("Hello from if null");
            enter = new FileObject(root.getPath());
            files.add(enter);
            return;
        }
        
        for ( File f: list){
            if (f.isDirectory()){
                walk(f.getPath());
                System.out.println("Hello from if of walk");
                //System.out.println("Dir: " + f.getAbsoluteFile());
            }
            else{
                enter = new FileObject(f.getPath());
                System.out.println("Hello from else of walk");
                files.add(enter);
                //System.out.println("File: "+ f.getAbsoluteFile());
            }
        }
    }
    
    public void printFiles(){
        //System.out.println("Hello from printFiles.");
        System.out.println("Number of Files in Files: " + files.size());
        for (int i = 0; i < files.size(); i++) {
            System.out.println("File: " + files.get(i).getName());
        }
    }
    
    public void addFileToBackUp(FileObject add){
        walk(add.getAbsolutePath());
    }
    
    public void addFileToBackUp(ArrayList<FileObject> add){
        System.out.println("added file size is: " + add.size());
        for (int i = 0; i < add.size(); i++){
            walk(add.get(i).getAbsolutePath());
        }
    }
    
    public ArrayList<FileObject> getFiles(){
        return files;
    }
    
    /*public void removeFile(FileObject remove){
        String name = remove.getName();
        for (int i = 0; i < files.size(); i++){
            if (name == files.get(i).getName())
        }
    }*/
    // how will files be stored on the server? will i use encoded name?
    
    public void setBackUpName(String name){
        this.backupName = name;
    }
    
    public String getBackUpName(){
        return this.backupName;
    }

    public void removeBackup(){
        
    }
    
    public void setFrequency(int f){
        this.frequency = f;
    }
    
    public int getFrequency(){
        return this.frequency;
    }
    
    public void cancelTimer(){
        myTimer.cancel();
    }
   
}
