import java.io.IOException;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import Controllers.*;
import Controllers.DataBaseFunctions.DBConnection;
import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        DBConnection.Connect();
        AllProducts.getProducts();



        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e){
                SaveAndLoadHistory.SaveFile();
                Platform.exit();
            }
        });
        openFirstScene(stage);
    }
    private void openFirstScene(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("./Scenes/OpenScene.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Management System");
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}