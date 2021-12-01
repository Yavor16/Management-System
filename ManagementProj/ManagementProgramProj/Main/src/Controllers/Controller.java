package Controllers;

import javafx.scene.image.*;
import javafx.scene.*;
import javafx.fxml.*;
import java.net.*;
import java.io.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class Controller implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private Alert alert = new Alert(AlertType.ERROR);

    @FXML
    Button buyBttn;
    @FXML 
    Button addBttn;
    
    public void initialize(URL url, ResourceBundle rb ){
        addImageToBttn(addBttn, "adminImage");
        addImageToBttn(buyBttn, "userImage");
    }
    private void addImageToBttn(Button bttn, String imageName){
        try {
            URL url = new File("Main/images/" + imageName + ".png").toURI().toURL();
            Image img = new Image(url.toString(), 130 ,130,true,false);
            
            bttn.setGraphic(createImageViewForNewIcon(img));
        } catch (MalformedURLException e) {
            alert.setContentText("Cannot set icon to button");
            alert.show();
        }
    }
    public void OpenUserScene(ActionEvent e) {
        createNewScene(e, "UserScenes/MainUserScene");
    }
    
    public void OpenAdminScene(ActionEvent e) {
        createNewScene(e, "AdminScenes/AdminMainScene");
    }
    private void createNewScene(ActionEvent e, String sceneName){
        try {
            URL url = new File("Main/src/Scenes/" + sceneName + ".fxml").toURI().toURL();
            root = FXMLLoader.load(url);

            stage = (Stage)((Node )e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exe) {
            alert.setContentText("Cannot create new scene");
            alert.show();
        }
    }
    private ImageView createImageViewForNewIcon(Image img){
        ImageView newImageVIew = new ImageView(img);
            
        newImageVIew.maxHeight(130);
        newImageVIew.maxWidth(130);
        newImageVIew.setPreserveRatio(true);

        return newImageVIew;
    }
}
