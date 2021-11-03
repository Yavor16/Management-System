package ManagementProgramProj.Main.src.Controllers.AdminSceneControllers;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ManagementProgramProj.Main.src.DBConnection;
import ManagementProgramProj.Main.src.ProductModel;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminMainSceneController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static ProductModel selectedProduct;
    static ObservableList<ProductModel> listOfProducts;
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
    ComboBox<String> searchComboBox;
    /************************************/
    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        listOfProducts = FXCollections.observableArrayList();
        InitializeComboBox();
        AddDataToTableView();
    }

    public void OpenAddProductScene(ActionEvent e) throws IOException, SQLException{

        stage = new Stage();
        stage.setScene(CreateScene("AddProduct"));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
         
        UpdateTableView();
    }
    public void OpenEditProductScene(ActionEvent e) throws IOException, SQLException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 

        stage = new Stage();
        stage.setScene(CreateScene("EditProductScene"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        UpdateTableView();
    }
    public void DeleteProduct(ActionEvent e) throws SQLException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();  

        DBConnection.DeleteProduct(selectedProduct.GetID());
        listOfProducts.remove(listOfProducts.indexOf(selectedProduct));
        UpdateTableView();
    }
    void UpdateTableView() throws SQLException{
        DBConnection.GetProducts();

        if (searchComboBox.getValue().toString() == "Don't search"){
            productsTableView.setItems(listOfProducts);
        }
        else{
            productsTableView.setItems(GetComboBoxSearchResults());
        }
        productsTableView.refresh();
    }
    Scene CreateScene(String fileName) throws IOException{
        url = new File("ManagementProgramProj/Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        scene = new Scene(root);
        return scene;
    }
    void InitializeComboBox(){
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Fruits", "Vegetables", "Meat", "Alcohol", "Don't search");
        searchComboBox.setItems(comboBoxItems);
    }
    public void SearchUsingComboBox(ActionEvent e) throws SQLException{
        UpdateTableView();

    }
  
    ObservableList<ProductModel> GetComboBoxSearchResults(){
        ObservableList<ProductModel> searchResultList = FXCollections.observableArrayList();

        for (ProductModel prod : listOfProducts){
            if(prod.GetCategory().equals(searchComboBox.getValue().toString()) ){
                searchResultList.add(prod);
            }
        }
        return searchResultList;
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

        //With this two lines we check if no rows are selected the button are goint to be disabled and when we select a row the activad 
        editBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
        deleteBttn.disableProperty().bind(Bindings.isEmpty(productsTableView.getSelectionModel().getSelectedItems()));
    }
}

