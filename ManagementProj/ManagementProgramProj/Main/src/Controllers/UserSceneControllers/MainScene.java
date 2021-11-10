package Controllers.UserSceneControllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import Controllers.SaveAndLoadHistory;
import Controllers.DBConnection;
import Models.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

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
    public static Queue<VBox> queueForBill = new LinkedList<>();
    ProductInfoController pInfoController;

    Stage stage;
    Scene scene;
    Parent root;

    ObservableList<ProductModel> pm; 
    SortedList<ProductModel> sortedList;
    FilteredList<ProductModel> filteredList;
    public void initialize(URL url, ResourceBundle rb ){
        basketItems = new HashMap<>();
        
        productsGrid.setHgap(180);
        productsGrid.setVgap(10);

        try {
            SaveAndLoadHistory.LoadFile();
            DBConnection.GetProducts();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Search();
    }
    void UpdateDataStructures(){
        try {
            DBConnection.GetProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        pm = FXCollections.observableArrayList(DBConnection.product.values());
        filteredList = new FilteredList<>(pm, b->true);
        sortedList = new SortedList<>(filteredList);
    }
    void Search(){
        try{

            UpdateDataStructures();
            UpdateTilePane();
                    
            searchTextField.textProperty().addListener((pm, oldValue, newValue) -> {
                filteredList.setPredicate(prod ->{
                if(newValue == null || newValue.isEmpty() || newValue.equals(null)){
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                if(prod.GetName().toLowerCase().indexOf(lowerCaseFilter)!= -1 ){
                    return true;
                } else if(prod.GetCategory().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(prod.GetMainCategory().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else{
                    return false;
                }
            });
            sortedList = new SortedList<>(filteredList);
            try {
                UpdateTilePane();
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        } catch(Exception e){
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("ERROR!");
            a.setContentText(e.getMessage());
            a.show();
        }
    }
    public void OpenOpenScene(ActionEvent e ) throws IOException{
        SaveAndLoadHistory.SaveFile();
        URL url = new File("Main/src/Scenes/OpenScene.fxml").toURI().toURL();
        
        Parent root = FXMLLoader.load(url);

        Stage stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void ShowHistory(ActionEvent e ) throws IOException{
        CreateScene("HistoryScene");
    }
    public void OpenBasket(ActionEvent e) throws IOException, SQLException{
        CreateScene("BoxScene");
        basketItems.clear();
        productsGrid.getChildren().clear();
        UpdateDataStructures();
        Search();
        UpdateTilePane();
    }

    void UpdateTilePane() throws IOException, SQLException{
        DBConnection.GetProducts();
        productsGrid.getChildren().clear();

        int row = 0, col=0;
        int amountOfBoxed;

            amountOfBoxed = sortedList.size();
            
            for (int i = 0; i < amountOfBoxed; i++) {
                if (col ==3 ) {
                    col =0;
                    row++;
                }
                
                CreateProductsScene();
                pInfoController.SetData(sortedList.get(i));
                
                productsGrid.add(root,col ,row);
                col++;
            }
    }
    void CreateProductsScene() throws IOException{
        URL  url = new File("Main/src/Scenes/UserScenes/ProductInfoBox.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        pInfoController = loader.getController();
    }
    void CreateScene(String name) throws IOException{
        URL  url = new File("Main/src/Scenes/UserScenes/" + name +".fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        root = loader.load();
        scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
