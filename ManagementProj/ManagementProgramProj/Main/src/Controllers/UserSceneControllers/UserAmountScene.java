package ManagementProgramProj.Main.src.Controllers.UserSceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UserAmountScene{
    @FXML
    TextField amountTextField;

    public static int amount;

    public void AddAmount(ActionEvent e){
        if(amountTextField.getText().matches("[0-9]+")){
            Stage stage = (Stage)((Node )e.getSource()).getScene().getWindow();
            try{
                UserMainScene.amountToBuy = Integer.parseInt(amountTextField.getText());
                stage.close();
            }
            catch(Exception exe){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Enter smaller number!");
                alert.showAndWait();
            }
        }
        else{

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Wrong input!");
            alert.setContentText("Enter a number!");
            alert.setHeaderText(null);
            alert.showAndWait();
            amountTextField.setText("");
        }
    }
}
