package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.TechnologyProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class TechnolofyEditController extends EditProductController{
    @FXML
    TextField resolutionText;
    @FXML
    CheckBox usedCheckBox;
    
    TechnologyProductModel chosenProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        chosenProduct = (TechnologyProductModel)selectedProd;
        resolutionText.setText(chosenProduct.GetResolution());
        usedCheckBox.setSelected(chosenProduct.GetUsed());
    }
    @Override
    public void UpdateProduct(ActionEvent e) throws SQLException {
        if(SetPrice() && SetQuantity() && SetResolitoin()){
            TechnologyProductModel pModel = new TechnologyProductModel(chosenProduct.GetID(), 
                                                                        chosenProduct.GetName(), 
                                                                        chosenProduct.GetCategory(), 
                                                                        quantity, 
                                                                        price, 
                                                                        resolutionText.getText(), 
                                                                        usedCheckBox.isSelected());
            UpdateProductToDB(pModel);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    Boolean SetResolitoin(){
        if (resolutionText.getText().isEmpty()) {
            alert.setTitle("Error");
            alert.setContentText("Resolution: Enter a resolution");
            alert.show();
            return false;
        }
        return true;
    }
}
