package Controllers;

import java.util.*;
import java.sql.*;
import Models.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnection {
    
    private static Connection dbConnection = null;
    private static PreparedStatement pst;
    private static Alert alert = new Alert(AlertType.WARNING);
    
    public static ProductModel wantedProd;
    public static TreeMap<Integer, ProductModel> product = new TreeMap<Integer, ProductModel>();

    public static void Connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/productsdb";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "Jokojok16.");

            dbConnection = DriverManager.getConnection(url, info);
        } 
        catch (SQLException ex) {
            alert.setTitle("Error");;
            alert.setContentText("An error occurred while connecting MySQL database");
            alert.showAndWait();
        }
    }
    public static void getProducts() {
        try {
            
            final String SQL = "SELECT * from product";
            final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
            ProductModel currentProduct;
            product.clear();
            
            while (rs.next()) {
                currentProduct = chooseWhichProdModelToCreate(rs);
                product.put(currentProduct.GetID(), currentProduct); 
            }
        } catch (SQLException e) {
            alert.setContentText("Could get products from the database");
            alert.show();
        }
    }
    private static ProductModel chooseWhichProdModelToCreate(ResultSet rs){
        ProductModel currentProduct;
        try {
            if(rs.getString("maincategory").equals("Technology")){
                currentProduct = createTechnologyProductModel(rs);
            } else if (rs.getString("maincategory").equals("Food")) {
                currentProduct = createFoodProductModel(rs);
            } else if (rs.getString("maincategory").equals("Clothes")){
                currentProduct = createClothesProductModel(rs);
            } else{
                currentProduct = createProductModel(rs);
            }
            return currentProduct;
        } catch (SQLException e) {
            alert.setContentText("Cannot get product");
            alert.show();
            return null;
        }
    }
    private static ProductModel createClothesProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct= new ClothesModel(rs.getInt("id"), 
                                                    rs.getString("name"), 
                                                    rs.getString("category"), 
                                                    rs.getInt("quantity"), 
                                                    rs.getFloat("price"), 
                                                    rs.getString("size"));
        return currentProduct;
    }
    private static ProductModel createFoodProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct  = new VegetangleFruitModel(rs.getInt("id"), 
                                                                rs.getString("name"), 
                                                                rs.getString("category"), 
                                                                rs.getInt("quantity"), 
                                                                rs.getFloat("price"));
        return currentProduct;
    }
    private static ProductModel createTechnologyProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct = new TechnologyProductModel(rs.getInt("id"), 
                                                                rs.getString("name"), 
                                                                rs.getString("category"), 
                                                                rs.getInt("quantity"), 
                                                                rs.getFloat("price"), 
                                                                rs.getString("resolution"), 
                                                                rs.getBoolean("used"));
        return currentProduct;
    }
    private static ProductModel createProductModel(ResultSet rs) throws SQLException{
        ProductModel currentProduct = new ProductModel(rs.getInt("id"), 
                                                    rs.getString("name"), 
                                                    rs.getString("category"), 
                                                    rs.getInt("quantity"), 
                                                    rs.getFloat("price"));
        currentProduct.SetMainCategory(rs.getString("maincategory"));
        return currentProduct;
    }
    public static void addProduct(ProductModel pm) {
        try {
            
            pst = dbConnection.prepareStatement("insert into product(id,name,category,price,quantity,resolution,used,size,maincategory)values(?,?,?,?,?,?,?,?,?)");
            
            setBasicVarValues(pm);
            checkWhichProdToAdd(pm);
            
            pst.executeUpdate();
        } catch (Exception e) {
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
    public static void deleteProduct(int id) {
        try{
            pst = dbConnection.prepareStatement("delete from product where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException exe){
            alert.setContentText("Cannot delete product");
            alert.show();
        }
    }
    public static void updateProduct(ProductModel pm) {
        
        try{
            pst = dbConnection.prepareStatement("update product set name= ?, price= ?, category= ?, quantity= ?, resolution= ?, used= ?,size= ?, maincategory= ? where id= ?");
            
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
        }
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
    
    public static void getSpecificProduct(int id) {
        try {
            
            final String SQL = "SELECT * FROM product where id=" + id ;
            final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
            wantedProd = null;
            
            while (rs.next()) {
                String category = rs.getString(9); 
                if (category.equals("Technology")) {
                    wantedProd = createTechnologyProductModel(rs);
                    
                } else if(category.equals("Food")){
                    wantedProd = createFoodProductModel(rs);
                    
                } else if(category.equals("Clothes")){
                    wantedProd = createClothesProductModel(rs);
                    
                } else{
                    wantedProd = createProductModel(rs);
                }
            }
        } catch (SQLException e) {
            alert.setContentText("Could not get the product");
            alert.show();
        }
    }
}


