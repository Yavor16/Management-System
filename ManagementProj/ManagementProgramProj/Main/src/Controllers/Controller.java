package Controllers;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller  implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Button buyBttn;
    @FXML 
    Button addBttn;
    
    public void initialize(URL url, ResourceBundle rb ){
        AddImageToBttn(addBttn, "adminImage");
        AddImageToBttn(buyBttn, "userImage");
    }
    public void OpenUserScene(ActionEvent e) throws IOException{
        URL url = new File("Main/src/Scenes/UserScenes/UserScene.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void OpenAdminScene(ActionEvent e) throws IOException {
        URL url = new File("Main/src/Scenes/AdminScenes/AdminMainScene.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    void AddImageToBttn(Button bttn, String imageName){
        try {
            URL url = new File("Main/images/" + imageName + ".png").toURI().toURL();
            Image img = new Image(url.toString(), 130 ,130,true,false);
            ImageView view = new ImageView(img);
            
            view.maxHeight(130);
            view.maxWidth(130);
            view.setPreserveRatio(true);
            bttn.setGraphic(view);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
