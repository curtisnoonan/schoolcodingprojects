package rcrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class sideMenuController implements Initializable {
    @FXML private Button home;
    @FXML private Button manageBackups;
    @FXML private Button manageServers;
    @FXML private Button fileBrowser;
    @FXML private Button settingsButton;
    @FXML private Button logsfield;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void homePushed(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("openingPage.fxml"));
        Scene home_scene = new Scene(homeParent);
        Stage home_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        home_stage.setScene(home_scene);
        home_stage.show();
    }
    public void filePushed(ActionEvent event) throws IOException {
        Parent filesParent = FXMLLoader.load(getClass().getResource("fileBrowserWindow.fxml"));
        Scene files_scene = new Scene(filesParent);
        Stage files_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        files_stage.setScene(files_scene);
        files_stage.show();
    }
    public void backupsPushed(ActionEvent event) throws IOException {
        Parent backupParent = FXMLLoader.load(getClass().getResource("manageBackups.fxml"));
        Scene backup_scene = new Scene(backupParent);
        Stage backup_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        backup_stage.setScene(backup_scene);
        backup_stage.show();
    }
    public void serversPushed(ActionEvent event) throws IOException {
        Parent serversParent = FXMLLoader.load(getClass().getResource("manageServers.fxml"));
        Scene servers_scene = new Scene(serversParent);
        Stage servers_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        servers_stage.setScene(servers_scene);
        servers_stage.show();
    }
    public void settingsPushed(ActionEvent event) throws IOException {
        Parent settingsParent = FXMLLoader.load(getClass().getResource("settingsWindow.fxml"));
        Scene settings_scene = new Scene(settingsParent);
        Stage settings_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        settings_stage.setScene(settings_scene);
        settings_stage.show();
    }
    public void logsPushed(ActionEvent event) throws IOException {
        Parent logsParent = FXMLLoader.load(getClass().getResource("logsWindow.fxml"));
        Scene logs_scene = new Scene(logsParent);
        Stage logs_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        logs_stage.setScene(logs_scene);
        logs_stage.show();
    }
}
