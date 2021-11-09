package Controllers.UserSceneControllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainScene implements Initializable{
    
    @FXML 
    TextField searchTextField;
    @FXML
    Button backBttn;
    @FXML
    Button allProductsBttn;
    @FXML
    Button historyBttn;
    @FXML
    Button basketBttn;
    @FXML
    GridPane productsGrid;
    public static Map<ProductModel, Integer> basketItems;
    ProductInfoController pInfoController;

    Stage stage;
    Scene scene;
    Parent root;

    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        basketItems = new HashMap<>();
        
        productsGrid.setHgap(180);
        productsGrid.setVgap(10);
        try {
            DBConnection.GetProducts();
            UpdateTilePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SearchForProduct(ActionEvent e ){
        System.out.println(2);
    }
    public void OpenOpenScene(ActionEvent e ) throws IOException{
        URL url = new File("Main/src/Scenes/OpenScene.fxml").toURI().toURL();
        
        Parent root = FXMLLoader.load(url);

        Stage stage = (Stage)((Node )e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void ShowAllProdcuts(ActionEvent e ) throws IOException{
        CreateScene("UserScene");
    }
    public void ShowHistory(ActionEvent e ){
        System.out.println(4);
    }
    public void OpenBasket(ActionEvent e) throws IOException, SQLException{
        CreateScene("BoxScene");
        basketItems.clear();
        productsGrid.getChildren().clear();
        UpdateTilePane();
    }
    void UpdateTilePane() throws IOException, SQLException{
        DBConnection.GetProducts();
        List<Integer> keys =  new ArrayList<>(DBConnection.product.keySet());
        int row = 0, col=0;
        int amountOfBoxed = DBConnection.product.size();
        
        for (int i = 0; i < amountOfBoxed; i++) {
            if (col ==3 ) {
                col =0;
                row++;
            }

            CreateProductsScene();
            pInfoController.SetData(DBConnection.product.get(keys.get(i)));
            
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
