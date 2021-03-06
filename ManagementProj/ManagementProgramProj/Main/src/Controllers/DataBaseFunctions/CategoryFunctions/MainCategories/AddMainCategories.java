package Controllers.DataBaseFunctions.CategoryFunctions.MainCategories;

import java.sql.*;
import java.util.Map;

import Controllers.DataBaseFunctions.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddMainCategories {

    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);

    private static Connection dbConnection;

    public static void addNewMainCatToDB() {
        try {
            tryToConnectToDB();
            resetTableAndVariables();
            
            insertCategories();
            
        } catch (SQLException e) {
            alert.setContentText("Cannot add maincategory to database");
            alert.show();
        }
    }
    private static void tryToConnectToDB(){
        DBConnection.getDBConnection().connectToDataBase();
        dbConnection = DBConnection.getTheConnectionToTheDB();
    }
    private static void resetTableAndVariables() throws SQLException{
        pst = dbConnection.prepareStatement("truncate maincategories");
        pst.executeUpdate();
    }
    private static void insertCategories() throws SQLException{
        prepareInsertStatement();
        for (Map.Entry<Integer,String> cat : AllMainCategories.mainCategories.entrySet()) {
            setVarValues(cat.getKey(), cat.getValue());
            pst.executeUpdate();
        }
    }
    private static void prepareInsertStatement() throws SQLException{
        pst = dbConnection.prepareStatement("insert into " +  
                                            "maincategories(mainc_id,maincategories)" + 
                                            "values(?,?)");   
    }
    private static void setVarValues(int id ,String mainCat) throws SQLException {
        pst.setInt(1, id);
        pst.setString(2, mainCat);

        id++;
    }
}
