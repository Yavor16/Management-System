package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

import java.sql.*;

import Controllers.DataBaseFunctions.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddChildrenCategories {
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    private static Connection dbConnection;

    public static void addAllChildCategoriesToDB() {
        try {
            tryToConnectToDB();
            resetTableAndVariables();
            
            insertCategories();
            
        } catch (SQLException e) {
            alert.setContentText("Cannot add category to database");
            alert.show();
        }
    }
    private static void tryToConnectToDB(){
        DBConnection.getDBConnection().connectToDataBase();
        dbConnection = DBConnection.getTheConnectionToTheDB();
    }
    private static void resetTableAndVariables() throws SQLException{
        pst = dbConnection.prepareStatement("truncate categories");
        pst.executeUpdate();
    }
    private static void insertCategories() throws SQLException{
        prepareInsertStatement();
        for (Category currentCateg : AllChildCategories.childCategories) {
            setVarValues(currentCateg);
            pst.executeUpdate();
        }
    }
    private static void prepareInsertStatement() throws SQLException{
        pst = dbConnection.prepareStatement("insert into " +  
                                            "categories(id,maincategory_id,parentcategory_id,childcategory)" + 
                                            "values(?,?,?,?)");   
    }
    private static void setVarValues(Category mainCat) throws SQLException {
        pst.setInt(1, mainCat.getCategoriId());
        pst.setInt(2, mainCat.getMainCategoryId());
        pst.setInt(3, mainCat.getParentCategoryId());
        pst.setString(4, mainCat.getChildCategory());

    }
    
}
