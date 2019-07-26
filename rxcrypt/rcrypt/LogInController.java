package rcrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LogInController implements Initializable {

    @FXML
    private AnchorPane logInPane;

    @FXML
    private Button logIn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void changeSceneButtonPushed(ActionEvent event) throws IOException{
        Parent logInParent = FXMLLoader.load(getClass().getResource("openingPage.fxml"));
        Scene opening_page_scene = new Scene(logInParent);
        Stage openingPage_stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        openingPage_stage.setScene(opening_page_scene);
        openingPage_stage.show();

    }
}
