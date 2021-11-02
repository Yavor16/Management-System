package ManagementProgramProj.Main.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBConnection {
    static Connection dbConnection = null;
    static PreparedStatement pst;
    
    public static Map<Integer, ProductModel> product = new HashMap<>();

    public static void Connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/productsdb";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "Jokojok16.");

            dbConnection = DriverManager.getConnection(url, info);
        } 
        catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while connecting MySQL database");
                ex.printStackTrace();
        }
    }
    public static void GetProducts() throws SQLException{

        final String SQL = "SELECT * from product";
        final ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
        ProductModel currentProduct;
        product.clear();
        
        while (rs.next()) {
            currentProduct = new ProductModel(rs.getInt("id"), 
                                                rs.getString("name"), 
                                                rs.getString("category"), 
                                                rs.getInt("quantity"), 
                                                rs.getFloat("price"));

            product.put(currentProduct.GetID(), currentProduct); 
        }
    }
    public static void AddProduct(int id, String name, String category, float price, int quantity) throws SQLException{
        
        pst = dbConnection.prepareStatement("insert into product(id,name,category,price,quantity)values(?,?,?,?,?)");
        
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, category);
        pst.setFloat(4, price);
        pst.setInt(5, quantity);
        
        pst.executeUpdate();
    }
    public static void DeleteProduct(int id) throws SQLException{
        
        try{
            pst = dbConnection.prepareStatement("delete from product where id= ?");
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted");
        }
        catch(Exception exe){
            JOptionPane.showMessageDialog(null, exe);
        }
    }
    public static void UpdateProduct(int id, String name, String category, float price, int quantity) throws SQLException{
        try{
             
            pst = dbConnection.prepareStatement("update product set name= ?, price= ?, category= ?, quantity= ? where id= ?");
            
            pst.setString(1, name);
            pst.setFloat(2, price);
            pst.setString(3, category);
            pst.setInt(4, quantity);
            pst.setInt(5, id);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated");
        }
        catch(Exception exe){
            JOptionPane.showMessageDialog(null, exe);

        }
    }
}


