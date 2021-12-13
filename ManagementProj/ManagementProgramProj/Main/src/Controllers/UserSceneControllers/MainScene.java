package Controllers.UserSceneControllers;

import java.net.URL;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.transformation.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.stage.*;
import Controllers.*;
import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;
import javafx.fxml.*;
import java.util.*;
import java.io.*;

public class MainScene implements Initializable{
    
    @FXML 
    TextField searchTextField;
    @FXML
    Button backBttn;
    @FXML
    Button historyBttn;
    @FXML
    Button basketBttn;
    @FXML
    GridPane productsGrid;

    public static Map<ProductModel, Integer> basketItems;
    public static boolean isOrderedComplete = false;
    
    private ProductInfoController pInfoController;
    private Alert alert = new Alert(AlertType.ERROR);
    private URL url;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<ProductModel> pm; 
    private SortedList<ProductModel> sortedResultFromSearch;
    private FilteredList<ProductModel> filteredResultsFromSearch;
    
    public void initialize(URL url, ResourceBundle rb ){
        basketItems = new HashMap<>();
        
        productsGrid.setHgap(180);
        productsGrid.setVgap(10);

        SaveAndLoadHistory.LoadFile();
        
        searchProductFromTable();
    }   
    private void searchProductFromTable(){
        updateDataStructures();
        updateTilePane();
                
        searchTextField.textProperty().addListener((pm, oldValue, newValue) -> {
            filteredResultsFromSearch.setPredicate(prod ->{
            if(newValue == null || newValue.isEmpty() || newValue.equals(null)){
                return true;
            }
            
            return getResultsFromSearch(prod, newValue);  
        });
            sortedResultFromSearch = new SortedList<>(filteredResultsFromSearch);
            
            updateTilePane(); 
        });
    }
    private void updateDataStructures(){
        AllProducts.getProducts();
        
        pm = FXCollections.observableArrayList(AllProducts.products.values());
        filteredResultsFromSearch = new FilteredList<>(pm, b->true);
        sortedResultFromSearch = new SortedList<>(filteredResultsFromSearch);
    }
    private Boolean getResultsFromSearch(ProductModel prod, String newValue){
        String searchValue = newValue.toLowerCase();

        if(prod.GetName().toLowerCase().indexOf(searchValue)!= -1 ){
            return true;
        } else if(prod.GetCategory().toLowerCase().indexOf(searchValue) != -1){
            return true;
        } else if(prod.GetMainCategory().toLowerCase().indexOf(searchValue) != -1){
            return true;
        } else{
            return false;
        }
    }
    public void OpenOpenScene(ActionEvent e ) {
        SaveAndLoadHistory.SaveFile();
    
        try {
            url = new File("Main/src/Views/OpenScene.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            
            stage = (Stage)((Node )e.getSource()).getScene().getWindow();
            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exe) {
            alert.setContentText("Could not find the file");
            alert.show();
        }
    }
    public void ShowHistory(ActionEvent e ) {
        createScene("UserScenes/HistoryScene");
    }
    public void OpenBasket(ActionEvent e) {
        createScene("UserScenes/BoxScene");

        if (isOrderedComplete) {
            basketItems.clear();
            productsGrid.getChildren().clear();
            
            updateDataStructures();
            updateTilePane();
            
            isOrderedComplete = false;
        }
    }
    private void createScene(String name) {
        try {
            url = new File("Main/src/Views/" + name +".fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            
            scene = new Scene(root);
            stage = new Stage();
            
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            alert.setContentText("Could not find the file");
            alert.show();
        }
    }
    private void updateTilePane() {
        AllProducts.getProducts();
        productsGrid.getChildren().clear();

        int row = 0, col=0;
        int amountOfBoxed;

        amountOfBoxed = sortedResultFromSearch.size();
        
        for (int i = 0; i < amountOfBoxed; i++) {
            if (col ==3 ) {
                col =0;
                row++;
            }
            
            createProductsInfoSceneAndGetController();
            pInfoController.setData(sortedResultFromSearch.get(i));
            
            productsGrid.add(root,col ,row);
            col++;
        }
    }
    private void createProductsInfoSceneAndGetController() {
        try {
            url = new File("Main/src/Views/UserScenes/ProductInfoBox.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            pInfoController = loader.getController();
        } catch (IOException e) {
            alert.setContentText("Could not open the info scene");
            alert.show();
        }
    }
    
}
