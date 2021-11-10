package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TreeMap;

import Models.ClothesModel;
import Models.ProductModel;
import Models.TechnologyProductModel;
import Models.VegetangleFruitModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnection {
    
    static Connection dbConnection = null;
    static PreparedStatement pst;
    
    public static TreeMap<Integer, ProductModel> product = new TreeMap<Integer, ProductModel>();

    static Alert alert = new Alert(AlertType.WARNING);

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
    public static void GetProducts() throws SQLException{

        final String SQL = "SELECT * from product";
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
        ProductModel currentProduct;
        product.clear();

        while (rs.next()) {
            if(rs.getString("maincategory").equals("Technology")){
                currentProduct = new TechnologyProductModel(rs.getInt("id"), 
                                                            rs.getString("name"), 
                                                            rs.getString("category"), 
                                                            rs.getInt("quantity"), 
                                                            rs.getFloat("price"), 
                                                            rs.getString("resolution"), 
                                                            rs.getBoolean("used"));
            } else if (rs.getString("maincategory").equals("Food")) {
                currentProduct = new VegetangleFruitModel(rs.getInt("id"), 
                                                        rs.getString("name"), 
                                                        rs.getString("category"), 
                                                        rs.getInt("quantity"), 
                                                        rs.getFloat("price"));
            } else{
                currentProduct = new ClothesModel(rs.getInt("id"), 
                                                    rs.getString("name"), 
                                                    rs.getString("category"), 
                                                    rs.getInt("quantity"), 
                                                    rs.getFloat("price"), 
                                                    rs.getString("size"));
            }
            product.put(currentProduct.GetID(), currentProduct); 
        }
    }
    public static void AddProduct(ProductModel pm) throws SQLException{
        
        pst = dbConnection.prepareStatement("insert into product(id,name,category,price,quantity,resolution,used,size,maincategory)values(?,?,?,?,?,?,?,?,?)");
        
        pst.setInt(1, pm.GetID());
        pst.setString(2, pm.GetName());
        pst.setString(3, pm.GetCategory());
        pst.setFloat(4, pm.GetPrice());
        pst.setInt(5, pm.GetQuantity());

        if (pm instanceof TechnologyProductModel) {
            TechnologyProductModel tp = (TechnologyProductModel)pm;
            int isUsed = tp.GetUsed() ? 1 : 0;
            pst.setString(6, tp.GetResolution());
            pst.setInt(7, isUsed);
            pst.setString(8, null);
            pst.setString(9, tp.GetMainCategory());    
        } else if(pm instanceof VegetangleFruitModel){
            pst.setString(6, null);
            pst.setString(7, null);
            pst.setString(8, null);
            pst.setString(9, "Food");
        } else if(pm instanceof ClothesModel){
            ClothesModel cm = (ClothesModel)pm;
            pst.setString(6, null);
            pst.setString(7, null);
            pst.setString(8, cm.GetSize());
            pst.setString(9, cm.GetMainCategory());
        }
        pst.executeUpdate();
    }
    public static void DeleteProduct(int id) throws SQLException{
        
        try{
            pst = dbConnection.prepareStatement("delete from product where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(Exception exe){
            alert.setTitle("Error");;
            alert.setContentText(exe.getLocalizedMessage());
            alert.showAndWait();
        }
    }
    public static void UpdateProduct(ProductModel pm) throws SQLException{
        
        try{
            pst = dbConnection.prepareStatement("update product set name= ?, price= ?, category= ?, quantity= ?, resolution= ?, used= ?,size= ?, maincategory= ? where id= ?");
            
            pst.setString(1, pm.GetName());
            pst.setFloat(2, pm.GetPrice());
            pst.setString(3, pm.GetCategory());
            pst.setInt(4, pm.GetQuantity());
            pst.setInt(9, pm.GetID());
            

            if (pm instanceof TechnologyProductModel ) {
                TechnologyProductModel tp = (TechnologyProductModel)pm;
                int isUsed = tp.GetUsed() ? 1 : 0;
                pst.setString(5, tp.GetResolution());
                pst.setInt(6, isUsed);
                pst.setString(7, null);
                pst.setString(8, tp.GetMainCategory());    
            } else if(pm instanceof VegetangleFruitModel ){
                pst.setString(5, null);
                pst.setString(6, null);
                pst.setString(7, null);
                pst.setString(8, pm.GetMainCategory());
            } else if(pm instanceof ClothesModel ){
                ClothesModel cm = (ClothesModel)pm;
                pst.setString(5, null);
                pst.setString(6, null);
                pst.setString(7, cm.GetSize());
                pst.setString(8, cm.GetMainCategory());
            }
            pst.executeUpdate();

        } catch(Exception exe){          
            alert.setTitle("Error");;
            alert.setContentText(exe.getLocalizedMessage());
            alert.showAndWait();
        }
    }
    public static ProductModel wantedProd;

    public static void GetSpecificProduct(int id) throws SQLException{
        System.out.println(id);
        final String SQL = "SELECT * FROM product where id=" + id ;
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
        wantedProd = null;

        while (rs.next()) {
            String category = rs.getString(9); 
            if (category.equals("Technology")) {
                wantedProd = new TechnologyProductModel(rs.getInt("id"), 
                                                        rs.getString("name"), 
                                                        rs.getString("category"), 
                                                        rs.getInt("quantity"), 
                                                        rs.getFloat("price"),
                                                        rs.getString("resolution"),
                                                        rs.getBoolean("used")); 
            } 
            else if(category.equals("Food")){
                wantedProd = new VegetangleFruitModel(rs.getInt("id"), 
                                                        rs.getString("name"), 
                                                        rs.getString("category"), 
                                                        rs.getInt("quantity"), 
                                                        rs.getFloat("price"));
            } else if(category.equals("Clothes")){
                wantedProd = new ClothesModel(rs.getInt("id"), 
                                                rs.getString("name"), 
                                                rs.getString("category"), 
                                                rs.getInt("quantity"), 
                                                rs.getFloat("price"), 
                                                rs.getString("size"));
            }
        }
    }
}


