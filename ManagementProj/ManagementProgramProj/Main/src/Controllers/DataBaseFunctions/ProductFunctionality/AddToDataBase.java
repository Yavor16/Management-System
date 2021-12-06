package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import static Controllers.DataBaseFunctions.DBConnection.*;
import Models.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddToDataBase {

    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    
    public static void addNewProductToDB(ProductModel pm) {
        try {
            getDBConnection().connectToDataBase();
            Connection dbConnection =getTheConnectionToTheDB();

            pst = dbConnection.prepareStatement("insert into product(id,name,category,price,quantity,resolution,used,size,maincategory)values(?,?,?,?,?,?,?,?,?)");
            
            setBasicVarValues(pm);
            checkWhichProdToAdd(pm);
            
            pst.executeUpdate();
        } catch (SQLException e) {
            alert.setContentText("Cannot add product to database");
            alert.show();
        }
    }
    private static void setBasicVarValues(ProductModel pm) throws SQLException{
        pst.setInt(1, pm.GetID());
        pst.setString(2, pm.GetName());
        pst.setString(3, pm.GetCategory());
        pst.setFloat(4, pm.GetPrice());
        pst.setInt(5, pm.GetQuantity());
    }
    private static void checkWhichProdToAdd(ProductModel pm) throws SQLException{
        if (pm instanceof TechnologyProductModel) {
            TechnologyProductModel tp = (TechnologyProductModel)pm;
            int isUsed = tp.GetUsed() ? 1 : 0;
            String used = String.valueOf(isUsed);  

            setProductToAddVarValues(tp.GetResolution(), used, null, tp.GetMainCategory());
        } else if(pm instanceof VegetangleFruitModel){
            setProductToAddVarValues(null, null, null, "Food");
        } else if(pm instanceof ClothesModel){
            ClothesModel cm = (ClothesModel)pm;
            setProductToAddVarValues(null, null, cm.GetSize(), cm.GetMainCategory());
        } else{
            setProductToAddVarValues(null, null, null, pm.GetMainCategory());
        }
    }
    private static void setProductToAddVarValues(String resolution, String used, String size, String maincategory) throws SQLException{        
        pst.setString(6, resolution);
        pst.setString(7, used);
        pst.setString(8, size);
        pst.setString(9, maincategory);
    }
}
