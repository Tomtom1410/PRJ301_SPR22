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

    PreparedStatement stm;
    ResultSet rs;

    public void insertFeedBack(Feedback f) {
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
                rs = stm_getCusID.executeQuery();
                if (rs.next()) {
                    c = new Customer();
                    c.setCustomerID(rs.getInt("customerID"));
                }
            } else {
                String sql = "INSERT INTO [Feedback]\n"
                        + "           ([CustomerID]\n"
                        + "           ,[FeedBack]\n"
                        + "           ,[Date])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?)";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, c.getCustomerID());
                stm.setString(2, f.getFeedbackContent());
                stm.setDate(3, f.getDate());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Feedback> getFeedback(int pageIndex, int pageSize) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        try {
            String sql = "SELECT *  \n"
                    + "FROM  (SELECT ROW_NUMBER() OVER (ORDER BY feedID desc) as rownum, \n"
                    + "	feedID, Customer.CustomerID, CustomerName, Phone, Email, [Address],\n"
                    + "	FeedBack.[Date], FeedBack.FeedBack	 \n"
                    + "	FROM Feedback  \n"
                    + "	inner join Customer on Customer.CustomerID = FeedBack.CustomerID\n"
                    + "	)  as f  \n"
                    + "WHERE  rownum >= (?-1)*?  + 1 AND rownum <=  ?*?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            rs = stm.executeQuery();

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

    public int toltalFeedback() {
        try {
            String sql = "select count(*) as totalFeedBack from feedback";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public ArrayList<Feedback> searchFeedbacks(String value) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        try {
            String sql = "SELECT feedID, Customer.CustomerID, CustomerName, Phone, Email, [Address],\n"
                    + "        FeedBack.[Date], FeedBack.FeedBack \n"
                    + "from Customer \n"
                    + "inner join Feedback on Feedback.CustomerID = Customer.CustomerID\n"
                    + "where CustomerName like ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + value + "%");
            rs = stm.executeQuery();

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

//    public static void main(String[] args) {
//        FeedBackDBContext fdb = new FeedBackDBContext();
//        for (Feedback feedback : fdb.getFeedback(1, 10)) {
//            System.out.println(feedback.getFeedID() + " " + feedback.getCustomer().getCustomerName());
//        }
//
//        System.out.println(fdb.toltalFeedback());
//    }
}
