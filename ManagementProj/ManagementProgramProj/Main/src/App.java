package ManagementProgramProj.Main.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception{
        DBConnection.Connect();
        DBConnection.GetProducts();
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