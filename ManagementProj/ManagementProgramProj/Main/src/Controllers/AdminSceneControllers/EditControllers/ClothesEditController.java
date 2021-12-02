package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ClothesModel;
import Models.ProductModel;
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
    public void UpdateProduct(ActionEvent e) {
        if(areAllInputsValid()){

            ProductModel newModel = createNewModelForUpdate();
            updateProduct(newModel);

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
        
    }
    @Override
    protected java.lang.Boolean areAllInputsValid() {
        return super.areAllInputsValid() && isSizeTextValid(sizeText);
    }
    @Override
    protected ProductModel createNewModelForUpdate() {
        ClothesModel pModel = new ClothesModel(chosenProduct.GetID(), 
                                                chosenProduct.GetName(), 
                                                chosenProduct.GetCategory(), 
                                                quantity, 
                                                price, 
                                                sizeText.getText());
        return pModel;
    }
}
