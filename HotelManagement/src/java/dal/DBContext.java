/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tom
 */
public class DBContext {
    protected Connection connection;
    public DBContext(){
        try {
            String user = "luongnthhe151453";
            String pass = "14052001";
            String url = "jdbc:sqlserver://Tom\\SQLEXPRESS:1433;databaseName=Hotel_Booking";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
    public static void main(String[] args) {
        DBContext db = new DBContext();
        System.out.println(db);
    }
}
