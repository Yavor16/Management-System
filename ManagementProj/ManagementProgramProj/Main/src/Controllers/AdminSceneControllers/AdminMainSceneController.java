package Controllers.AdminSceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


import Models.ClothesModel;
import Models.ProductModel;
import Models.TechnologyProductModel;
import Models.VegetangleFruitModel;
import Controllers.DBConnection;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AdminMainSceneController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static ProductModel selectedProduct;
    public static ObservableList<ProductModel> listOfProducts;

    //FXML location URL
    URL url;

    /*****  FXML Attribs *****/
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
    /************************************/
    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        listOfProducts = FXCollections.observableArrayList();
        searchComboBox.setValue("All products");
        InitializeComboBox();
        try {
            DBConnection.GetProducts();
            UpdateTableView();
            AddDataToTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OpenAddProductScene(ActionEvent e) throws IOException, SQLException{

        stage = new Stage();
        stage.setScene(CreateScene("ChooseCategoryScene"));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        UpdateTableView();
    }
    public void OpenEditProductScene(ActionEvent e) throws IOException, SQLException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 

        stage = new Stage();
        DBConnection.GetSpecificProduct(selectedProduct.GetID());
        String mainCat = DBConnection.wantedProd.GetMainCategory(); 
        
        if (mainCat.equals("Technology")) {
            TechnologyProductModel tm = (TechnologyProductModel)DBConnection.wantedProd; 
            selectedProduct = tm;
            stage.setScene(CreateScene("TechnologyEditScene"));
        }
        else if (mainCat.equals("Food")) {
            VegetangleFruitModel fm = (VegetangleFruitModel)DBConnection.wantedProd;
            selectedProduct= fm;
            stage.setScene(CreateScene("FoodEditScene"));
        }
        else if(mainCat.equals("Clothes")){
            ClothesModel cm = (ClothesModel)DBConnection.wantedProd;
            selectedProduct = cm;
            stage.setScene(CreateScene("ClothesEditScene"));
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        UpdateTableView();
    }
    public void DeleteProduct(ActionEvent e) throws SQLException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();  

        Alert confirmDiag = new Alert(AlertType.CONFIRMATION);
        confirmDiag.setTitle("Delete");
        confirmDiag.setHeaderText("Are you sure you want to delete " + selectedProduct.GetName());
        
        Optional<ButtonType> result = confirmDiag.showAndWait();

        if (result.get() == ButtonType.OK) {   
            DBConnection.DeleteProduct(selectedProduct.GetID());
            listOfProducts.remove(listOfProducts.indexOf(selectedProduct));
            UpdateTableView();
        }
        else{
            confirmDiag.close();
        }
    }
    private void UpdateTableView() throws SQLException{
        DBConnection.GetProducts();
        if (!searchComboBox.getSelectionModel().isEmpty()){

            if (searchComboBox.getValue().toString() == "All products" ){
                productsTableView.setItems(listOfProducts);
            }
            else{
                productsTableView.setItems(GetComboBoxSearchResults());
            }
            productsTableView.refresh();
        }
    }
    Scene CreateScene(String fileName) throws IOException{
        url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        
        scene = new Scene(root);
        return scene;
    }
    void InitializeComboBox(){
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Technology", "Food", "Clothes", "All products");
        searchComboBox.setItems(comboBoxItems);
    }
    public void SearchUsingComboBox(ActionEvent e) throws SQLException{
        UpdateTableView();
    }
    public void CloseWindow(ActionEvent e) throws IOException{
        url = new File("Main/src/Scenes/OpenScene.fxml").toURI().toURL();
        
        root = FXMLLoader.load(url);

        stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    ObservableList<ProductModel> GetComboBoxSearchResults(){
        ObservableList<ProductModel> searchResultList = FXCollections.observableArrayList();

        for (ProductModel prod : listOfProducts){
            if(prod.GetMainCategory().equals(searchComboBox.getValue().toString()) ){
                searchResultList.add(prod);
            }
        }
        return searchResultList;
    }
    public static void SetListOfProducts() throws SQLException{
        DBConnection.GetProducts();
        listOfProducts.clear();
        for(var prod : DBConnection.product.entrySet()){
            listOfProducts.add(prod.getValue());
        }
    }
    void AddDataToTableView() throws SQLException{
        SetListOfProducts();

        nameTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetName()));
        categoryTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(d.getValue().GetCategory()));
        quantityTableCol.setCellValueFactory(d->new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetQuantity())));
        priceTableCol.setCellValueFactory(d-> new ReadOnlyStringWrapper(String.valueOf(d.getValue().GetPrice())));
        
        productsTableView.setItems(listOfProducts);

        //With this two lines we check if no rows are selected the button are goint to be disabled and when we select a row the activad 
        editBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
        deleteBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
    }
}

