package Controllers.UserSceneControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ManageBills;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryController implements Initializable{
    @FXML
    VBox vBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!ManageBills.bills.isEmpty()) {   
            if (ManageBills.bills.size() > 10) {
                ManageBills.bills.remove();
            }
            for (VBox bill : ManageBills.bills) {
                vBox.getChildren().add(bill);
            }        
        }
    }
    public void CloseScene(ActionEvent e){
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
