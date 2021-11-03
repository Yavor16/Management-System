package ManagementProgramProj.Main.src.Controllers.UserSceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import ManagementProgramProj.Main.src.DBConnection;
import ManagementProgramProj.Main.src.ProductModel;
import ManagementProgramProj.Main.src.Models.ItemToBuyModel;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UserMainScene implements Initializable{
    
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


    public ProductModel selectedProduct;
    public static int amountToBuy;
    ItemToBuyModel newProdToBuy;
    
    static ObservableList<ProductModel> listOfProducts;
    ObservableList<ItemToBuyModel>listOfProductsToBuy;
    
    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        listOfProducts = FXCollections.observableArrayList();
        listOfProductsToBuy = FXCollections.observableArrayList();
        
        AddDataToTableView();
        AddDataToBuyTableView();
        
        removeFromProdTableBttn.disableProperty().bind(Bindings.isEmpty(buyProductsTableView.getSelectionModel().getSelectedItems()));
        addToProdTableBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
    }
    void AddDataToTableView(){
        for(var prod : DBConnection.product.entrySet()){
            listOfProducts.add(prod.getValue());
        }

        nameTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetName()));
        categoryTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetCategory()));
        quantityTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetQuantity())));
        priceTableCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetPrice())));
      
        productsTableView.setItems(listOfProducts);
    }
    void AddDataToBuyTableView(){
        
        buyProdNameCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetProduct().GetName()));
        buyProdQuantityCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetQuantity())));
        buyProdPriceCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice())));
        buyProdAmountCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetProduct().GetPrice() * d.getValue().GetAmount())));
   
        buyProductsTableView.setItems(listOfProductsToBuy);
    }
    public void AddItemToProductTable(ActionEvent e) throws IOException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 
        CreateAmountScene();
        
        int indexOfIteminList = listOfProducts.indexOf(selectedProduct); 
        ProductModel chosenItemFromList = listOfProducts.get(indexOfIteminList);

        if(chosenItemFromList.GetQuantity() - amountToBuy < 0){
            JOptionPane.showMessageDialog(null, "Not enough items");
        }
        else if(chosenItemFromList.GetQuantity() - amountToBuy == 0){

            CheckAndAddProductToList(indexOfIteminList, chosenItemFromList);

            listOfProducts.remove(indexOfIteminList);
            UpdateBuyProductsTable();
            UpdateProductsTable();
        }
        else{
            CheckAndAddProductToList( indexOfIteminList, chosenItemFromList);
            UpdateBuyProductsTable();
            UpdateProductsTable();
        }
    }
    void CheckAndAddProductToList(int indexOfIteminList, ProductModel chosenItemFromList){
        newProdToBuy = new ItemToBuyModel(new ProductModel(selectedProduct.GetID(),
                                                                selectedProduct.GetName(), 
                                                                selectedProduct.GetCategory(), 
                                                                amountToBuy, 
                                                                selectedProduct.GetPrice()), 
                                                                amountToBuy); 

            if(DoesListContainsItem(newProdToBuy)){
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

    }
    Boolean DoesListContainsItem(ItemToBuyModel productModeBuyModel){
        for(int i = 0; i < listOfProductsToBuy.size(); i++){
            if (listOfProductsToBuy.get(i).GetProduct().GetName().equals(productModeBuyModel.GetProduct().GetName())){
                return true;
            }
        }
        return false;
    }
    void CreateAmountScene() throws IOException{
        URL url = new File("ManagementProgramProj/Main/src/Scenes/UserScenes/AmounScene.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
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
    
    public void RemoveItemFromProductTable(ActionEvent e){
        
    }
    public void BuyProducts(ActionEvent e){
    }
    public void CloseWindow(ActionEvent e) throws IOException{
        URL url = new File("ManagementProgramProj/Main/src/Scenes/OpenScene.fxml").toURI().toURL();
        
        Parent root = FXMLLoader.load(url);

        Stage stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
