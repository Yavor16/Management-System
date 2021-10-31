package ManagementProgramProj.Main.src;

import java.io.IOException;

import javax.xml.transform.stax.StAXResult;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception{

        try{

            Parent root = FXMLLoader.load(getClass().getResource("./Scenes/OpenScene.fxml"));
            Scene scene = new Scene(root);
            
            stage.setTitle("Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}