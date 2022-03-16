/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Feedback;

/**
 *
 * @author Tom
 */
public class FeedBackDBContext extends DBContext {

    public boolean insertFeedBack(Feedback f) {
        try {
            connection.setAutoCommit(false);
            CustomerDBContext cdb = new CustomerDBContext();
            Customer c = cdb.customerExits(f.getCustomer());
            if (c == null) {
                String sql_insertC = "INSERT INTO [Customer]\n"
                        + "           ([CustomerName]\n"
                        + "           ,[Phone]\n"
                        + "           ,[Email]\n"
                        + "           ,[Address])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement stm_C = connection.prepareStatement(sql_insertC);
                stm_C.setString(1, f.getCustomer().getCustomerName());
                stm_C.setString(2, f.getCustomer().getPhone());
                stm_C.setString(3, f.getCustomer().getEmail());
                stm_C.setString(4, f.getCustomer().getAddress());
                stm_C.executeUpdate();

                String sql_getCusID = "SELECT @@IDENTITY as customerID";
                PreparedStatement stm_getCusID = connection.prepareStatement(sql_getCusID);
                ResultSet rs = stm_getCusID.executeQuery();
                if (rs.next()) {
                    c = new Customer();
                    c.setCustomerID(rs.getInt(1));
                }
            }
            String sql = "INSERT INTO [Feedback]\n"
                    + "           ([CustomerID]\n"
                    + "           ,[FeedBack]\n"
                    + "           ,[Date])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "     ,GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, c.getCustomerID());
            stm.setString(2, f.getFeedbackContent());
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex2) {
                Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        return true;
    }

    public ArrayList<Feedback> getAllFeedback(String key, int pageIndex, int pageSize) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        try {
            String sql = "SELECT *  \n"
                    + "FROM  (SELECT ROW_NUMBER() OVER (ORDER BY feedID desc) as rownum, \n"
                    + "	feedID, Customer.CustomerID, CustomerName, Phone, Email, [Address],\n"
                    + "	FeedBack.[Date], FeedBack.FeedBack	 \n"
                    + "	FROM Feedback  \n"
                    + "	inner join Customer on Customer.CustomerID = FeedBack.CustomerID\n"
                    + "	where (1=1)\n";
            if (key != null && key.trim().length() != 0) {
                sql += "and CustomerName like '%" + key + "%' or FeedBack like '%" + key + "%'\n";
            }
            sql += ")  as f  \n"
                    + "WHERE  rownum >= (?-1)*?  + 1 AND rownum <=  ?*?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                Feedback f = new Feedback();
                f.setCustomer(c);
                f.setFeedbackContent(rs.getString("FeedBack"));
                f.setDate(rs.getDate("Date"));
                f.setFeedID(rs.getInt("feedID"));
                feedbacks.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedbacks;
    }

    public Feedback getFeedbackByID(int id) {
        try {
            String sql = "SELECT \n"
                    + "	feedID, Customer.CustomerID, CustomerName, Phone, Email, [Address],\n"
                    + "	FeedBack.[Date], FeedBack.FeedBack	 \n"
                    + "	FROM Feedback  \n"
                    + "	inner join Customer on Customer.CustomerID = FeedBack.CustomerID\n"
                    + "	where feedID = ?\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                Feedback f = new Feedback();
                f.setCustomer(c);
                f.setFeedbackContent(rs.getString("FeedBack"));
                f.setDate(rs.getDate("Date"));
                f.setFeedID(rs.getInt("feedID"));
                return f;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int toltalFeedback(String key) {
        try {
            String sql = "SELECT count (*)"
                    + "	FROM Feedback  \n"
                    + "	inner join Customer on Customer.CustomerID = FeedBack.CustomerID\n"
                    + "	where (1=1)\n";
            if (key != null && key.trim().length() != 0) {
                sql += "and CustomerName like '%" + key + "%' or FeedBack like '%" + key + "%'\n";
            };
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public static void main(String[] args) {
        FeedBackDBContext fdb = new FeedBackDBContext();
        Feedback f = new Feedback();
        f.setFeedbackContent("ádhasdhsgdahda");
        Customer c = new Customer();
        c.setCustomerName("Tép");
        c.setPhone("6532156323");
        c.setEmail("tep@tep.com");
        f.setCustomer(c);
        fdb.insertFeedBack(f);
//        for (Feedback feedback : fdb.getAllFeedback(null, 1, 10)) {
//            System.out.println(feedback.getFeedID() + " " + feedback.getCustomer().getCustomerName());
//        }

//        System.out.println(fdb.getFeedbackByID(1).getCustomer().getCustomerName());
    }
}
