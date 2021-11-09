package Controllers.UserSceneControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Models.ItemToBuyModel;
import Models.ProductModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BuyController extends UserMainScene{
    @FXML
    Button buyBttn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listOfProducts = FXCollections.observableArrayList();
        listOfProductsToBuy = FXCollections.observableArrayList();

        for(var prod : DBConnection.product.entrySet()){
            listOfProducts.add(prod.getValue());
        }
        totalMoneyLabel.setText("0.0");
        
        InitializeBuyTableView();
        buyBttn.disableProperty().bind(Bindings.isEmpty(listOfProductsToBuy));
       
        if(!MainScene.basketItems.isEmpty()){
            listOfProductsToBuy.clear();
            for (var productModel : MainScene.basketItems.entrySet()) {
                listOfProductsToBuy.add(new ItemToBuyModel( 
                                        new ProductModel(productModel.getKey().GetID(), 
                                        productModel.getKey().GetName(), 
                                        productModel.getKey().GetCategory(), 
                                        productModel.getValue(), 
                                        productModel.getKey().GetPrice()),
                                        productModel.getValue()) );
                              
                amountToBuy = productModel.getValue();
                selectedProduct = productModel.getKey();
                
                AddToBuyListFromBasket();
            }
        }
    }
}
