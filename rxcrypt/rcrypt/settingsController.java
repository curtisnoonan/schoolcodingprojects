package rcrypt;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class settingsController implements Initializable {

    @FXML private ComboBox keyname;
    @FXML private PasswordField newpassword;
    @FXML private PasswordField newpasswordconfirmation;
    @FXML private JFXHamburger ham;
    @FXML private JFXDrawer drawer;
    @FXML private Text passwordmatch;

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
            Logger.getLogger(settingsController.class.getName()).log(Level.SEVERE,null,ex);
        }
        passwordmatch.setText(null);
        passwordmatch.setVisible(false);
        
        //KEYNAME COMBOBOX
        refreshKeyName();
        
    }
    
    
    
    
    @FXML
    void changePasswordPressed(ActionEvent event) {
        String pass = newpassword.getText();
        passwordmatch.setText(null);
        if(pass.equals(newpasswordconfirmation.getText()))
            RCrypt.USER_KEY = Encryption.genKeyFromPass(pass.toCharArray());
        else{
            passwordmatch.setVisible(true);
            passwordmatch.setText("Passwords do not match");
        }
            
        refreshPassword();
    }
    @FXML
    void addKeyPressed(ActionEvent event) {
        String name  = (String) keyname.getSelectionModel().selectedItemProperty().getValue();
        RCrypt.CURRENT_USER.addKey(name);
        refreshKeyName();
    }
    
    @FXML
    void removeKeyPressed(ActionEvent event) {
        String name  = (String) keyname.getSelectionModel().selectedItemProperty().getValue();
        RCrypt.CURRENT_USER.removeKey(RCrypt.CURRENT_USER, name);
        refreshKeyName();
    }
    
    void refreshKeyName(){
        keyname.getItems().clear();
        keyname.setValue(null);
        keyname.promptTextProperty().set("Enter/Select");
        keyname.getSelectionModel().clearSelection();
        for(String k: RCrypt.CURRENT_USER.getKeySet())
            keyname.getItems().add(k);
    }
    void refreshPassword(){
        newpassword.clear();
        newpasswordconfirmation.clear();
    }
    
    
    
}
