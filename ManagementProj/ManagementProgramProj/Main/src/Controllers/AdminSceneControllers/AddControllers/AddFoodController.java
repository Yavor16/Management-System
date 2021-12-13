package Controllers.AdminSceneControllers.AddControllers;

import Models.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddFoodController extends AddProductController{
    
    @Override
    public void AddProduct(ActionEvent e){
        if(areAllInputsValid()){
            ProductModel newModel = createNewProductModel();
            addProductToDB(newModel);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }        
    @Override
    protected ProductModel createNewProductModel() {
        setVariablesValues();
        VegetangleFruitModel pModel = new VegetangleFruitModel(getIndexToAddNewProduct(), 
                                                                        nameText.getText(), 
                                                                        category, 
                                                                        quantity, 
                                                                        price);
                                                                        
        return pModel;
    }
}
