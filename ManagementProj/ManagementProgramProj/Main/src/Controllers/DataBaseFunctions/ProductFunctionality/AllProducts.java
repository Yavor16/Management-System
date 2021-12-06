package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;
import java.util.TreeMap;

import static Controllers.DataBaseFunctions.DBConnection.*;
import Models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AllProducts extends CreateModels{

    private static Alert alert = new Alert(AlertType.WARNING);
    public static TreeMap<Integer, ProductModel> products = new TreeMap<Integer, ProductModel>();

    public static void getProducts() {
        try {  
            final ResultSet rs = getResultsFromDataBase();
            
            ProductModel currentProduct;
            products.clear();
            
            while (rs.next()) {
                currentProduct = chooseWhichProdModelToCreate(rs);
                products.put(currentProduct.GetID(), currentProduct); 
            }
        } catch (SQLException e) {
            alert.setContentText("Could get products from the database");
            alert.show();
        }
    }
    private static ResultSet getResultsFromDataBase() throws SQLException{
        getDBConnection().connectToDataBase();

        final String SQL = "SELECT * from product";
        
        Connection dbConnection = getTheConnectionToTheDB(); 
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
    
        return rs;
    }
    private static ProductModel chooseWhichProdModelToCreate(ResultSet rs){
        ProductModel currentProduct;
        try {
            if(rs.getString("maincategory").equals("Technology")){
                currentProduct = createTechnologyProductModel(rs);
            } else if (rs.getString("maincategory").equals("Food")) {
                currentProduct = createFoodProductModel(rs);
            } else if (rs.getString("maincategory").equals("Clothes")){
                currentProduct = createClothesProductModel(rs);
            } else{
                currentProduct = createProductModel(rs);
            }
            return currentProduct;
        } catch (SQLException e) {
            alert.setContentText("Cannot get product");
            alert.show();
            return null;
        }
    }
}
