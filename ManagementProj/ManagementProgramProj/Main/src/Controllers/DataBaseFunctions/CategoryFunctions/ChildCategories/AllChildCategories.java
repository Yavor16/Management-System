package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

import java.sql.*;
import java.util.*;

import Controllers.DataBaseFunctions.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AllChildCategories {
    private static Alert alert = new Alert(AlertType.WARNING);
    public static ArrayList<Category> childCategories = new ArrayList<Category>();

    public static void getChildrenCategories() {
        try {  
            final ResultSet rs = getResultsFromDataBase();
            
            childCategories.clear();
            
            while (rs.next()) {
                childCategories.add(createCategory(rs)); 
            }
        } catch (SQLException e) {
            alert.setContentText("Could not get categories from the database");
            alert.show();
        }
    }
    private static ResultSet getResultsFromDataBase() throws SQLException{
        DBConnection.getDBConnection().connectToDataBase();

        final String SQL = "SELECT * from categories";
        Connection dbConnection = DBConnection.getTheConnectionToTheDB(); 
        
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
    
        return rs;
    }
    private static Category createCategory(ResultSet rs) throws SQLException{
        int categoryId = rs.getInt("id");
        int maincategoryId = rs.getInt("maincategory_id");
        int parentCategoryId = rs.getInt("parentcategory_id");
        String childCategory = rs.getString("childcategory");


        Category newCategory = new Category(categoryId, maincategoryId, parentCategoryId,childCategory);

        return newCategory;
    }
}
