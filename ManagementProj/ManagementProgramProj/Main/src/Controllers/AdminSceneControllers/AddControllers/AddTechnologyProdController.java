package Controllers.AdminSceneControllers.AddControllers;

import javafx.scene.control.*;
import Models.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

public class AddTechnologyProdController extends AddProductController{
    @FXML
    TextField resolutionText;
    @FXML
    CheckBox usedCheckBox;
    
    @Override
    public void AddProduct(ActionEvent e){
        if(areAllInputsValid()){
            ProductModel newProduct = createNewProductModel();
            addProductToDB(newProduct);
            
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }        
    @Override
    protected Boolean areAllInputsValid() {
        return super.areAllInputsValid() && isResolutionTextValid(resolutionText);
    }
    @Override
    protected ProductModel createNewProductModel(){
        setVariablesValues();
        TechnologyProductModel pModel = new TechnologyProductModel(getIndexToAddNewProduct(), 
                                                                        nameText.getText(), 
                                                                        category, 
                                                                        quantity, 
                                                                        price, 
                                                                        resolutionText.getText(), 
                                                                        usedCheckBox.isSelected());

        return pModel;
    }
}
