package ManagementProgramProj.Main.src;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void OpenUserScene(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("./Scenes/UserScenes/UserScene.fxml"));
        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void OpenAdminScene(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("./Scenes/AdminScenes/AdminMainScene.fxml"));
        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
