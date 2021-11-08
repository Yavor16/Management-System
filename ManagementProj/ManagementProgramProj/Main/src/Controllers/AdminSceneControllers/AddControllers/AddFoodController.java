package Controllers.AdminSceneControllers.AddControllers;

import java.sql.SQLException;

import Models.VegetangleFruitModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddFoodController extends AddProductController{
    
    @Override
    public void AddProduct(ActionEvent e) throws SQLException {
        if(SetName() && SetQuantity() && SetPrice()){
            VegetangleFruitModel pModel = new VegetangleFruitModel(GetLastIndex(), 
                                                                        nameText.getText(), 
                                                                        category, 
                                                                        quantity, 
                                                                        price);
            AddProductToDB(pModel);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }        
    
}
