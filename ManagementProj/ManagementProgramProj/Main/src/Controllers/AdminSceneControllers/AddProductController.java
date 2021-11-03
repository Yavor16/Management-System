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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Fruits", "Vegetables", "Meat", "Alcohol");
        categoryComboBox.setItems(comboBoxItems);
        System.out.println(GetLastIndex());
    }
    
    public void addProduct(ActionEvent e) throws SQLException{
        CheckComboBoxInput();
        try{
            float price = Float.parseFloat(priceText.getText());
            int quantity = Integer.parseInt(quantityText.getText());
            selectedCategory = categoryComboBox.getValue().toString();
            DBConnection.AddProduct(GetLastIndex(), nameText.getText(), selectedCategory, price, quantity);
            
            AdminMainSceneController.listOfProducts.add(new ProductModel(GetLastIndex(), nameText.getText(), selectedCategory, quantity, price));

            JOptionPane.showMessageDialog(null, "Product added!");

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
        catch(Exception exe){
            JOptionPane.showMessageDialog(null, exe.getMessage());
        }
    }
    private void CheckComboBoxInput(){
        try{
              selectedCategory = categoryComboBox.getValue().toString();
        }
        catch(Exception exe){
            JOptionPane.showMessageDialog(null, exe.getMessage());
        }
    }

    int GetLastIndex(){
        Object key =DBConnection.product.keySet().toArray()[DBConnection.product.size() - 1]; 

        int id = DBConnection.product.get(key).GetID();
        return id + 1;
    }
}
