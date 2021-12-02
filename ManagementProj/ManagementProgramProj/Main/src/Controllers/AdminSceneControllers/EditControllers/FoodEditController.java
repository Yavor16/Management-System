package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ProductModel;
import Models.VegetangleFruitModel;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class FoodEditController extends EditProductController{
    
    VegetangleFruitModel chosenProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        chosenProduct = (VegetangleFruitModel)selectedProd;
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
    protected ProductModel createNewModelForUpdate() {
        VegetangleFruitModel pModel = new VegetangleFruitModel(chosenProduct.GetID(), 
                                                                chosenProduct.GetName(), 
                                                                chosenProduct.GetCategory(), 
                                                                quantity, 
                                                                price);
        return pModel;
    }
}
