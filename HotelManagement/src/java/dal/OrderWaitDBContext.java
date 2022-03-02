package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Department;
import model.OrderWait;

public class OrderWaitDBContext extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public void orderWait(OrderWait o) {
        try {
            connection.setAutoCommit(false);
            CustomerDBContext cdb = new CustomerDBContext();
            Customer c = cdb.customerExits(o.getCustomer());
            if (c == null) {
                String sql_insertCus = "INSERT INTO [Customer]\n"
                        + "           ([CustomerName]\n"
                        + "           ,[Phone]\n"
                        + "           ,[Email]\n)"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement stm_cus = connection.prepareStatement(sql_insertCus);
                stm_cus.setString(1, o.getCustomer().getCustomerName());
                stm_cus.setString(2, o.getCustomer().getPhone());
                stm_cus.setString(3, o.getCustomer().getEmail());
                stm_cus.executeUpdate();
                String sql_getCusID = "SELECT @@IDENTITY as customerID";
                PreparedStatement stm_getCusID = connection.prepareStatement(sql_getCusID);
                rs = stm_getCusID.executeQuery();
                if (rs.next()) {
                    o.getCustomer().setCustomerID(rs.getInt("customerID"));
                }
            } else {
                o.getCustomer().setCustomerID(c.getCustomerID());
            }

            String sql_insertOrder = "INSERT INTO[OrderWait]\n"
                    + "           ([deptName]\n"
                    + "           ,[CustomerID]\n"
                    + "           ,[CheckIn]\n"
                    + "           ,[CheckOut]\n"
                    + "           ,[noOfRooms]\n"
                    + "           ,[Rented])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_o = connection.prepareStatement(sql_insertOrder);
            stm_o.setString(1, o.getDepartment().getDeptName());
            stm_o.setInt(2, o.getCustomer().getCustomerID());
            stm_o.setDate(3, o.getCheckIn());
            stm_o.setDate(4, o.getCheckOut());
            stm_o.setInt(5, o.getNoOfRoom());
            stm_o.setBoolean(6, false);
            stm_o.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<OrderWait> getInformationOrderWait(int pageIndex, int pageSize, boolean rented) {
        ArrayList<OrderWait> orderWaits = new ArrayList<>();
        try {
            String sql = "SELECT * \n"
                    + " FROM  (SELECT ROW_NUMBER() OVER (ORDER BY OrderWaitID desc) as rownum,\n"
                    + "			Customer.CustomerID, CustomerName, Phone, Email, [Address]	\n"
                    + "			,orderWaitID, deptName, CheckIn, CheckOut, noOfRooms,Rented\n"
                    + "		FROM OrderWait \n"
                    + "		inner join Customer on Customer.CustomerID = OrderWait.CustomerID)  as O \n"
                    + "WHERE  rownum >= (? - 1)*? + 1 AND rownum <= ? * ? AND Rented = ? ";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            stm.setBoolean(5, rented);
            rs = stm.executeQuery();
            while (rs.next()) {
                OrderWait o = new OrderWait();
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                o.setCustomer(c);

                Department d = new Department();
                d.setDeptName(rs.getString("deptName"));
                o.setDepartment(d);
                o.setNoOfRoom(rs.getInt("noOfRooms"));
                o.setCheckIn(rs.getDate("CheckIn"));
                o.setCheckOut(rs.getDate("CheckOut"));
                o.setOrderWaitID(rs.getInt("orderWaitID"));
                o.setRented(rs.getBoolean("Rented"));
                orderWaits.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderWaits;
    }

    public OrderWait checkChangeInforOrder(OrderWait o) {
        try {
            String sql = "select * from OrderWait\n"
                    + "where orderWaitID = ? and CustomerID = ? \n"
                    + "and CheckIn = ? and CheckOut = ? and deptName = ?\n"
                    + "and noOfRooms = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, o.getOrderWaitID());
            stm.setInt(2, o.getCustomer().getCustomerID());
            stm.setDate(3, o.getCheckIn());
            stm.setDate(4, o.getCheckOut());
            stm.setString(5, o.getDepartment().getDeptName());
            stm.setInt(6, o.getNoOfRoom());

            rs = stm.executeQuery();

            if (rs.next()) {
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void updateOrder(OrderWait o) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [OrderWait]\n"
                    + "   SET [deptName] = ?\n"
                    + "      ,[CheckIn] = ?\n"
                    + "      ,[CheckOut] = ?\n"
                    + "      ,[noOfRooms] = ?\n"
                    + "      ,[Rented] = ?"
                    + " WHERE orderWaitID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, o.getDepartment().getDeptName());
            stm.setDate(2, o.getCheckIn());
            stm.setDate(3, o.getCheckOut());
            stm.setInt(4, o.getNoOfRoom());
            stm.setBoolean(5, true);
            stm.setInt(6, o.getOrderWaitID());
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int totalRow(boolean rented) {
        try {
            String sql = "select COUNT(*) as totalRow \n"
                    + "from OrderWait\n"
                    + "where Rented = ?";
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, rented);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt("totalRow");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void deleteOrder(String orderID) {
        try {
            String sql = "DELETE FROM [OrderWait]\n"
                    + "      WHERE orderWaitID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, orderID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<OrderWait> searchOrder(String value) {
        ArrayList<OrderWait> orderWaits = new ArrayList<>();
        try {
            String sql = "select orderWaitID, Customer.CustomerID, CustomerName, Phone, [Address], Email,\n"
                    + "	noOfRooms, CheckIn, CheckOut, Rented, deptName\n"
                    + "from OrderWait\n"
                    + "inner join Customer on Customer.CustomerID = OrderWait.CustomerID\n"
                    + "where CustomerName like ? and Rented = 0";
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + value + "%");
            rs = stm.executeQuery();

            while (rs.next()) {
                OrderWait o = new OrderWait();
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                o.setCustomer(c);

                Department d = new Department();
                d.setDeptName(rs.getString("deptName"));
                o.setDepartment(d);
                o.setNoOfRoom(rs.getInt("noOfRooms"));
                o.setCheckIn(rs.getDate("CheckIn"));
                o.setCheckOut(rs.getDate("CheckOut"));
                o.setOrderWaitID(rs.getInt("orderWaitID"));
                o.setRented(rs.getBoolean("Rented"));
                orderWaits.add(o);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderWaits;
    }

//    public static void main(String[] args) {
//        OrderWaitDBContext odb = new OrderWaitDBContext();
//        for (OrderWait orderWait : odb.getInformationOrderWait(1, 10, true)) {
//            System.out.println(orderWait.getOrderWaitID());
//        }
//    }
}
