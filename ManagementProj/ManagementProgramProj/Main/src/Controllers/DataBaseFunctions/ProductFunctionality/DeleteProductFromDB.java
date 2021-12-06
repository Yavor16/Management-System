package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import static Controllers.DataBaseFunctions.DBConnection.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DeleteProductFromDB {
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    
    public static void deleteProductFromDB(int id) {
        try{
            getDBConnection().connectToDataBase();

            Connection dbConnection = getTheConnectionToTheDB(); 

            pst = dbConnection.prepareStatement("delete from product where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException exe){
            alert.setContentText("Cannot delete product");
            alert.show();
        }
    }
}