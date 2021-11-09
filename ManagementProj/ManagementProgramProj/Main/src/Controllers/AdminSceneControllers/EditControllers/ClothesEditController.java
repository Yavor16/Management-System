package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.ClothesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ClothesEditController extends EditProductController{
    ClothesModel chosenProduct;
    @FXML
    TextField sizeText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        chosenProduct = (ClothesModel)selectedProd;
        sizeText.setText(chosenProduct.GetSize());
    }
    @Override
    public void UpdateProduct(ActionEvent e) throws SQLException {
        if(SetPrice() && SetQuantity() && SetSize()){
            ClothesModel pModel = new ClothesModel(chosenProduct.GetID(), 
                                                                        chosenProduct.GetName(), 
                                                                        chosenProduct.GetCategory(), 
                                                                        quantity, 
                                                                        price, 
                                                                        sizeText.getText());
            UpdateProductToDB(pModel);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
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
