package Controllers.UserSceneControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryController implements Initializable{
    @FXML
    VBox vBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!MainScene.queueForBill.isEmpty()) {   
            if (MainScene.queueForBill.size() > 10) {
                MainScene.queueForBill.remove();
            }
            for (VBox bill : MainScene.queueForBill) {
                vBox.getChildren().add(bill);
            }        
        }
    }
    public void CloseScene(ActionEvent e){
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
