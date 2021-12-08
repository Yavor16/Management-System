package Controllers.AdminSceneControllers.AddControllers;

import Controllers.AdminSceneControllers.*;
import Controllers.AdminSceneControllers.Categories.ChooseCategoryController;
import javafx.scene.control.*;
import javafx.fxml.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;

import static Controllers.DataBaseFunctions.ProductFunctionality.AddToDataBase.*;
import Models.ProductModel;
import javafx.event.ActionEvent;
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
        AllProducts.getProducts();

        int id = 0;
        if (!AllProducts.products.isEmpty()) {            
            Object key = AllProducts.products.keySet().toArray()[AllProducts.products.size() - 1]; 
            id = AllProducts.products.get(key).GetID() + 1;
        }
        return id;
    }
    protected void addProductToDB(ProductModel pModel){
    
        AllProducts.getProducts();

        if (isNewTheProductInList(nameText.getText())) {
            alert.setContentText("Change the name because this name already exists");
            alert.show();
        } else{   
            addNewProductToDB(pModel);
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
