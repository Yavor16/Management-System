package Controllers.AdminSceneControllers.AddControllers;

import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddClothesController extends AddProductController {
    @FXML
    TextField sizeText;

    @Override
    public void AddProduct(ActionEvent e) {
        if(areAllInputsValid()){
            ProductModel newModel = createNewProductModel();
            addProductToDB(newModel);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    @Override
    protected Boolean areAllInputsValid() {
        return super.areAllInputsValid() && isSizeTextValid(sizeText);
    }
    @Override
    protected ProductModel createNewProductModel(){
        setVariablesValues();
        ClothesModel pModel = new ClothesModel(getIndexToAddNewProduct(), 
                                                    nameText.getText(), 
                                                    category, 
                                                    quantity, 
                                                    price,
                                                    sizeText.getText());

        return pModel;
    }
    
}
