package Controllers.UserSceneControllers;

import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import java.io.*;

import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

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

    private int count = 0;
    private ProductModel productModel;

    public void RemoveOneNum(ActionEvent e){
        count = count == 0 ? count : count -1;
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
        }
        countText.setText(String.valueOf(count));
    }
    public void setData(ProductModel prod) {
        productModel = prod;
        
        initializeTextFields(prod);
        
        if (prod.GetMainCategory().equals("Technology")) {  
            setProductImageToImageView("technology");
        } else if (prod.GetMainCategory().equals("Food")) {
            setProductImageToImageView("food");
        } else if (prod.GetMainCategory().equals("Clothes")) {
            setProductImageToImageView("clothes");
        }
    }
    private void initializeTextFields(ProductModel prod){
        nameText.setText(prod.GetName());
            
        priceText.setText("Price: " + String.valueOf(prod.GetPrice()));
        
        amountText.setText("Amount: " + String.valueOf(prod.GetQuantity()));
        
        countText.setEditable(false);
    }
    private void setProductImageToImageView(String imageName){
        try {
            URL url = new File("Main/images/categoryicons/"+ imageName +".png").toURI().toURL();
            Image icon = new Image(url.toString());
            productImage.setImage(icon);
        } catch (MalformedURLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Could not create items");
            alert.show();
        }
    }
}
