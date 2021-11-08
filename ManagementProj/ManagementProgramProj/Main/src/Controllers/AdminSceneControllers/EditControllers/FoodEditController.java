package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    public void UpdateProduct(ActionEvent e) throws SQLException {
        if(SetPrice() && SetQuantity()){
            VegetangleFruitModel pModel = new VegetangleFruitModel(chosenProduct.GetID(), 
            chosenProduct.GetName(), 
            chosenProduct.GetCategory(), 
            quantity, 
            price);
            
            UpdateProductToDB(pModel);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
        
    }
    
}
