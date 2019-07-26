package rcrypt;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class openingPageController implements Initializable {
    @FXML private JFXHamburger ham;
    @FXML private JFXDrawer drawer;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Text loginconfirmation;
    @FXML private Text accountcreation;
    @FXML private Button logout;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //SIDEMENU
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
            if(RCrypt.CURRENT_USER == null){
                ham.setVisible(false);
                logout.setVisible(false);
            }
            else
                logout.setText("Sign Out: " + RCrypt.CURRENT_USER.getName());
        
        } catch (IOException ex) {
            Logger.getLogger(openingPageController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        //LOGIN
        
        
        
        
    }
    
        
    @FXML
    void loginButtonPressed(ActionEvent event) {
        loginconfirmation.setText(null);
        accountcreation.setText(null);
        //Authenticate
        RCrypt.USER_KEY = Authentication.getUserKey(password.getText());
        RCrypt.CURRENT_USER = Authentication.login(username.getText(), RCrypt.USER_KEY);
        if(RCrypt.CURRENT_USER == null){
            loginconfirmation.setText("Authentication failed");
            username.clear();
            password.clear();
        }
        else{
            loginconfirmation.setText("Login Successful");
            logout.setVisible(true);
            logout.setText("Sign Out: " + RCrypt.CURRENT_USER.getName());
            ham.setVisible(true);
            username.clear();
            password.clear();
            
        }
    }
    @FXML
    void createUserButtonPressed(ActionEvent event) {
        loginconfirmation.setText(null);
        accountcreation.setText(null);
        if(username.getText().isEmpty() || password.getText().isEmpty()) {
        //RCrypt.CURRENT_USER = new User(username.getText());
        //RCrypt.USER_KEY = Authentication.getUserKey(password.getText());
        //Authentication.createUser(username.getText(), RCrypt.USER_KEY);
        //accountcreation.setText("Account Created");
        //RCrypt.CURRENT_USER = Authentication.login(username.getText(), RCrypt.USER_KEY);
        //if(RCrypt.CURRENT_USER == null){
            loginconfirmation.setText("Authentication failed");
            username.clear();
            password.clear();
        } 
        /*
        if(username.getText() == null) {
        //if(RCrypt.CURRENT_USER == null){
            loginconfirmation.setText("Authentication failed");
            username.clear();
            password.clear();
        }*/
        else{
            RCrypt.CURRENT_USER = new User(username.getText());
            RCrypt.USER_KEY = Authentication.getUserKey(password.getText());
            Authentication.createUser(username.getText(), RCrypt.USER_KEY);
            accountcreation.setText("Account Created");
            RCrypt.CURRENT_USER = Authentication.login(username.getText(), RCrypt.USER_KEY);
            loginconfirmation.setText("Login Successful");
            logout.setVisible(true);
            logout.setText("Sign Out: " + RCrypt.CURRENT_USER.getName());
            ham.setVisible(true);
            username.clear();
            password.clear();
        }
    }
    @FXML
    void logoutButtonPressed(ActionEvent event) {
        //SAVE USER
        Authentication.saveUser(RCrypt.CURRENT_USER, RCrypt.USER_KEY);
        RCrypt.CURRENT_USER = null;
        RCrypt.USER_KEY = null;
        logout.setText(null);
        logout.setVisible(false);
        ham.setVisible(false);
    }
   
}

