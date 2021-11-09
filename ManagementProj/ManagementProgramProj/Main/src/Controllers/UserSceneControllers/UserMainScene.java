package Controllers.UserSceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Controllers.DBConnection;
import Models.ProductModel;
import Models.TechnologyProductModel;
import Models.VegetangleFruitModel;
import Models.ClothesModel;
import Models.ItemToBuyModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class 
UserMainScene implements Initializable{
    /*All products table*/
    @FXML
    TableView<ProductModel> productsTableView;
    @FXML
    TableColumn<ProductModel, String> nameTableCol;
    @FXML
    TableColumn<ProductModel, String> quantityTableCol;
    @FXML
    TableColumn<ProductModel, String> categoryTableCol;
    @FXML
    TableColumn<ProductModel, String> priceTableCol;
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
    Button addToProdTableBttn;
    @FXML
    Button removeFromProdTableBttn;
    @FXML
    Button buyBttn;
    @FXML
    Button closeWindowBttn;
    @FXML
    Label totalMoneyLabel;
    
    static Alert alert = new Alert(AlertType.INFORMATION);
    public ProductModel selectedProduct;
    public static int amountToBuy;
    float totalMoney = 0;

    static ObservableList<ProductModel> listOfProducts;
    ObservableList<ItemToBuyModel>listOfProductsToBuy;

    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        listOfProducts = FXCollections.observableArrayList();
        listOfProductsToBuy = FXCollections.observableArrayList();

        totalMoneyLabel.setText("0.0");
        try {
            InitializeTableView();
            System.out.println(listOfProducts);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        InitializeBuyTableView();
        removeFromProdTableBttn.disableProperty().bind(Bindings.isEmpty(buyProductsTableView.getSelectionModel().getSelectedItems()));
        addToProdTableBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
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
                UpdateProductsTable();   
               
            }
        }
    }
    void InitializeTableView() throws SQLException{
        for(var prod : DBConnection.product.entrySet()){
            listOfProducts.add(prod.getValue());
        }

        nameTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetName()));
        categoryTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetCategory()));
        quantityTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetQuantity())));
        priceTableCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetPrice())));
      
        productsTableView.setItems(listOfProducts);
    }
    void InitializeBuyTableView(){
        
        buyProdNameCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetProduct().GetName()));
        buyProdQuantityCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetQuantity())));
        buyProdPriceCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice())));
        buyProdAmountCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice() * d.getValue().GetAmount())));
   
        buyProductsTableView.setItems(listOfProductsToBuy);
    }
    public void AddItemToProductTable(ActionEvent e) throws IOException{
        AddProdToTable();
    }
    void AddToBuyListFromBasket(){
        if (amountToBuy > 0) {

            int indexOfIteminList = listOfProducts.indexOf(selectedProduct); 
            ProductModel chosenItemFromList = listOfProducts.get(indexOfIteminList);
           
            if(chosenItemFromList.GetQuantity() - amountToBuy == 0){
                listOfProducts.remove(indexOfIteminList);
            }else{
                listOfProducts.get(indexOfIteminList).SetQuantity(chosenItemFromList.GetQuantity() - amountToBuy);    
            }
            UpdateBuyProductsTable();
            UpdatePriceLabel(); 
        }
    }
    public void AddProdToTable() throws IOException{
        CreateAmountScene();
        
        if (amountToBuy > 0) {
            selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 
           
            int indexOfIteminList = listOfProducts.indexOf(selectedProduct); 
            ProductModel chosenItemFromList = listOfProducts.get(indexOfIteminList);
           
            if(chosenItemFromList.GetQuantity() - amountToBuy < 0){
                CallAlert("Not enough items", "Error");
            }
            else if(chosenItemFromList.GetQuantity() - amountToBuy == 0){
                
                CheckAndAddProductToList(indexOfIteminList, chosenItemFromList, true);
                
                listOfProducts.remove(indexOfIteminList);
                UpdateBuyProductsTable();
                UpdateProductsTable();
            }
            else{
                CheckAndAddProductToList( indexOfIteminList, chosenItemFromList, true);
                UpdateBuyProductsTable();
                UpdateProductsTable();
            }
           
        } 
    }
    void CheckAndAddProductToList(int indexOfIteminList, ProductModel chosenItemFromList, Boolean addIfContains){
        ItemToBuyModel newProdToBuy = new ItemToBuyModel(new ProductModel(selectedProduct.GetID(),
                                                                selectedProduct.GetName(), 
                                                                selectedProduct.GetCategory(), 
                                                                amountToBuy, 
                                                                selectedProduct.GetPrice()), 
                                                                amountToBuy); 
        if(DoesListContainsItem(newProdToBuy) && addIfContains){
            listOfProductsToBuy.forEach(x->{
                if(x.GetProduct().GetName() == newProdToBuy.GetProduct().GetName()){
                    x.SetAmount(x.GetAmount() + amountToBuy);
                    x.GetProduct().SetQuantity(x.GetProduct().GetQuantity() + amountToBuy);
                }
            });
            listOfProducts.get(indexOfIteminList).SetQuantity(chosenItemFromList.GetQuantity() - amountToBuy);
        }
        else{
            listOfProductsToBuy.add(newProdToBuy);
            listOfProducts.get(indexOfIteminList).SetQuantity(chosenItemFromList.GetQuantity() - amountToBuy);
        } 
        UpdatePriceLabel();
    }
    void CheckAndAddProductToBuyList(int  indexOfIteminList, ItemToBuyModel chosenItemFromBuyList){
    
        ProductModel newProdToList = new ProductModel(chosenItemFromBuyList.GetProduct().GetID(),
                                                    chosenItemFromBuyList.GetProduct().GetName(), 
                                                    chosenItemFromBuyList.GetProduct().GetCategory(), 
                                                    amountToBuy, 
                                                    chosenItemFromBuyList.GetProduct().GetPrice()); 

        if(DoesBuyListContainsItem(newProdToList)){
            listOfProducts.forEach(x->{
                if(x.GetName() == newProdToList.GetName()){
                    x.SetQuantity(x.GetQuantity() + amountToBuy);
                    
                }
            });
            
            listOfProductsToBuy.get(indexOfIteminList).GetProduct().SetQuantity(chosenItemFromBuyList.GetProduct().GetQuantity() - amountToBuy);
            listOfProductsToBuy.get(indexOfIteminList).SetAmount(chosenItemFromBuyList.GetAmount() - amountToBuy);
        }
        else{   
            listOfProducts.add(newProdToList);
            listOfProductsToBuy.get(indexOfIteminList).GetProduct().SetQuantity(chosenItemFromBuyList.GetProduct().GetQuantity() - amountToBuy);
        }
    }
    Boolean DoesListContainsItem(ItemToBuyModel productModeBuyModel){
        for(int i = 0; i < listOfProductsToBuy.size(); i++){
            if (listOfProductsToBuy.get(i).GetProduct().GetName().equals(productModeBuyModel.GetProduct().GetName())){
                return true;
            }
        }
        return false;
    }
    Boolean DoesBuyListContainsItem(ProductModel productModeBuyModel){
        for (int i = 0; i < listOfProducts.size(); i++) {
            if (listOfProducts.get(i).GetName().equals(productModeBuyModel.GetName())) {
                return true;
            }
        }
        return false;
    }
    void CreateAmountScene() throws IOException{
        URL url = new File("Main/src/Scenes/UserScenes/AmounScene.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        amountToBuy = 0;
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
    void UpdateBuyProductsTable(){
        buyProductsTableView.setItems(listOfProductsToBuy);
        buyProductsTableView.refresh();
    }
    void UpdateProductsTable(){
        productsTableView.setItems(listOfProducts);
        productsTableView.refresh();
    }
    /*
    public void RemoveItemFromProductTable(ActionEvent e) throws IOException{
        CreateAmountScene();
        
        if (amountToBuy > 0) {
            
            ItemToBuyModel selectedProductFromBuyList = buyProductsTableView.getSelectionModel().getSelectedItem(); 
            
            int indexOfIteminList = listOfProductsToBuy.indexOf(selectedProductFromBuyList); 
            ItemToBuyModel chosenItemFromList = listOfProductsToBuy.get(indexOfIteminList);
            
            if(chosenItemFromList.GetProduct().GetQuantity() - amountToBuy < 0){
                CallAlert("Not enough items", "Error");
            }
            else if(chosenItemFromList.GetProduct().GetQuantity() - amountToBuy == 0){
                CheckAndAddProductToBuyList(indexOfIteminList, chosenItemFromList);
                
                listOfProductsToBuy.remove(indexOfIteminList);
                UpdateBuyProductsTable();
                //UpdateProductsTable();
            }
            else{
                CheckAndAddProductToBuyList(indexOfIteminList, chosenItemFromList);
                UpdateBuyProductsTable();
                //UpdateProductsTable();
            }
            UpdatePriceLabel();
        }
    }
    */
    public void BuyProducts(ActionEvent e) throws SQLException, IOException{
      
        DBConnection.GetProducts();
        
        ItemToBuyModel prod;
        ProductModel prodFromDB;
        
        int listSize = listOfProductsToBuy.size();
        
        for (int i = 0; i < listSize; i++) {   
            prod = listOfProductsToBuy.get(i);
            prodFromDB = DBConnection.product.get(prod.GetProduct().GetID());
            
            if (prodFromDB.GetQuantity() - prod.GetProduct().GetQuantity() > 0) {
               
                //prodFromDB.SetQuantity(prodFromDB.GetQuantity() - prod.GetProduct().GetQuantity());
                //newProD.SetMainCategory(prodFromDB.GetMainCategory());
                DBConnection.product.get(prod.GetProduct().GetID()).SetQuantity(prodFromDB.GetQuantity() - prod.GetProduct().GetQuantity());
                DBConnection.UpdateProduct(DBConnection.product.get(prod.GetProduct().GetID()));
            }
            else{
                DBConnection.DeleteProduct(prod.GetProduct().GetID());
            }
        }
        CallAlert("Order is completed!", "Success");

        listOfProductsToBuy.clear();
        UpdateBuyProductsTable();
        UpdatePriceLabel();
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    public void CloseWindow(ActionEvent e) throws IOException{
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    void UpdatePriceLabel(){
        totalMoney = 0;
        for (int i = 0; i < listOfProductsToBuy.size(); i++) {
            totalMoney+=listOfProductsToBuy.get(i).GetProduct().GetPrice() * listOfProductsToBuy.get(i).GetProduct().GetQuantity();
        }
        totalMoneyLabel.setText(String.valueOf(totalMoney).toString());
    }
    void CallAlert(String message, String title){
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    ProductModel CreateProd(ProductModel prod){
        if (prod instanceof TechnologyProductModel) {
            TechnologyProductModel tm = (TechnologyProductModel)prod;
            System.out.println(2222);
            return tm;
        }
        else if (prod.GetMainCategory().equals("Food")) {
            VegetangleFruitModel vm = (VegetangleFruitModel)prod;
            return vm;
        }
        ClothesModel cm = (ClothesModel)prod;
        return cm;
    
    }
}
