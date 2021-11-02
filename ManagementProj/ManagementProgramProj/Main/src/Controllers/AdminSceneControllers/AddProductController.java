package ManagementProgramProj.Main.src.Controllers.AdminSceneControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ManagementProgramProj.Main.src.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddProductController implements Initializable {
    @FXML
    TextField idText;
    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
    @FXML
    ComboBox<String> categoryComboBox;
    private int id = 9;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList("Fruits", "Vegetables", "Meat", "Alcohol");
        categoryComboBox.setItems(comboBoxItems);
    }
    public void addProduct(ActionEvent e) throws SQLException{

        float price = Float.parseFloat(priceText.getText());
        int quantity = Integer.parseInt(quantityText.getText());
        String selectedCategory = categoryComboBox.getValue().toString();
        
        try{
            DBConnection.AddProduct(id, nameText.getText(), selectedCategory, price, quantity);
            id++;
            Alert alert = new Alert(AlertType.CONFIRMATION, "Product added!", ButtonType.OK);
            alert.showAndWait();
        }
        catch(Exception exe){
            System.out.println(exe.getMessage() );
        }
    }
}
