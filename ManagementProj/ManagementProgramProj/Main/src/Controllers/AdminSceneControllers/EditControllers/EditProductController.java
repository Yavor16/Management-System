package Controllers.AdminSceneControllers.EditControllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Controllers.AdminSceneControllers.AdminMainSceneController;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class EditProductController implements Initializable{
    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
  
    ProductModel selectedProd = AdminMainSceneController.selectedProduct; 
    Alert alert = new Alert(AlertType.INFORMATION);

    String selectedCategory;
    float price;
    int quantity;
    ProductModel productToAdd;
    int selectedProductID = AdminMainSceneController.listOfProducts.indexOf(selectedProd);

    @Override
    public void initialize(URL url, ResourceBundle rb){
        nameText.setText(selectedProd.GetName());
        quantityText.setText(String.valueOf(selectedProd.GetQuantity()));
        priceText.setText(String.valueOf(selectedProd.GetPrice()));
  
        nameText.setDisable(true);

        alert.setHeaderText("");
    }
    public abstract void UpdateProduct(ActionEvent e) throws SQLException;

    public void UpdateProductToDB(ProductModel pModel){
        try{
           
                DBConnection.UpdateProduct(pModel);
                AdminMainSceneController.SetListOfProducts();
                //AdminMainSceneController.listOfProducts.remove((ProductModel)pModel);
                //AdminMainSceneController.listOfProducts.add(pModel);
                alert.setTitle("Update");
                alert.setContentText(selectedProd.GetName() + " updated!");
                alert.showAndWait();

        }catch(Exception exe){
            alert.setTitle("New product");
            alert.setContentText(exe.getLocalizedMessage());
            alert.showAndWait();
        }
    }
    public void CancelProduct(ActionEvent e ){
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    Boolean SetQuantity(){
        alert.setTitle("Error");

        if(quantityText.getText().matches("[0-9]+")){
            try{
                quantity = Integer.parseInt(quantityText.getText());
                return true;
            }catch(Exception e){
                alert.setContentText("Enter smaller quantity!");
                alert.showAndWait();
                return false;
            }
        }
        else{
            alert.setContentText("Quantity: Enter only numbers");
            alert.showAndWait();
            return false;
        }
    }
    Boolean SetPrice(){
        
        try{
            price = Float.parseFloat(priceText.getText());
            price = Math.round(price);
            return true;
        }
        catch(Exception e){
            alert.setTitle("Error");
            alert.setContentText("Price: Enter only numbers");
            alert.showAndWait();
            return false;
        }
    }
}