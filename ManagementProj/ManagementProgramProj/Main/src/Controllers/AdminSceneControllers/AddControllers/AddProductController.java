package Controllers.AdminSceneControllers.AddControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Controllers.AdminSceneControllers.AdminMainSceneController;
import Controllers.AdminSceneControllers.ChooseCategoryController;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
public abstract class AddProductController implements Initializable{

    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
    
    String category;
    Stage stage;
    float price;
    int quantity;
    static Alert alert = new Alert(AlertType.INFORMATION);
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        category = ChooseCategoryController.chosenCategory;
        alert.setTitle("Error");
        alert.setHeaderText(""); 
    }
    public abstract void AddProduct(ActionEvent e)throws SQLException ;

    void AddProductToDB(ProductModel pModel){
        try{
            DBConnection.GetProducts();
            if (DoesListContainProduct(nameText.getText())) {
                alert.setContentText("Change the name because this name already exists");
                alert.show();
            } else{   
                DBConnection.AddProduct(pModel);
                AdminMainSceneController.listOfProducts.add(new ProductModel(GetLastIndex() - 1, nameText.getText(), ChooseCategoryController.chosenCategory, quantity, price));
                alert.setTitle("New product");
                alert.setHeaderText("");
                alert.setContentText("Product added!");
                alert.show();
            }
            
        } catch(Exception exe){
            alert.setContentText(exe.getLocalizedMessage());
            alert.show();
        }
    }
    public void CancelProduct(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    Boolean DoesListContainProduct(String name){
        int arrSize = AdminMainSceneController.listOfProducts.size();
        for (int i = 0; i < arrSize; i++) {
            if (AdminMainSceneController.listOfProducts.get(i).GetName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    Boolean SetQuantity(){
        if(!quantityText.getText().isEmpty()){

            if(quantityText.getText().matches("[0-9]+")){
                try{
                    quantity = Integer.parseInt(quantityText.getText());
                    return true;
                }catch(Exception e){
                    alert.setContentText("Enter smaller quantity!");
                    alert.show();
                    return false;
                }
            } else{
                alert.setContentText("Quantity: Enter only numbers");
                alert.show();
                return false;
            }
        } else{
            alert.setContentText("Quantity: Field cannot be empty!");
            alert.show();
            return false;
        }
    }
    Boolean SetPrice(){
        if(!priceText.getText().isEmpty()){
            try{
                price = Float.parseFloat(priceText.getText());
                price = Math.round(price);
                return true;
            } catch(Exception e){
               
                alert.setContentText("Price: Enter only numbers");
                alert.show();
                return false;
            }
        } else{
            alert.setContentText("Price: Field cannot be empty!");
            alert.show();
            return false;
        }
    }
    Boolean SetName(){
        if (nameText.getText().isEmpty()) {
            alert.setContentText("Name: Enter a name");
            alert.show();
            return false;
        }
        return true;
    }
    //Object key;
    int GetLastIndex() throws SQLException{
        DBConnection.GetProducts();
        int id = 0;
        if (!DBConnection.product.isEmpty()) {            
            Object key = DBConnection.product.keySet().toArray()[DBConnection.product.size() - 1]; 
            id = DBConnection.product.get(key).GetID() + 1;
        }
        return id;
    }
}
