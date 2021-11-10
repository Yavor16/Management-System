package Controllers.UserSceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductInfoController {
    @FXML
    ImageView productImage;
    @FXML
    Label nameText;     
    @FXML 
    Label priceText;
    @FXML 
    Button minusBttn;
    @FXML
    Button plusBttn;
    @FXML
    TextField countText;
    @FXML
    Label amountText;

    int count = 0;
    ProductModel productModel;

    public void RemoveOneNum(ActionEvent e){
        count = count == 0? count: count -1;
        if (count == 0) {
                MainScene.basketItems.remove(productModel);            
        } else if (count > 0){
            MainScene.basketItems.replace(productModel, Integer.parseInt(countText.getText()), count);  
        }
        countText.setText(String.valueOf(count));
    }
    public void AddOneNum(ActionEvent e) throws IOException{
        if (productModel.GetQuantity() > count) {
            count++;
            MainScene.basketItems.put(productModel, count);
            System.out.println(MainScene.basketItems);
        }
        countText.setText(String.valueOf(count));
    }
    void SetData(ProductModel prod) throws MalformedURLException{
        productModel = prod;
        
        nameText.setText(prod.GetName());

        priceText.setText("Price: " + String.valueOf(prod.GetPrice()));

        amountText.setText("Amount: " + String.valueOf(prod.GetQuantity()));

        countText.setEditable(false);

        if (prod.GetMainCategory().equals("Technology")) {
            URL url = new File("Main/images/categoryicons/technology.png").toURI().toURL();
            Image icon = new Image(url.toString());
            productImage.setImage(icon);            
        } else if (prod.GetMainCategory().equals("Food")) {
            URL url = new File("Main/images/categoryicons/food.png").toURI().toURL();
            Image icon = new Image(url.toString());
            productImage.setImage(icon);
        } else if (prod.GetMainCategory().equals("Clothes")) {
            URL url = new File("Main/images/categoryicons/clothes.png").toURI().toURL();
            Image icon = new Image(url.toString());
            productImage.setImage(icon);
        }
    }
}
