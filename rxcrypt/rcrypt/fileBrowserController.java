package rcrypt;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.event.*;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


   
public class fileBrowserController implements Initializable {

    @FXML private ComboBox<String> directoryselection = new ComboBox<>();
    @FXML private ComboBox<String> poolselection = new ComboBox<>();
    @FXML private JFXHamburger ham;
    @FXML private JFXDrawer drawer;
    @FXML private Button getDirectory;
    @FXML private ListView remotewindow;
    @FXML private ListView fileSelected;
    @FXML private Button uploadButton;
    @FXML private Button downloadButton;
    @FXML private Text progress;
    
    //public TreeView<String> dirTree;

    /**public static void getFileName(File file,TreeItem<String> root) {
        //if(level == 0){

        //}
        File fileT = new File(file.getPath());
        if(fileT.listFiles() != null) {
            File[] fileR = fileT.listFiles();
            for (int i = 0; i < fileR.length; i++) {
                if (!fileR[i].isHidden()) {
                    if (fileR[i].isDirectory()) {
                        TreeItem<String> root2 = new TreeItem<>(fileR[i].getName());
                        root.getChildren().add(root2);
                        File fileTe = new File(fileR[i].getPath());
                        File[] fileRe = fileTe.listFiles();
                        if(fileTe.listFiles() != null) {
                            for (int j = 0; j < fileRe.length; j++) {
                                if (!fileRe[j].isHidden()) {
                                    TreeItem<String> root3 = new TreeItem<>(fileRe[j].getName());
                                    root2.getChildren().add(root3);
                                    File fileTee = new File(fileRe[j].getPath());
                                    File[] fileRee = fileTee.listFiles();
                                    if(fileTee.listFiles() != null) {
                                        for (int k = 0; k < fileRee.length; k++) {
                                            if (!fileRee[k].isHidden()) {
                                                TreeItem<String> root4 = new TreeItem<>(fileRee[k].getName());
                                                root3.getChildren().add(root4);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }
    */
    /**public static TreeItem<String> createTreeItem(String fname, TreeItem<String> t){
        TreeItem<String> root = new TreeItem<>(fname);
        t.getChildren().add(root);
        return t;
    }*/
    /**public static String getFileDirectory(TreeItem<String> t, String x){
        
        String a = "/";
        String y = "";
        if(t != null){

            System.out.println("y: "+y);
            if(t.getParent()!= null){
                y = x;
                y = y.concat("/").concat(t.getValue());
                System.out.println("y at concat: " + y);
                getFileDirectory(t.getParent(),y);
            }
            System.out.println("y after if: " + y);
            
        }
        return y;
    }
    */
    @FXML
    void getDirectory(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        if(selectedFiles != null){
            for(int i = 0; i < selectedFiles.size(); i++){
                fileSelected.getItems().add(selectedFiles.get(i).getAbsolutePath());
            }
        }
        else{
            System.out.println("Error");
        }
        
    }
 
    ObservableList<String> tempOne = FXCollections.observableArrayList();

    public void setBox(String x){
        
        tempOne = FXCollections.observableArrayList(x);
        //this.list = list;
    }
     
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SIDEMENU
        try {
            VBox box = FXMLLoader.load(getClass().getResource("sideMenu.fxml"));
            drawer.setSidePane(box);

            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(ham);
            transition.setRate(-1);
            ham.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                transition.setRate(transition.getRate() * -1);
                transition.play();
                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(fileBrowserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //REMOTE ListView
        remotewindow.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                
        progress.setText(null);
        
        
        
        /**
        //DIRECTORYTREE
        File file = new File("/");
        ArrayList<String> tempFile = getFileName(file);

        TreeItem<String> root = new TreeItem<>("/");

        for (int i = 0; i != tempFile.size(); i++) {
            createTreeItem(tempFile.get(i), root);
        }
        dirTree.setRoot(root);
        dirTree.setShowRoot(true);
        dirTree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                String t = "/";
                String x = t.concat(newValue.getValue()).concat(t);
                File newF = new File(x);
                ArrayList<String> tempFile1;
                if (newF.isDirectory()) {
                    tempFile1 = getFileName(newF);
                    for (int i = 0; i != tempFile1.size(); i++) {
                        createTreeItem(tempFile1.get(i), newValue);
                    }
                }
            }

        });*/
        //DIRECTORYTREE
        /**
        File[] paths;
        paths = File.listRoots();
        for(int i = 0; i < paths.length;i++){
            TreeItem<String> root = new TreeItem<>(paths[i].toString());
            File file = new File(paths[0].getPath());
            getFileName(file,root);
            dirTree.setRoot(root);
            dirTree.setShowRoot(true);
        }
        dirTree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            System.out.println();
            //if (newValue != null) {
                String t = "/";
                String x = t.concat(newValue.getValue()).concat(t);
                //System.out.print("New Value: ");
                //String p = getFileDirectory(newValue.getParent(),newValue.getValue());
                //System.out.print(p);
            //}
        });
        */
        
        //POOL COMBOBOX
        
            for(Pool p: RCrypt.CURRENT_USER.getPools())
                poolselection.getItems().add(p.getName());
            
            poolselection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                    refreshRemoteWindow();  
                }
              });
       
        
    }
    
    @FXML
    void uploadButtonPressed(ActionEvent event) {
        ArrayList <FileObject> files = new ArrayList<>();
        List l = fileSelected.getItems();
        for(Object s: l){
            files.add(new FileObject((String) s));
        }
        progress.setText("Transfering");
        RCrypt.CURRENT_USER.addFilesToPool(files, poolselection.getSelectionModel().selectedItemProperty().getValue());
        progress.setText("Transfer Complete 100%");
        
        
        refreshFileList();
        refreshRemoteWindow();
        
    }
    @FXML
    void downloadButtonPressed(ActionEvent event) {
        ArrayList<FileObject> files = new ArrayList<>();
        List l = remotewindow.getSelectionModel().getSelectedItems();
        for(Object s: l){
            files.add(new FileObject((String) s));
        }
        progress.setText("Transfering");
        RCrypt.CURRENT_USER.getFilesFromPool(files, poolselection.getSelectionModel().selectedItemProperty().getValue());
        progress.setText("Transfer Complete 100%");
        
        
    }
    @FXML
    void deleteButtonPressed(ActionEvent event) {
        ArrayList<FileObject> files = new ArrayList<>();
        List l = remotewindow.getSelectionModel().getSelectedItems();
        for(Object s: l){
            files.add(new FileObject((String) s));
        }
        RCrypt.CURRENT_USER.removeFilesFromPool(files, poolselection.getSelectionModel().selectedItemProperty().getValue());
        refreshRemoteWindow();
    }
    
    private void refreshFileList(){
        fileSelected.getItems().clear();
    }
    
    private void refreshRemoteWindow(){
        //remotewindow
        remotewindow.getSelectionModel().clearSelection();
        remotewindow.getItems().clear();
        
        String pool = poolselection.getSelectionModel().selectedItemProperty().getValue();
        HashMap<String, FileObject> files = RCrypt.CURRENT_USER.getPool(pool).getFileManifest();
        for(String currentKey : RCrypt.CURRENT_USER.getPool(pool).getFileManifest().keySet()){
            remotewindow.getItems().add(files.get(currentKey).getAbsolutePath());
        }
    }
    
            
    
}
