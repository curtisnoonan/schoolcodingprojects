package rcrypt;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class manageBackupsController implements Initializable {

    private Backup current;
    private ArrayList<FileObject> files;
    
    @FXML private AnchorPane anchorid;
    @FXML private TextField backupname;
    @FXML private ListView backupList;
    @FXML private Button addBackup;
    @FXML private Button removeBackup;
    @FXML private Button editBackup;
    @FXML private Button applyBackup;
    @FXML private Button chooseFiles;
    @FXML private Button chooseFolders;
    @FXML private Button printInfo;
    @FXML private CheckBox oneTime;
    @FXML private CheckBox weekly;
    @FXML private CheckBox daily;
    @FXML private CheckBox monthly;
    @FXML private ComboBox<String> pool = new ComboBox<>();
    @FXML private JFXDrawer drawer;
    @FXML private JFXHamburger ham;
    @FXML private JFXDatePicker backupCalendar;
    @FXML private JFXTimePicker backupTime;
    
    
    @FXML
    public void applyButtonPressed(ActionEvent event) {
        current = new Backup();
        String backupNameString = backupname.getText();
        current.setBackUpName(backupNameString);
        current.setBackUpName(backupname.getText());
        
        System.out.println("Backup name is: " + current.getBackUpName());
        
        Calendar specTime = Calendar.getInstance();
        specTime.clear(Calendar.MILLISECOND);
        specTime.clear(Calendar.SECOND);
        LocalDate ld = backupCalendar.getValue();
        LocalTime td = backupTime.getValue();
        specTime.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), td.getHour(), td.getMinute());
        
        current.setTime(specTime);
        System.out.println("Specified time: " + current.getTime());
        if (oneTime.isSelected()){
            current.setFrequency(0);
        }
        else if (daily.isSelected()){
            current.setFrequency(1);
        }
        else if (weekly.isSelected()){
            current.setFrequency(2);
        }
        else if (monthly.isSelected()){
            current.setFrequency(3);
        }
        System.out.println("Freq is: " + current.getFrequency());
        
        
        
        String POOLNAME = pool.getSelectionModel().selectedItemProperty().getValue();
        current.setPoolName(POOLNAME);
        System.out.println("Pool name: " + current.getPoolName());
        current.addFileToBackUp(files);
        current.printFiles();
        current.start();
        RCrypt.CURRENT_USER.addBackup(current);
        refreshBackupList();
        refreshPoolList();
        resetAddArea();
        
    }
    public void refreshBackupList(){
        backupList.getItems().clear();
        for(Backup b: RCrypt.CURRENT_USER.getBackup()){
            backupList.getItems().add(b.getBackUpName());
        }
    }
    
    public void resetAddArea(){
            backupname.clear();
            oneTime.setSelected(false);
            oneTime.setDisable(false);
            daily.setDisable(false);
            daily.setSelected(false);
            weekly.setDisable(false);
            weekly.setSelected(false);
            monthly.setDisable(false);
            monthly.setSelected(false);
            backupCalendar.getEditor().clear();
            backupTime.getEditor().clear();
            pool.getItems().clear();
            pool.setValue(null);
            
    }
    @FXML
    void chooseFilesButton(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        FileObject file;
        files = new ArrayList<>();
        
        if(selectedFiles != null){
            for (int i = 0; i < selectedFiles.size(); i ++){
                file = new FileObject(selectedFiles.get(i).getAbsolutePath());
                files.add(file);
            }
        }
        else{
            System.out.println("Error");
        }
        for (int i = 0; i < files.size(); i++){
            System.out.println("File: " + files.get(i).getName());
        }
        //System.out.println("File: " + files.getName());
    }
    @FXML
    void chooseFoldersButton(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File selectedDirectory = dc.showDialog(stage);
        FileObject file;
        files = new ArrayList<>();
        
        if(selectedDirectory != null){
                file = new FileObject(selectedDirectory.getAbsolutePath());
                files.add(file);
            
        }
        else{
            System.out.println("Error");
        }
        for (int i = 0; i < files.size(); i++){
            System.out.println("File: " + files.get(i).getName());
        }
    }
    
    /*@FXML
    void printInfo(ActionEvent event) {
        //for (int i = 0; i <)
        /*for (int i = 0; i < RCrypt.CURRENT_USER.backupsSize(); i++){
            System.out.println("Backup Name: " + RCrypt.CURRENT_USER.getBackup(i).getBackUpName());
            //RCrypt.CURRENT_USER.getBackup(i).printFiles();
        }
    }*/
    
    @FXML
    public void addBackupPushed(ActionEvent event) {
        System.out.println("Add Backup Pushed");
        refreshPoolList();
    }
    @FXML
    public void removeBackupPushed(ActionEvent event) {
        resetAddArea();
        int selection = backupList.getSelectionModel().getSelectedIndex();
        System.out.println("Selected Backup #: " + selection);
        RCrypt.CURRENT_USER.removeBackup(selection);
        refreshBackupList();
        System.out.println("Remove Backup Pushed");

    }
    
    @FXML
    public void editBackupPushed(ActionEvent event) {
        String selection = (String) backupList.getSelectionModel().getSelectedItem();
        Backup edit = RCrypt.CURRENT_USER.getBackup(selection);
        System.out.println("Specified Time in EDIT: " + edit.getTime());
        Date getDate = edit.getTime();
        Calendar date = Calendar.getInstance();
        date.setTime(getDate);
        backupname.setText(edit.getBackUpName());
        
        LocalDate d = LocalDate.of(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        backupCalendar.setValue(d);
        LocalTime t = LocalTime.of(date.get(Calendar.HOUR), date.get(Calendar.MINUTE));
        backupTime.setValue(t);
        System.out.println("Edit Backup Pushed");
        refreshPoolList();
    }
    
    private void refreshPoolList(){
        //POOL COMBOBOX
        pool.getItems().clear();
        pool.setValue(null);
        pool.promptTextProperty().set("New/Existing");
        for(Pool p: RCrypt.CURRENT_USER.getPools())
        pool.getItems().add(p.getName());  
    }
    
    @FXML
    public void oneTimeChecked(ActionEvent event) {
        if(oneTime.isSelected()){
            daily.setDisable(true);
            weekly.setDisable(true);
            monthly.setDisable(true);
        }
        else{
            daily.setDisable(false);
            weekly.setDisable(false);
            monthly.setDisable(false);
        }
    }
    @FXML
    public void dailyChecked(ActionEvent event) {
        if(daily.isSelected()){
            oneTime.setDisable(true);
            weekly.setDisable(true);
            monthly.setDisable(true);
        }
        else{
            oneTime.setDisable(false);
            weekly.setDisable(false);
            monthly.setDisable(false);
        }
    }
    
    @FXML
    public void weeklyChecked(ActionEvent event) {
        if(weekly.isSelected()){
            oneTime.setDisable(true);
            daily.setDisable(true);
            monthly.setDisable(true);
        }
        else{
            oneTime.setDisable(false);
            daily.setDisable(false);
            monthly.setDisable(false);
        }
    }
    
    @FXML
    public void monthlyChecked(ActionEvent event) {
        if(monthly.isSelected()){
            oneTime.setDisable(true);
            daily.setDisable(true);
            weekly.setDisable(true);
        }
        else{
            oneTime.setDisable(false);
            daily.setDisable(false);
            weekly.setDisable(false);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            Logger.getLogger(manageBackupsController.class.getName()).log(Level.SEVERE,null,ex);
        }
        refreshBackupList();
        refreshPoolList();
        
    }
}
