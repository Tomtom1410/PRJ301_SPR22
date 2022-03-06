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

    public int insertOrder(OrderWait o) {
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
                    + "           ,[Rented]\n"
                    + "           ,[Cancel])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?"
                    + "           ,0)";
            PreparedStatement stm_o = connection.prepareStatement(sql_insertOrder);
            stm_o.setString(1, o.getDepartment().getDeptName());
            stm_o.setInt(2, o.getCustomer().getCustomerID());
            stm_o.setDate(3, o.getCheckIn());
            stm_o.setDate(4, o.getCheckOut());
            stm_o.setInt(5, o.getNoOfRoom());
            stm_o.setBoolean(6, false);
            stm_o.executeUpdate();

            String sql_getOrderID = "SELECT @@IDENTITY as orderId";
            PreparedStatement stm_getCusID = connection.prepareStatement(sql_getOrderID);
            rs = stm_getCusID.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderId");
            }

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
        return -1;
    }

    public ArrayList<OrderWait> getInformationOrderWait(int pageIndex, int pageSize, String rented, String key) {
        ArrayList<OrderWait> orderWaits = new ArrayList<>();
        try {
            String sql = "SELECT * \n"
                    + " FROM  (SELECT ROW_NUMBER() OVER (ORDER BY OrderWaitID desc) as rownum,\n"
                    + "			Customer.CustomerID, CustomerName, Phone, Email, [Address]	\n"
                    + "			,orderWaitID, deptName, CheckIn, CheckOut, noOfRooms,Rented\n"
                    + "		FROM OrderWait \n"
                    + "		inner join Customer on Customer.CustomerID = OrderWait.CustomerID\n"
                    + "         where cancel = 0 and Rented = " + rented + "\n";
            if (key != null) {
                sql += "        and CustomerName like '%" + key + "%' or phone like '%" + key + "%'\n";
            }
            sql += ")  as O \n"
                    + "WHERE  rownum >= (? - 1)*? + 1 AND rownum <= ? * ?";
//            System.out.println(sql);
            stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
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

    public int totalRow(String rented, String key) {
        try {
            String sql = "SELECT Count(*)"
                    + "		FROM OrderWait \n"
                    + "		inner join Customer on Customer.CustomerID = OrderWait.CustomerID\n"
                    + "         where Rented = ?";
            if (key != null) {
                sql += "        and CustomerName like '%" + key + "%' or phone like '%" + key + "%'\n";
            }
            stm = connection.prepareStatement(sql);
            stm.setString(1, rented);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public OrderWait getOrderById(int orderId) {
        try {
            String sql = "SELECT Customer.CustomerID, CustomerName, Phone, Email, [Address]	\n"
                    + "       , deptName, CheckIn, CheckOut, noOfRooms, Rented, orderWaitID\n"
                    + "FROM OrderWait \n"
                    + "inner join Customer on Customer.CustomerID = OrderWait.CustomerID\n"
                    + "where orderWaitID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderId);
            rs = stm.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt(1));
                c.setCustomerName(rs.getString(2));
                c.setPhone(rs.getString(3));
                c.setEmail(rs.getString(4));
                c.setAddress(rs.getString(5));
                Department d = new Department();
                d.setDeptName(rs.getString(6));
                OrderWait o = new OrderWait();
                o.setCustomer(c);
                o.setDepartment(d);
                o.setCheckIn(rs.getDate(7));
                o.setCheckOut(rs.getDate(8));
                o.setNoOfRoom(rs.getInt(9));
                o.setRented(rs.getBoolean(10));
                o.setOrderWaitID(orderId);
                return o;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderWaitDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public static void main(String[] args) {
        OrderWaitDBContext odb = new OrderWaitDBContext();
//        for (OrderWait orderWait : odb.getInformationOrderWait(1, 10, "0", "096")) {
//            System.out.println(orderWait.getCustomer().getCustomerName());
//        }
        System.out.println(odb.getOrderById(1).getCustomer().getCustomerName());
    }
}
