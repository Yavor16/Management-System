package Controllers.AdminSceneControllers.AddControllers;

import java.sql.SQLException;

import Models.TechnologyProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTechnologyProdController extends AddProductController{
    @FXML
    TextField resolutionText;
    @FXML
    CheckBox usedCheckBox;
    
    @Override
    public void AddProduct(ActionEvent e) throws SQLException {
        if(SetName() && SetQuantity() && SetPrice() && SetResolution()){
            TechnologyProductModel pModel = new TechnologyProductModel(GetLastIndex(), 
                                                                        nameText.getText(), 
                                                                        category, 
                                                                        quantity, 
                                                                        price, 
                                                                        resolutionText.getText(), 
                                                                        usedCheckBox.isSelected());
            AddProductToDB(pModel);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }        
    Boolean SetResolution(){
        if (resolutionText.getText().isEmpty()) {
            alert.setContentText("Resolution: Enter a resolution");
            alert.show();
            return false;
        }
        return true;
    }
}
