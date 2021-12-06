package Controllers.DataBaseFunctions;

import java.util.*;
import java.sql.*;

import Controllers.DataBaseFunctions.ProductFunctionality.CreateModels;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBConnection extends CreateModels{
    private final static DBConnection connection = new DBConnection();
    private Alert alert = new Alert(AlertType.WARNING);
    private Connection dbConnection;
    
    private DBConnection() {
    
    }
    public void connectToDataBase(){
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
            alert.show();
        }
    }
    public static DBConnection getDBConnection(){
        return connection;
    }
    public static Connection getTheConnectionToTheDB(){
        return connection.dbConnection;
    }
}


