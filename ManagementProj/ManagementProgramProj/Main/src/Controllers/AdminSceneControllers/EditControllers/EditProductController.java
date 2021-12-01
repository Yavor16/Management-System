package Controllers.AdminSceneControllers.EditControllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Controllers.AdminSceneControllers.AdminMainSceneController;
import Controllers.AdminSceneControllers.TextFieldsChecks;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;

public class EditProductController extends TextFieldsChecks implements Initializable{
    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
  
    protected ProductModel selectedProd = AdminMainSceneController.selectedProduct; 
    protected Alert alert = new Alert(AlertType.INFORMATION);

    protected float price;
    protected int quantity;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        nameText.setText(selectedProd.GetName());
        quantityText.setText(String.valueOf(selectedProd.GetQuantity()));
        priceText.setText(String.valueOf(selectedProd.GetPrice()));
  
        nameText.setDisable(true);

        alert.setHeaderText("");
    }
    public void UpdateProduct(ActionEvent e) {
        if(areAllInputsValid()){
            
            ProductModel newModel = createNewModelForUpdate();
            updateProductToDB(newModel);
            
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    protected ProductModel createNewModelForUpdate(){
        setVariablesValues();
        ProductModel pModel = new ProductModel(selectedProd.GetID(), 
                                                selectedProd.GetName(), 
                                                selectedProd.GetCategory(), 
                                                quantity, 
                                                price);
        pModel.SetMainCategory(AdminMainSceneController.mainCat);

        return pModel;
    }
    public void updateProductToDB(ProductModel pModel){    
        DBConnection.updateProduct(pModel);
        
        alert.setTitle("Update");
        alert.setContentText(selectedProd.GetName() + " updated!");
        alert.show();
    }
    @Override
    protected Boolean areAllInputsValid() {
        return isQuantityTextValid(quantityText) && isPriceTextValid(priceText);
    }
    public void CancelProduct(ActionEvent e ){
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
    protected void setVariablesValues() {
        price = Float.parseFloat(priceText.getText());
        quantity = Integer.parseInt(quantityText.getText());
    }
}