package rcrypt;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class manageServersController implements Initializable {
    @FXML private JFXHamburger ham;
    @FXML private JFXDrawer drawer;
    @FXML private Button addServer;
    @FXML private Button removeServer;
    @FXML private Button editServer;
    @FXML private Button apply;
    @FXML private TextField serverName;
    @FXML private TextField address;
    @FXML private ComboBox<String> pool = new ComboBox<>();
    @FXML private ComboBox<String> keyname = new ComboBox<>();
    @FXML private TextField port;
    @FXML private ListView serverlist;
    
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
            Logger.getLogger(manageServersController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        //OTHER
        apply.setText("Add");
        
        
        //SERVERLIST
        for(ServerNode s: RCrypt.CURRENT_USER.getPool("ALL").getMembers()){
            serverlist.getItems().add(s.getName());
        }
        
        //PORT
        //port.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        
        //POOL COMBOBOX
        refreshPoolList();        
        
        //KEYNAME COMBOBOX
        refreshKeyList();
        
       
        
        
        
    }
    public void addServerPushed(ActionEvent event) throws IOException {
        resetAddArea();
        apply.setText("Add");
        System.out.println("Add server Pushed");
    }
    public void removeServer(ActionEvent event) throws IOException {
        resetAddArea();
        String name = (String) serverlist.getSelectionModel().getSelectedItem();
        RCrypt.CURRENT_USER.removeMemberFromPools(name);
        refreshServerList();
    }
    public void editServer(ActionEvent event) throws IOException {
        String name = (String) serverlist.getSelectionModel().getSelectedItem();
        ServerNode node = RCrypt.CURRENT_USER.getPool("ALL").getMember(name);
        Pool p = RCrypt.CURRENT_USER.findMemberInPools(name);
        serverName.setText(node.getName());
        address.setText(node.getAddress());
        port.setText(Integer.toString(node.getPort()));
        pool.setValue(p.getName());
        keyname.setValue(p.getKeyName());
        apply.setText("Apply");
        System.out.println("edit server Pushed");
    }
    public void applyButtonPushed(ActionEvent event) throws IOException {
        String POOLNAME = pool.getSelectionModel().selectedItemProperty().getValue();
        String SERVERNAME = serverName.getText();
        String ADDRESS = address.getText();
        int PORT = Integer.parseInt(port.getText());
        
        ServerNode node = RCrypt.CURRENT_USER.getPool("ALL").getMember(SERVERNAME);
        Pool p = RCrypt.CURRENT_USER.getPool(POOLNAME);
        if( p == null){
            //If pool doesnt exist
            RCrypt.CURRENT_USER.addPool(POOLNAME, keyname.getSelectionModel().selectedItemProperty().getValue());
        }
        if( node != null)
        {
            //Edit server
            node.setName(SERVERNAME);
            node.setAddress(ADDRESS);
            node.setPort(PORT);
            
        }
        else{
            //Add server to pool
            node = new ServerNode(SERVERNAME,ADDRESS , PORT);
            RCrypt.CURRENT_USER.addMemberToPool(node,POOLNAME);
            serverlist.getItems().add(node.getName());
        }
            
            resetAddArea();
            
    }

    private void refreshServerList(){
        serverlist.getItems().clear();
        for(ServerNode s: RCrypt.CURRENT_USER.getPool("ALL").getMembers()){
            serverlist.getItems().add(s.getName());
        }
    }
    
    private void refreshPoolList(){
        //POOL COMBOBOX
        pool.getItems().clear();
        pool.setValue(null);
        pool.promptTextProperty().set("New/Existing");
        for(Pool p: RCrypt.CURRENT_USER.getPools())
            pool.getItems().add(p.getName());  
    }
    
    private void refreshKeyList(){
        //KEYNAME COMBOBOX
        keyname.getSelectionModel().clearSelection();
        keyname.getItems().clear();
        keyname.setValue(null);
        for(String k: RCrypt.CURRENT_USER.getKeySet())
            keyname.getItems().add(k);
    }
    
    private void resetAddArea(){
            serverName.clear();
            address.clear();
            port.clear();
            
            
            refreshPoolList();
            refreshKeyList();
    }
}