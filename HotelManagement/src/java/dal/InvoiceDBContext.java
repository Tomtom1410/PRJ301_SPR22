package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Invoice;

/**
 *
 * @author Tom
 */
public class InvoiceDBContext extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public void insertInvoice(int toltalPrice, int customerID) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [Invoice]\n"
                    + "           ([totalPrice]\n"
                    + "           ,[CustomerID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, toltalPrice);
            stm.setInt(2, customerID);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Invoice getInvoiceByCustomer(int customerID) {
        try {
            String sql = "select invoiceID, Customer.CustomerID, CustomerName, Phone, Email, [Address],\n"
                    + "		totalPrice\n"
                    + "from Invoice \n"
                    + "inner join Customer on Customer.CustomerID = Invoice.CustomerID\n"
                    + "where Customer.CustomerID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, customerID);
            rs = stm.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));

                Invoice inv = new Invoice();
                inv.setCustomer(c);
                inv.setInvoiceID(rs.getInt("invoiceID"));
                inv.setTotalPrice(rs.getInt("totalPrice"));
                return inv;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateInvoice(int toltalPrice, int customerID) {
        try {
            connection.setAutoCommit(false);
            String sql_updateInvoice = "UPDATE [Invoice]\n"
                    + "   SET [totalPrice] = ?\n"
                    + " WHERE CustomerID = ?";
            stm = connection.prepareStatement(sql_updateInvoice);
            stm.setInt(1, toltalPrice);
            stm.setInt(2, customerID);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
