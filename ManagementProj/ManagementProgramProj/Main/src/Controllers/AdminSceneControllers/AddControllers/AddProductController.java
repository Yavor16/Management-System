package Controllers.AdminSceneControllers.AddControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Controllers.DBConnection;
import Controllers.AdminSceneControllers.*;
import Models.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Node;
public class AddProductController extends TextFieldsChecks implements Initializable{

    @FXML
    TextField nameText;
    @FXML
    TextField quantityText;
    @FXML
    TextField priceText;
    
    protected String category;
    protected Stage stage;
    protected float price;
    protected int quantity;
    protected static Alert alert = new Alert(AlertType.INFORMATION);
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        category = ChooseCategoryController.chosenCategory;
        alert.setTitle("Error");
        alert.setHeaderText(""); 
    }
    public void AddProduct(ActionEvent e)throws SQLException {
        if(areAllInputsValid()){
            ProductModel newModel = createNewProductModel();
            addProductToDB(newModel);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    @Override
    protected Boolean areAllInputsValid() {
        return isNameTextValid(nameText) && isQuantityTextValid(quantityText) && isPriceTextValid(priceText);
    }
    protected ProductModel createNewProductModel(){
        setVariablesValues();
        ProductModel pModel = new ProductModel(getIndexToAddNewProduct(), 
                                                nameText.getText(), 
                                                category, 
                                                quantity, 
                                                price);
        pModel.SetMainCategory(ChooseCategoryController.selcectedItemMainCategory);
        return pModel;
    }
    
    protected void setVariablesValues(){
        price = Float.parseFloat(priceText.getText());
        quantity = Integer.parseInt(quantityText.getText());
    }
    protected Integer getIndexToAddNewProduct(){   
        DBConnection.getProducts();

        int id = 0;
        if (!DBConnection.product.isEmpty()) {            
            Object key = DBConnection.product.keySet().toArray()[DBConnection.product.size() - 1]; 
            id = DBConnection.product.get(key).GetID() + 1;
        }
        return id;
    }
    protected void addProductToDB(ProductModel pModel){
    
        DBConnection.getProducts();

        if (isNewTheProductInList(nameText.getText())) {
            alert.setContentText("Change the name because this name already exists");
            alert.show();
        } else{   
            DBConnection.addProduct(pModel);

            ProductModel newModel = new ProductModel(getIndexToAddNewProduct() - 1, 
                                                        nameText.getText(), 
                                                        ChooseCategoryController.chosenCategory, 
                                                        quantity, 
                                                        price);

            AdminMainSceneController.products.add(newModel);

            alert.setTitle("New product");
            alert.setHeaderText("");
            alert.setContentText("Product added!");
            alert.show();
        }          
    }
    
    protected Boolean isNewTheProductInList(String searchedName){
        int arrSize = AdminMainSceneController.products.size();
        for (int i = 0; i < arrSize; i++) {

            ProductModel currentProduct = AdminMainSceneController.products.get(i) ; 

            if (currentProduct.GetName().equals(searchedName)) {
                return true;
            }
        }
        return false;
    }
    
    public void CancelProduct(ActionEvent e){
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }
}
