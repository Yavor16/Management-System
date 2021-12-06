package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import static Controllers.DataBaseFunctions.DBConnection.*;
import Models.ProductModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GetProduct extends CreateModels{
    public static ProductModel wantedProd;
    private static Alert alert = new Alert(AlertType.WARNING);

    public static void getSpecificProduct(int id) {
        try {
            
            final ResultSet rs = getResultsFromDataBase(id);
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
            return;
        }
    }
    private static ResultSet getResultsFromDataBase(int id) throws SQLException{
        getDBConnection().connectToDataBase();

        final String SQL = "SELECT * FROM product where id=" + id;
        Connection dbConnection = getTheConnectionToTheDB(); 

        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
    
        return rs;
    }
}
