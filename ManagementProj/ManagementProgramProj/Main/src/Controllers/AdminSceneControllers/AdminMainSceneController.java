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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdminMainSceneController implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static ProductModel selectedProduct;
    URL url;
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
    
    static ObservableList<ProductModel> listOfProducts;
    
    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        listOfProducts = FXCollections.observableArrayList();

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

    public void OpenAddProductScene(ActionEvent e) throws IOException, SQLException{
          
        url = new File("ManagementProgramProj/Main/src/Scenes/AdminScenes/AddProduct.fxml").toURI().toURL();
        root = FXMLLoader.load(url);

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);

        openAddScenetBttn.setDisable(true);    
        stage.showAndWait();
        openAddScenetBttn.setDisable(false);  
         
        UpdateTableView();
    }
    public void OpenEditProductScene(ActionEvent e) throws IOException, SQLException{
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem(); 

        url = new File("ManagementProgramProj/Main/src/Scenes/AdminScenes/EditProductScene.fxml").toURI().toURL();
        root = FXMLLoader.load(url);

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
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
        productsTableView.setItems(listOfProducts);
        productsTableView.refresh();
    }
}
