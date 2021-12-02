package Controllers.DataBaseFunctions.ProductFunctionality;

import java.sql.*;
import Models.*;

public abstract class CreateModels {
    public static ProductModel createClothesProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct= new ClothesModel(rs.getInt("id"), 
                                                    rs.getString("name"), 
                                                    rs.getString("category"), 
                                                    rs.getInt("quantity"), 
                                                    rs.getFloat("price"), 
                                                    rs.getString("size"));
        return currentProduct;
    }
    public static ProductModel createFoodProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct  = new VegetangleFruitModel(rs.getInt("id"), 
                                                                rs.getString("name"), 
                                                                rs.getString("category"), 
                                                                rs.getInt("quantity"), 
                                                                rs.getFloat("price"));
        return currentProduct;
    }
    public static ProductModel createTechnologyProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct = new TechnologyProductModel(rs.getInt("id"), 
                                                                rs.getString("name"), 
                                                                rs.getString("category"), 
                                                                rs.getInt("quantity"), 
                                                                rs.getFloat("price"), 
                                                                rs.getString("resolution"), 
                                                                rs.getBoolean("used"));
        return currentProduct;
    }
    public static ProductModel createProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct = new ProductModel(rs.getInt("id"), 
                                                    rs.getString("name"), 
                                                    rs.getString("category"), 
                                                    rs.getInt("quantity"), 
                                                    rs.getFloat("price"));
        currentProduct.SetMainCategory(rs.getString("maincategory"));
        return currentProduct;
    }
}
