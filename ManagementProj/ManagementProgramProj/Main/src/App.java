import Controllers.DBConnection;
import Controllers.SaveAndLoadHistory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception{
        DBConnection.Connect();
        DBConnection.GetProducts();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e){
                SaveAndLoadHistory.SaveFile();
                Platform.exit();
            }
        });
        try{
            Parent root = FXMLLoader.load(getClass().getResource("./Scenes/OpenScene.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Management System");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}