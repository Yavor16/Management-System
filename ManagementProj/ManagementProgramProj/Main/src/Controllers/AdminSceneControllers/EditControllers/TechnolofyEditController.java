package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ProductModel;
import Models.TechnologyProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public void UpdateProduct(ActionEvent e) {
        setVariablesValues();
        if(areAllInputsValid()){
            ProductModel newModel = createNewModelForUpdate();
            updateProduct(newModel);

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    @Override
    protected java.lang.Boolean areAllInputsValid() {
        return super.areAllInputsValid() && isResolutionTextValid(resolutionText);
    }
    @Override
    protected ProductModel createNewModelForUpdate() {
        TechnologyProductModel pModel = new TechnologyProductModel(chosenProduct.GetID(), 
                                                                        chosenProduct.GetName(), 
                                                                        chosenProduct.GetCategory(), 
                                                                        quantity, 
                                                                        price, 
                                                                        resolutionText.getText(), 
                                                                        usedCheckBox.isSelected());
        return pModel;
    }
}
