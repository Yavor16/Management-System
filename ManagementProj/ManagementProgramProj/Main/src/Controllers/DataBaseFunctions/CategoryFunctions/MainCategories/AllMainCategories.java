package Controllers.DataBaseFunctions.CategoryFunctions.MainCategories;

import java.sql.*;
import java.util.TreeMap;

import Controllers.DataBaseFunctions.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AllMainCategories {
    private static Alert alert = new Alert(AlertType.WARNING);
    public static TreeMap<Integer, String> mainCategories = new TreeMap<Integer, String>();

    public static void getMainCategories() {
        try {  
            final ResultSet rs = getResultsFromDataBase();
    
            mainCategories.clear();
            
            while (rs.next()) {
                mainCategories.put(rs.getInt("mainc_id"), rs.getString("maincategories")); 
            }
        } catch (SQLException e) {
            alert.setContentText("Could get main categories from the database");
            alert.show();
        }
    }
    private static ResultSet getResultsFromDataBase() throws SQLException{
        DBConnection.getDBConnection().connectToDataBase();

        final String SQL = "SELECT * from maincategories";
        
        Connection dbConnection = DBConnection.getTheConnectionToTheDB(); 
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
    
        return rs;
    }
}
