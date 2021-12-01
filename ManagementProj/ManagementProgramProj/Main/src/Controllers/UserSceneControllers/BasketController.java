package Controllers.UserSceneControllers;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BasketController implements Initializable{
    @FXML
    VBox vBox;

    @Override 
    public void initialize(URL url, ResourceBundle rb ){
        if(!MainScene.basketItems.isEmpty()){
            for(var prod : MainScene.basketItems.entrySet()){
                AddProdToBasket(prod.getValue(), prod.getKey());
            }
        }
    }
    private void AddProdToBasket(int amount, ProductModel prod){
        TextField text = new TextField();
        text.setText(prod.GetName() +" "+ amount +"x" + prod.GetPrice());
        text.setEditable(false);
        vBox.getChildren().add(text);
    }
    
    public void OpenBuyScene(ActionEvent e) throws IOException{
        URL url = new File("Main/src/Scenes/UserScenes/BuyScene.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        MainScene.basketItems.clear();
        
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();

    }
}
