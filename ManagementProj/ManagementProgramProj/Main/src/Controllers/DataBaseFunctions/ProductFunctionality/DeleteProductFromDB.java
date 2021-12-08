package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import Controllers.DataBaseFunctions.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DeleteProductFromDB {
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    
    public static void deleteProductFromDB(int id) {
        try{
            tryToConnectToDBAndPrepareStatement();
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException exe){
            alert.setContentText("Cannot delete product");
            alert.show();
        }
    }
    private static void tryToConnectToDBAndPrepareStatement() throws SQLException{
        DBConnection.getDBConnection().connectToDataBase();
        Connection dbConnection = DBConnection.getTheConnectionToTheDB();

        pst = dbConnection.prepareStatement("delete from product where id= ?");
    }
}
