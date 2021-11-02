package ManagementProgramProj.Main.src.Controllers.AdminSceneControllers;
import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMainSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button openAddScenetBttn;
    
    public void openAddProductScene(ActionEvent e) throws IOException{
          
        root = FXMLLoader.load(getClass().getClassLoader().getResource("ManagementProgramProj/Main/src/Scenes/AdminScenes/AddProduct.fxml"));
        
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);

        openAddScenetBttn.setDisable(true);    
        stage.showAndWait();
        openAddScenetBttn.setDisable(false);    
    }
    
}
