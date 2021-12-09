package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

import java.sql.*;
import java.util.Map;

import Controllers.DataBaseFunctions.DBConnection;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AllMainCategories;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddChildrenCategories {
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    private static int id = 1;
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
        id = 1;
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
                                            "categories(id,maincategory_id,parentcategory_name,childcategory)" + 
                                            "values(?,?,?,?)");   
    }
    private static void setVarValues(Category mainCat) throws SQLException {
        pst.setInt(1, id);
        pst.setInt(2, mainCat.getMainCategoryId());
        pst.setString(3, mainCat.getParentCategoryName());
        pst.setString(4, mainCat.getChildCategory());

        id++;
    }
    public static int getIdOfMainCategory(String mainCat){
        for (Map.Entry<Integer, String> category :  AllMainCategories.mainCategories.entrySet()) {
            if (category.getValue().equals(mainCat)) {
                return category.getKey();
            }
        }
        return 0;
    }
}
