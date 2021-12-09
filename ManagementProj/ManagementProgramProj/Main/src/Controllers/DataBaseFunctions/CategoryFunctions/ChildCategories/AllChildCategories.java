package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import Controllers.DataBaseFunctions.DBConnection;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AllMainCategories;
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
        int maincategoryId = rs.getInt("maincategory_id");
        String mainCategoryName = getMainCatagoryName(maincategoryId);
        String parentCategoryId = rs.getString("parentcategory_name");
        String childCategory = rs.getString("childcategory");


        Category newCategory = new Category(maincategoryId, mainCategoryName ,parentCategoryId,childCategory);

        return newCategory;
    }
    private static String getMainCatagoryName(int id){
        for (Map.Entry<Integer, String> categ : AllMainCategories.mainCategories.entrySet()) {
            if (categ.getKey() == id) {
                return categ.getValue();
            }
        }
        return "";
    }   
}
