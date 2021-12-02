package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import Controllers.DataBaseFunctions.DBConnection;
import Models.ProductModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GetProduct extends CreateModels{
    public static ProductModel wantedProd;
    private static Alert alert = new Alert(AlertType.WARNING);

    public static void getSpecificProduct(int id) {
        try {
            
            final String SQL = "SELECT * FROM product where id=" + id ;
            final ResultSet rs = DBConnection.dbConnection.createStatement().executeQuery(SQL);
            wantedProd = null;
            
            while (rs.next()) {
                String category = rs.getString(9); 
                if (category.equals("Technology")) {
                    wantedProd = createTechnologyProductModel(rs);
                    
                } else if(category.equals("Food")){
                    wantedProd = createFoodProductModel(rs);
                    
                } else if(category.equals("Clothes")){
                    wantedProd = createClothesProductModel(rs);
                    
                } else{
                    wantedProd = createProductModel(rs);
                }
            }
        } catch (SQLException e) {
            alert.setContentText("Could not get the product");
            alert.show();
        }
    }
}
