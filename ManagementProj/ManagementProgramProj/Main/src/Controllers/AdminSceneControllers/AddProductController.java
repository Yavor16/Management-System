package ManagementProgramProj.Main.src.Controllers.AdminSceneControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ManagementProgramProj.Main.src.DBConnection;
import ManagementProgramProj.Main.src.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
public class AddProductController implements Initializable {

    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
    @FXML
    ComboBox<String> categoryComboBox;

    String selectedCategory;
    Stage stage;
    float price;
    int quantity;
    static Alert alert = new Alert(AlertType.INFORMATION);
    

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Fruits", "Vegetables", "Meat", "Alcohol");
        categoryComboBox.setItems(comboBoxItems);
    
    }
    public void AddProduct(ActionEvent e) throws SQLException{
        try{
            if(SetPrice() && CheckComboBoxInput() && SetQuantity()){
                DBConnection.GetProducts();
                if (DoesListContainProduct(nameText.getText())) {
                    int arrSize = AdminMainSceneController.listOfProducts.size();
                    
                    for (int i = 0; i < arrSize; i++) {
                        ProductModel prod = AdminMainSceneController.listOfProducts.get(i);
                        if (prod.GetName().equals(nameText.getText()) ) {

                            prod.SetQuantity(prod.GetQuantity() + quantity);       
                            DBConnection.UpdateProduct(prod.GetID(), 
                                                        prod.GetName(), 
                                                        prod.GetCategory(), 
                                                        prod.GetPrice(), 
                                                        prod.GetQuantity());
                            break;
                        }
                    }
                }
                else{   
                    DBConnection.AddProduct(GetLastIndex(), nameText.getText(), selectedCategory, price, quantity);
                    AdminMainSceneController.listOfProducts.add(new ProductModel(GetLastIndex(), nameText.getText(), selectedCategory, quantity, price));
                    alert.setTitle("New product");
                    alert.setHeaderText("");
                    alert.setContentText("Product added!");
                    alert.showAndWait();
                }
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                stage.close();
            }
        }
        catch(Exception exe){
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText(exe.getLocalizedMessage());
            alert.showAndWait();
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("");

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
    Boolean CheckComboBoxInput(){
        try{
            selectedCategory = categoryComboBox.getValue().toString();
            return true;
        }
        catch(Exception exe){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Choose category!");
            alert.showAndWait();
            return false;
        }
    }
    Boolean SetPrice(){
        if(priceText.getText().matches("[0-9]+")){
            price = Float.parseFloat(priceText.getText());
            price = Math.round(price);
            return true;
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Price: Enter only numbers");
            alert.showAndWait();
            return false;
        }
    }
    //Object key;
    int GetLastIndex(){
        int id = 0;
        if (!DBConnection.product.isEmpty()) {
            Object key = DBConnection.product.keySet().toArray()[DBConnection.product.size() - 1]; 
            id = DBConnection.product.get(key).GetID() + 1;
        }
        return id;
    }
}
