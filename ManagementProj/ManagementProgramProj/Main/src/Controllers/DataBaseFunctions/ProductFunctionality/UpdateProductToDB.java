package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;

import Controllers.DataBaseFunctions.DBConnection;
import Models.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UpdateProductToDB {
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    
    public static void updateProductToDB(ProductModel pm) {
        try{
            tryToConnectToDBAndPrepareStatement();
            
            pst.setString(1, pm.GetName());
            pst.setFloat(2, pm.GetPrice());
            pst.setString(3, pm.GetCategory());
            pst.setInt(4, pm.GetQuantity());
            pst.setInt(9, pm.GetID());
            

            checkWhichProdToUpdate(pm);
            pst.executeUpdate();

        } catch(SQLException exe){          
            alert.setContentText("Could not update product");
            alert.showAndWait();
            return;
        }
    }
    private static void tryToConnectToDBAndPrepareStatement() throws SQLException{
        DBConnection.getDBConnection().connectToDataBase();
        Connection dbConnection = DBConnection.getTheConnectionToTheDB();

        pst = dbConnection.prepareStatement("update product set name= ?, price= ?, category= ?, quantity= ?, resolution= ?, used= ?,size= ?, maincategory= ? where id= ?");
    }
    private static void checkWhichProdToUpdate(ProductModel pm) throws SQLException{
        if (pm instanceof TechnologyProductModel) {
            TechnologyProductModel tp = (TechnologyProductModel)pm;
            int isUsed = tp.GetUsed() ? 1 : 0;
            String used = String.valueOf(isUsed);  

            setProductToUpdateVarValues(tp.GetResolution(), used, null, tp.GetMainCategory());
        } else if(pm instanceof VegetangleFruitModel){
            setProductToUpdateVarValues(null, null, null, "Food");
        } else if(pm instanceof ClothesModel){
            ClothesModel cm = (ClothesModel)pm;
            setProductToUpdateVarValues(null, null, cm.GetSize(), cm.GetMainCategory());
        } else{
            setProductToUpdateVarValues(null, null, null, pm.GetMainCategory());
        }
    }
    private static void setProductToUpdateVarValues(String resolution, String used, String size, String maincategory) throws SQLException{        
        pst.setString(5, resolution);
        pst.setString(6, used);
        pst.setString(7, size);
        pst.setString(8, maincategory);
    }
}
