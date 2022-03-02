/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

/**
 *
 * @author Tom
 */
public class CustomerDBContext extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public Customer getCustomer(Customer c) {
        try {
            String sql = "select * from Customer\n"
                    + "where CustomerID = ? and CustomerName = ?"
                    + " and Phone = ? and Email = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, c.getCustomerID());
            stm.setString(2, c.getCustomerName());
            stm.setString(3, c.getPhone());
            stm.setString(4, c.getEmail());
            rs = stm.executeQuery();

            if (rs.next()) {
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateCustomer(Customer c) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [Customer]\n"
                    + "   SET [CustomerName] = ?\n"
                    + "      ,[Phone] = ?\n"
                    + "      ,[Email] = ?\n"
                    + "      ,[Address] = ?\n"
                    + " WHERE CustomerID = ?";
            stm = connection.prepareCall(sql);
            stm.setString(1, c.getCustomerName());
            stm.setString(2, c.getPhone());
            stm.setString(3, c.getEmail());
            stm.setString(4, c.getAddress());
            stm.setInt(5, c.getCustomerID());
            stm.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Customer customerExits(Customer c) {
        try {
            String sql = "select * from Customer\n"
                    + "where CustomerName = ? AND Phone = ? \n"
                    + "AND   Email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, c.getCustomerName());
            stm.setString(2, c.getPhone());
            stm.setString(3, c.getEmail());
            rs = stm.executeQuery();
            if (rs.next()) {
                c.setCustomerID(rs.getInt("customerID"));
                c.setAddress(rs.getString("Address"));
                return c;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
