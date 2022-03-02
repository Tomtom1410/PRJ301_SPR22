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
import model.Administrator;

/**
 *
 * @author Tom
 */
public class AdminstratorDBContext extends DBContext {

    public Administrator getAccount(String username, String password) {

        try {
            String sql = "SELECT * FROM systemAdministrator\n"
                    + "WHERE username = ? AND [password] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Administrator acc = new Administrator();
                acc.setUsername("username");
                acc.setPassword("password");
                return acc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminstratorDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
