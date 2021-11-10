package Controllers.UserSceneControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Models.ItemToBuyModel;
import Models.ProductModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
     
    static Alert alert = new Alert(AlertType.INFORMATION);
    public ProductModel selectedProduct;
    public static int amountToBuy;
    
    ObservableList<ItemToBuyModel>listOfProductsToBuy;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listOfProductsToBuy = FXCollections.observableArrayList();
     
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
            
            }
        }
        UpdatePriceLabel();
    }
    void InitializeBuyTableView(){
        
        buyProdNameCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetProduct().GetName()));
        buyProdQuantityCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetQuantity())));
        buyProdPriceCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice())));
        buyProdAmountCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice() * d.getValue().GetAmount())));
   
        buyProductsTableView.setItems(listOfProductsToBuy);
    }
    public void BuyProducts(ActionEvent e) throws SQLException, IOException{
      
        DBConnection.GetProducts();
        
        ItemToBuyModel prod;
        ProductModel prodFromDB;
        
        int listSize = listOfProductsToBuy.size();
        
        for (int i = 0; i < listSize; i++) {   
            prod = listOfProductsToBuy.get(i);
            prodFromDB = DBConnection.product.get(prod.GetProduct().GetID());
            
            if (prodFromDB.GetQuantity() - prod.GetProduct().GetQuantity() > 0) {       
                DBConnection.product.get(prod.GetProduct().GetID()).SetQuantity(prodFromDB.GetQuantity() - prod.GetProduct().GetQuantity());
                DBConnection.UpdateProduct(DBConnection.product.get(prod.GetProduct().GetID()));
            } else{
                DBConnection.DeleteProduct(prod.GetProduct().GetID());
            }
        }
        AddNewBill(listOfProductsToBuy);
        
        CallAlert("Order is completed!", "Success");

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    public void CloseWindow(ActionEvent e) throws IOException{
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    void CallAlert(String message, String title){
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    void AddNewBill(ObservableList<ItemToBuyModel>listOfProductsToBuy){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();  
        
        VBox vb = new VBox();
        vb.prefWidth(247);
        Label lb = new Label();
        lb.setText(dtf.format(now));
        lb.setTextFill(Color.WHITE);

        lb.prefHeight(50);
        lb.prefWidth(247);
        vb.getChildren().add(lb);
        
        for (ItemToBuyModel itemToBuyModel : listOfProductsToBuy) {
            TextField tf = new TextField();
            tf.setText(itemToBuyModel.GetProduct().GetName() + "=" + itemToBuyModel.GetAmount() +"x"+ itemToBuyModel.GetProduct().GetPrice());
            tf.setEditable(false);
            vb.getChildren().add(tf);
        }
        if (MainScene.queueForBill.size() >= 10) {
            MainScene.queueForBill.remove();
            MainScene.queueForBill.add(vb);
        } else{
            MainScene.queueForBill.add(vb);
        }
    }
    void UpdatePriceLabel(){
        float totalMoney = 0;
        for (int i = 0; i < listOfProductsToBuy.size(); i++) {
            totalMoney+=listOfProductsToBuy.get(i).GetProduct().GetPrice() * listOfProductsToBuy.get(i).GetProduct().GetQuantity();
        }
        totalMoneyLabel.setText(String.valueOf(totalMoney).toString());
    }
}
