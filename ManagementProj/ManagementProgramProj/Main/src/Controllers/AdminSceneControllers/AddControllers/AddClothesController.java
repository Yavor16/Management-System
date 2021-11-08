package Controllers.AdminSceneControllers.AddControllers;

import java.sql.SQLException;

import Models.ClothesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddClothesController extends AddProductController {
    @FXML
    TextField sizeText;
    @Override
    public void AddProduct(ActionEvent e) throws SQLException {
        if(SetName() && SetQuantity() && SetPrice() && SetSize()){
            ClothesModel pModel = new ClothesModel(GetLastIndex(), 
                                                                        nameText.getText(), 
                                                                        category, 
                                                                        quantity, 
                                                                        price,
                                                                        sizeText.getText());
            AddProductToDB(pModel);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    Boolean SetSize(){
        if (sizeText.getText().isEmpty()) {
            alert.setContentText("Size: Enter a size");
            alert.show();            
        }
        return true;
    }
}
