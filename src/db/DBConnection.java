package db;

//Singleton design pattern --> 1 object ekak share karanawa users la godak athare  -->(Share an object through many users)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;

    private Connection connection;

    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dilco_plantaion","root","1234");
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        /*if(dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;*/
        return  (dbConnection==null)?dbConnection=new DBConnection() :dbConnection;
    }

    public Connection getConnection(){
        return  connection;
    }
}
