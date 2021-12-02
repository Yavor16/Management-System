package Controllers.UserSceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;

import static Controllers.DataBaseFunctions.ProductFunctionality.UpdateProductToDB.*;
import static Controllers.DataBaseFunctions.ProductFunctionality.DeleteProductFromDB.*;
import static Controllers.ManageBills.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.fxml.*;
import Models.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
public class BuyController implements Initializable{

    /*Product to buy table*/
    @FXML
    TableView<ItemToBuyModel> buyProductsTableView;
    @FXML
    TableColumn<ItemToBuyModel, String> buyProdNameCol;
    @FXML
    TableColumn<ItemToBuyModel, String> buyProdQuantityCol;
    @FXML
    TableColumn<ItemToBuyModel, String> buyProdPriceCol;
    @FXML
    TableColumn<ItemToBuyModel, String> buyProdAmountCol;
    /*Buttons */
    @FXML
    Button buyBttn;
    @FXML
    Button closeWindowBttn;
    @FXML
    Label totalMoneyLabel;


    private static Alert alert = new Alert(AlertType.INFORMATION);
    public ProductModel selectedProduct;
    public static int amountToBuy;
    
    private ObservableList<ItemToBuyModel>productsToBuy;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productsToBuy = FXCollections.observableArrayList();
     
        initializeTableColumns();
        buyBttn.disableProperty().bind(Bindings.isEmpty(productsToBuy));
       
        if(!MainScene.basketItems.isEmpty()){
            addProductsToBuyList();
        }
        updatePriceLabel();
    }
    private void addProductsToBuyList(){
        productsToBuy.clear();
        for (var productModel : MainScene.basketItems.entrySet()) {
            productsToBuy.add(new ItemToBuyModel( 
                                    new ProductModel(productModel.getKey().GetID(), 
                                    productModel.getKey().GetName(), 
                                    productModel.getKey().GetCategory(), 
                                    productModel.getValue(), 
                                    productModel.getKey().GetPrice()),
                                    productModel.getValue()) );
                          
            amountToBuy = productModel.getValue();
            selectedProduct = productModel.getKey();
        
        }
    }
    private void updatePriceLabel(){
        float totalMoney = 0;

        for (int i = 0; i < productsToBuy.size(); i++) {
            ProductModel currentModel = productsToBuy.get(i).GetProduct(); 
            totalMoney+=currentModel.GetPrice() * currentModel.GetQuantity();
        }

        totalMoneyLabel.setText(String.valueOf(totalMoney).toString());
    }
    private void initializeTableColumns(){
        
        buyProdNameCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetProduct().GetName()));
        buyProdQuantityCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetQuantity())));
        buyProdPriceCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice())));
        buyProdAmountCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice() * d.getValue().GetAmount())));
   
        buyProductsTableView.setItems(productsToBuy);
    }
    public void BuyProducts(ActionEvent e) {
      
        AllProducts.getProducts();
        
        ItemToBuyModel currentProd;
        ProductModel productFromDataBase;
        
        int listSize = productsToBuy.size();
        
        for (int i = 0; i < listSize; i++) {   
            currentProd = productsToBuy.get(i);
            productFromDataBase = AllProducts.products.get(currentProd.GetProduct().GetID());
            
            int newProductQuantity = productFromDataBase.GetQuantity() - currentProd.GetProduct().GetQuantity(); 

            if (newProductQuantity > 0) {    
                int productId = currentProd.GetProduct().GetID();

                AllProducts.products.get(productId).SetQuantity(newProductQuantity);
                updateProductToDB(productFromDataBase);
            } else{
                deleteProductFromDB(currentProd.GetProduct().GetID());
            }
        }
        addNewBill(productsToBuy);
        
        callAlert("Order is completed!", "Success");

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    private void callAlert(String message, String title){
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.show();
    }
    public void CloseWindow(ActionEvent e) throws IOException{
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
