package ManagementProgramProj.Main.src.Controllers.UserSceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UserAmountScene {
    @FXML
    TextField amountTextField;

    public static int amount;

    public void SetAmount(ActionEvent e){
        Stage stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        UserMainScene.amountToBuy = Integer.parseInt(amountTextField.getText());
        stage.close();
    }
}
