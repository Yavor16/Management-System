package ManagementProgramProj.Main.src.Controllers.AdminSceneControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.Node;
import javafx.stage.Stage;

public class EditProductController implements Initializable{
    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
    @FXML
    ComboBox<String> categoryComboBox;
    ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Fruits", "Vegetables", "Meat", "Alcohol");

    ProductModel selectedProd = AdminMainSceneController.selectedProduct; 

    String selectedCategory;

    ProductModel productToAdd;

    int selectedProductID = AdminMainSceneController.listOfProducts.indexOf(selectedProd);

    @Override
    public void initialize(URL url, ResourceBundle rb){
        nameText.setText(selectedProd.GetName());
        quantityText.setText(String.valueOf(selectedProd.GetQuantity()));
        priceText.setText(String.valueOf(selectedProd.GetPrice()));
        categoryComboBox.setItems(comboBoxItems);
        categoryComboBox.setValue(selectedProd.GetCategory());

        nameText.setDisable(true);
    }
    public void UpdateProduct(ActionEvent e) throws SQLException {
        try{
            float price = Float.parseFloat(priceText.getText());
            int quantity = Integer.parseInt(quantityText.getText());
            selectedCategory = categoryComboBox.getValue().toString();
           
            DBConnection.UpdateProduct(selectedProd.GetID(), nameText.getText(), selectedCategory, price , quantity);
            AdminMainSceneController.listOfProducts.remove(selectedProductID);
            
            AdminMainSceneController.listOfProducts.add(selectedProductID,
             new ProductModel(selectedProd.GetID(), nameText.getText(), selectedCategory, quantity , price)
            );
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("");
            alert.setContentText(selectedProd.GetName() + " updated!");
            alert.showAndWait();
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
        catch(Exception exe){
            JOptionPane.showMessageDialog(null, exe.getMessage());
        }
    }
    public void CancelProduct(ActionEvent e ){
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}