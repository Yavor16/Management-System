package ManagementProgramProj.Main.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {
    static Connection dbConnection = null;
    static PreparedStatement pst;
    
    public static void Connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/productsdb";
            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "Jokojok16.");

            dbConnection = DriverManager.getConnection(url, info);
        } 
        catch (SQLException ex) {
                System.out.println("An error occurred while connecting MySQL database");
                ex.printStackTrace();
        }
    }
    public static void GetProducts() throws SQLException{
        String SQL = "SELECT * from product";
        ResultSet rs = dbConnection.createStatement().executeQuery(SQL);
        //System.out.println(rs.getMetaData());

        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
    }
    public static void AddProduct(int id, String name, String category, float price, int quantity) throws SQLException{
        pst = dbConnection.prepareStatement("insert into product(id,name,category,price,quantity)values(?,?,?,?,?)");
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, category);
        pst.setFloat(4, price);
        pst.setInt(5, quantity);
        int status = pst.executeUpdate();

        if(status == 1 ){
            System.out.println("You did it!");
        }
        else{
            System.out.println("Next time!");
        }
    }
}

