package Controllers.AdminSceneControllers;

import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import java.util.*;

import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;
import Controllers.DataBaseFunctions.ProductFunctionality.GetProduct;

import java.net.*;
import java.io.*;
import Models.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import static Controllers.DataBaseFunctions.ProductFunctionality.DeleteProductFromDB.*;

public class AdminMainSceneController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static ProductModel selectedProduct;
    public static ObservableList<ProductModel> products ;
    public static String mainCat;
    private static Alert alert = new Alert(AlertType.ERROR);
    private URL url;

    @FXML
    Button openAddScenetBttn;
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
    @FXML
    Button editBttn;
    @FXML
    Button deleteBttn;
    @FXML
    Button closeWindowBttn;
    @FXML
    ComboBox<String> searchComboBox;

    @Override 
    public void initialize(URL url, ResourceBundle rb ){

        products = FXCollections.observableArrayList();
        searchComboBox.setValue("All products");
        
        initializeComboBox();
        updateTableView();
        initializeTableColumns();
        bindButtonsToTable();
       
    }
    private void updateTableView() {
        refreshProducts();
        if (!searchComboBox.getSelectionModel().isEmpty()){

            if (searchComboBox.getValue().toString() == "All products" ){
                productsTableView.setItems(products);
            } else{
                productsTableView.setItems(getComboBoxSearchResults());
            }
            productsTableView.refresh();
        }
    }
    private static void refreshProducts() {
        
        products = FXCollections.observableArrayList();
        AllProducts.getProducts();
        products.clear();
        for(var prod : AllProducts.products.entrySet()){
            products.add(prod.getValue());
        }
    }
    private ObservableList<ProductModel> getComboBoxSearchResults() {
        ObservableList<ProductModel> searchResults = FXCollections.observableArrayList();
        
        for (ProductModel prod : products){
            String comboBoxSelectedItem = searchComboBox.getValue().toString();

            if(prod.GetMainCategory().equals(comboBoxSelectedItem) ){
                searchResults.add(prod);
            }
        }
        return searchResults;
    }
    private void initializeComboBox() {
        getChooseCategoryController();

        ObservableList<String> comboBoxItems = FXCollections.observableArrayList(ChooseCategoryController.mainCategoryNames);
        comboBoxItems.add("All products");

        searchComboBox.setItems(comboBoxItems);
    }
    private void getChooseCategoryController() {
        try {
            URL  url = new File("Main/src/Scenes/AdminScenes/ChooseCategoryScene.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            loader.getController();
        } catch (IOException e) {
            alert.setContentText("Could not open the ChooseCategoryScene scene");
            alert.show();
        }
    }
    private void initializeTableColumns(){

        nameTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetName()));
        categoryTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetCategory()));
        quantityTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetQuantity())));
        priceTableCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetPrice())));
        
        productsTableView.setItems(products);
    }
    private void bindButtonsToTable(){
        editBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
        deleteBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
    }
    public void OpenAddProductScene(ActionEvent e) throws IOException{
        
        stage = new Stage();
        stage.setScene(createScene("ChooseCategoryScene"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        updateTableView();
        
        initializeComboBox();
    }

    public void OpenEditProductScene(ActionEvent e) {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 

        AllProducts.getProducts();
        GetProduct.getSpecificProduct(selectedProduct.GetID());

        chooseAndCreateEditProductScene();
    
        updateTableView();

    }
    private void chooseAndCreateEditProductScene(){
        stage = new Stage();
        mainCat = GetProduct.wantedProd.GetMainCategory(); 
   
        if (mainCat.equals("Technology")) {
            selectedProduct = (TechnologyProductModel)GetProduct.wantedProd; 
            stage.setScene(createScene("TechnologyEditScene"));
        } else if (mainCat.equals("Food")) {
            selectedProduct = (VegetangleFruitModel)GetProduct.wantedProd;
            stage.setScene(createScene("FoodEditScene"));
        } else if(mainCat.equals("Clothes")){
            selectedProduct = (ClothesModel)GetProduct.wantedProd;
            stage.setScene(createScene("ClothesEditScene"));
        } else{
            selectedProduct = (ProductModel)GetProduct.wantedProd;
            stage.setScene(createScene("EditProductScene"));
        }
    
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    private Scene createScene(String fileName) {
        try{
            url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
            root = FXMLLoader.load(url);
            
            scene = new Scene(root);
        } catch (IOException e) {
            alert.setContentText("Cannot open new the " + fileName + "scene");
            alert.show();
        }
        return scene;
    }
    public void DeleteProduct(ActionEvent e) {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();  

        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Are you sure you want to delete " + selectedProduct.GetName());
        
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {   
            deleteProductFromDB(selectedProduct.GetID());
            products.remove(products.indexOf(selectedProduct));
            updateTableView();
        } else{
            alert.close();
        }
    }
    
    public void SearchUsingComboBox(ActionEvent e) {
        updateTableView();
    }
    public void CloseWindow(ActionEvent e) {

        try {
            url = new File("Main/src/Scenes/OpenScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
        } catch (IOException exe) {
            alert.setContentText("Cannot open the scene");
            alert.show();
            return;
        } 
    
        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

