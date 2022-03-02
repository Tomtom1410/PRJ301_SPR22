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
import model.BookingDetail;
import model.Customer;
import model.Department;
import model.OrderWait;

/**
 *
 * @author Tom
 */
public class BookingDBContext extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public void insertBooking(BookingDetail b) {
        try {
            connection.setAutoCommit(false);
            for (Department d : b.getDepartments()) {
                String sql = "INSERT INTO [Booking_Detail]\n"
                        + "           ([orderWaitID]\n"
                        + "           ,[cancel]\n"
                        + "           ,[deptID])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?)";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, b.getOrderWait().getOrderWaitID());
                stm.setBoolean(2, false);
                stm.setInt(3, d.getDeptID());
                stm.executeUpdate();

                String sql_updateDept = "UPDATE [Department]\n"
                        + "   SET [Status] = ?\n"
                        + " WHERE deptID = ?";
                PreparedStatement stm_dept = connection.prepareStatement(sql_updateDept);
                stm_dept.setBoolean(1, true);
                stm_dept.setInt(2, d.getDeptID());
                stm_dept.executeUpdate();
            }

            String sql_order = "UPDATE [OrderWait]\n"
                    + "   SET [Rented] = ?\n"
                    + " WHERE orderWaitID = ?";
            PreparedStatement stm_updateO = connection.prepareStatement(sql_order);
            stm_updateO.setBoolean(1, true);
            stm_updateO.setInt(2, b.getOrderWait().getOrderWaitID());
            stm_updateO.executeUpdate();

            InvoiceDBContext idb = new InvoiceDBContext();
            idb.insertInvoice(computeTotalPrice(b.getOrderWait().getOrderWaitID()), b.getOrderWait().getCustomer().getCustomerID());

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int computeTotalPrice(int orderID) {
        try {
            String sql_totalPrice = "select Booking_Detail.orderWaitID, Customer.CustomerID,\n"
                    + "CASE\n"
                    + "    WHEN CheckIn = CheckOut THEN SUM(price)*1\n"
                    + "    ELSE SUM(price)*DATEDIFF(day,CheckIn,CheckOut)\n"
                    + "END as totalPrice\n"
                    + "from Department\n"
                    + "inner join Booking_Detail on Booking_Detail.deptID = Department.deptID\n"
                    + "inner join OrderWait on OrderWait.orderWaitID = Booking_Detail.orderWaitID\n"
                    + "inner join Customer on Customer.CustomerID = OrderWait.CustomerID\n"
                    + "where Booking_Detail.orderWaitID = ?\n"
                    + "group by Booking_Detail.orderWaitID, CheckIn, CheckOut,Customer.CustomerID";
            stm = connection.prepareStatement(sql_totalPrice);
            stm.setInt(1, orderID);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalPrice");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<BookingDetail> getAllBookingDetails(int pageIndex, int pageSize, String cancel) {
        ArrayList<BookingDetail> bookingDetails = new ArrayList<>();
        try {
            String sql = "with b as (\n"
                    + "	select distinct Booking_Detail.orderWaitID,\n"
                    + "			Customer.CustomerID, CustomerName, [Address], Phone, Email,\n"
                    + "			CheckIn, CheckOut, noOfRooms, deptName, Rented, cancel\n"
                    + "	from Booking_Detail\n"
                    + "	inner join OrderWait on OrderWait.orderWaitID = Booking_Detail.orderWaitID\n"
                    + "	inner join Customer on OrderWait.CustomerID = Customer.CustomerID\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM  (SELECT ROW_NUMBER() OVER (ORDER BY OrderWaitID desc) as rownum,\n"
                    + "			orderWaitID, CustomerID, CustomerName, [Address], Phone, Email,\n"
                    + "			CheckIn, CheckOut, noOfRooms, deptName, Rented, cancel\n"
                    + "		 from b) as o\n"
                    + "WHERE  rownum >= (? - 1)*? + 1 AND rownum <= ? * ?\n";

            if (cancel.equals("true")) {
                sql += "AND cancel = 1";
            }
            if (cancel.equals("false")) {
                sql += "AND cancel = 0";
            }

            stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                BookingDetail b = new BookingDetail();
                b.setCancel(rs.getBoolean("cancel"));

                OrderWait o = new OrderWait();
                o.setOrderWaitID(rs.getInt("orderWaitID"));
                o.setNoOfRoom(rs.getInt("noOfRooms"));
                o.setRented(true);
                o.setCheckIn(rs.getDate("CheckIn"));
                o.setCheckOut(rs.getDate("CheckOut"));
                Department d = new Department();
                d.setDeptName(rs.getString("deptName"));
                o.setDepartment(d);
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                o.setCustomer(c);
                b.setOrderWait(o);

                bookingDetails.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookingDetails;
    }

    public BookingDetail getBookingDetail(int orderWaitID) {
        try {
            String sql = "SELECT DISTINCT BookingID, OrderWait.orderWaitID, noOfRooms, Customer.CustomerID, \n"
                    + "		CustomerName, Customer.Email, Phone, [Address], Rented, CheckIn, CheckOut,\n"
                    + "		deptID, deptName, cancel\n"
                    + "FROM Booking_Detail\n"
                    + "inner JOIN OrderWait ON OrderWait.orderWaitID = Booking_Detail.orderWaitID\n"
                    + "INNER JOIN Customer ON OrderWait.CustomerID = Customer.CustomerID\n"
                    + "WHERE OrderWait.orderWaitID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderWaitID);
            rs = stm.executeQuery();
            BookingDetail bookingDetail = null;
            OrderWait o = new OrderWait();
            while (rs.next()) {
                if (bookingDetail == null) {
                    o = new OrderWait();
                    o.setOrderWaitID(rs.getInt("orderWaitID"));
                    o.setNoOfRoom(rs.getInt("noOfRooms"));
                    o.setRented(true);
                    o.setCheckIn(rs.getDate("CheckIn"));
                    o.setCheckOut(rs.getDate("CheckOut"));
                    Department d = new Department();
                    d.setDeptName(rs.getString("deptName"));
                    o.setDepartment(d);
                    Customer c = new Customer();
                    c.setCustomerID(rs.getInt("CustomerID"));
                    c.setCustomerName(rs.getString("CustomerName"));
                    c.setEmail(rs.getString("Email"));
                    c.setPhone(rs.getString("Phone"));
                    c.setAddress(rs.getString("Address"));
                    o.setCustomer(c);

                    bookingDetail = new BookingDetail();
                    bookingDetail.setOrderWait(o);
                    bookingDetail.setCancel(rs.getBoolean("cancel"));
                }
                Department d = new Department();
                d.setDeptID(rs.getInt("deptID"));
                bookingDetail.getDepartments().add(d);
            }

            return bookingDetail;
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int totalRowBookingDetail(String cancel) {
        try {
            String sql = "with b as (\n"
                    + "	select distinct Booking_Detail.orderWaitID,\n"
                    + "			Customer.CustomerID, CustomerName, [Address], Phone, Email,\n"
                    + "			CheckIn, CheckOut, noOfRooms, deptName, Rented, cancel\n"
                    + "	from Booking_Detail\n"
                    + "	inner join OrderWait on OrderWait.orderWaitID = Booking_Detail.orderWaitID\n"
                    + "	inner join Customer on OrderWait.CustomerID = Customer.CustomerID\n"
                    + ")\n"
                    + "\n"
                    + "select COUNT(*) as totalRow\n"
                    + "from b\n";
            if (cancel.equals("false")) {
                sql += "where cancel = 0";
            }
            if (cancel.equals("true")) {
                sql += "where cancel = 1";
            }
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public void updateInforBooking(BookingDetail b) {
        try {
            connection.setAutoCommit(false);
            ArrayList<BookingDetail> bookingOfOrder = getBookingOfOrder(b.getOrderWait().getOrderWaitID());
            for (BookingDetail bookingOrder : bookingOfOrder) {
                String sql_deptFalse = "UPDATE [Department]\n"
                        + "   SET [Status] = ?\n"
                        + " WHERE deptID = ?";
                stm = connection.prepareStatement(sql_deptFalse);
                stm.setBoolean(1, false);
                stm.setInt(2, bookingOrder.getDepartments().get(0).getDeptID());
                stm.executeUpdate();
            }

            String sql_book = "DELETE FROM [Booking_Detail]\n"
                    + "      WHERE orderWaitID = ?";
            stm = connection.prepareStatement(sql_book);
            stm.setInt(1, b.getOrderWait().getOrderWaitID());
            stm.executeUpdate();

            for (Department d : b.getDepartments()) {
                String sql_inserBooking = "INSERT INTO [Booking_Detail]\n"
                        + "           ([orderWaitID]\n"
                        + "           ,[cancel]\n"
                        + "           ,[deptID])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?)";
                stm = connection.prepareStatement(sql_inserBooking);
                stm.setInt(1, b.getOrderWait().getOrderWaitID());
                stm.setBoolean(2, false);
                stm.setInt(3, d.getDeptID());
                stm.executeUpdate();

                String sql_updateDept = "UPDATE [Department]\n"
                        + "   SET [Status] = ?\n"
                        + " WHERE deptID = ?";
                PreparedStatement stm_dept = connection.prepareStatement(sql_updateDept);
                stm_dept.setBoolean(1, true);
                stm_dept.setInt(2, d.getDeptID());
                stm_dept.executeUpdate();
            }

            InvoiceDBContext idb = new InvoiceDBContext();
            idb.updateInvoice(computeTotalPrice(b.getOrderWait().getOrderWaitID()), b.getOrderWait().getCustomer().getCustomerID());

            CustomerDBContext cdb = new CustomerDBContext();
            cdb.updateCustomer(b.getOrderWait().getCustomer());

            OrderWaitDBContext odb = new OrderWaitDBContext();
            odb.updateOrder(b.getOrderWait());
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<BookingDetail> getBookingOfOrder(int orderID) {
        ArrayList<BookingDetail> bookingDetails = new ArrayList<>();
        try {
            String sql = "select BookingID, deptID from Booking_Detail\n"
                    + "where orderWaitID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, orderID);
            rs = stm.executeQuery();
            while (rs.next()) {
                BookingDetail b = new BookingDetail();
                b.setBookingID(rs.getInt("BookingID"));
                Department d = new Department();
                d.setDeptID(rs.getInt("deptID"));
                b.getDepartments().add(d);
                bookingDetails.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookingDetails;
    }

    public void cancelBookingDetail(OrderWait o) {
        try {
            ArrayList<BookingDetail> bookingOfOrder = getBookingOfOrder(o.getOrderWaitID());
            for (BookingDetail bookingOrder : bookingOfOrder) {
                String sql_deptFalse = "UPDATE [Department]\n"
                        + "   SET [Status] = ?\n"
                        + " WHERE deptID = ?";
                stm = connection.prepareStatement(sql_deptFalse);
                stm.setBoolean(1, false);
                stm.setInt(2, bookingOrder.getDepartments().get(0).getDeptID());
                stm.executeUpdate();
            }

            String sql = "UPDATE [Booking_Detail]\n"
                    + "   SET [cancel] = 1\n"
                    + "      ,[deptID] = null"
                    + " WHERE orderWaitID = ?";
            PreparedStatement stm_updateB = connection.prepareStatement(sql);
            stm_updateB.setInt(1, o.getOrderWaitID());
            stm_updateB.executeUpdate();

            String sql_invoice = "UPDATE [Invoice]\n"
                    + "   SET [totalPrice] = null\n"
                    + " WHERE CustomerID = ?";
            PreparedStatement stm_updateIn = connection.prepareStatement(sql_invoice);
            stm_updateIn.setInt(1, o.getCustomer().getCustomerID());
            stm_updateIn.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<BookingDetail> getBookingWithOutPage(String cancel) {
        ArrayList<BookingDetail> bookingDetails = new ArrayList<>();
        try {
            String sql = "select distinct Booking_Detail.orderWaitID,\n"
                    + "    Customer.CustomerID, CustomerName, [Address], Phone, Email,\n"
                    + "CheckIn, CheckOut, noOfRooms, deptName, Rented, cancel \n"
                    + "from Booking_Detail \n"
                    + "inner join OrderWait on OrderWait.orderWaitID = Booking_Detail.orderWaitID \n"
                    + "inner join Customer on OrderWait.CustomerID = Customer.CustomerID\n";

            if (cancel.equals("true")) {
                sql += "where cancel = 1";
            }
            if (cancel.equals("false")) {
                sql += "where cancel = 0";
            }

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                BookingDetail b = new BookingDetail();
                b.setCancel(rs.getBoolean("cancel"));

                OrderWait o = new OrderWait();
                o.setOrderWaitID(rs.getInt("orderWaitID"));
                o.setNoOfRoom(rs.getInt("noOfRooms"));
                o.setRented(true);
                o.setCheckIn(rs.getDate("CheckIn"));
                o.setCheckOut(rs.getDate("CheckOut"));
                Department d = new Department();
                d.setDeptName(rs.getString("deptName"));
                o.setDepartment(d);
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                o.setCustomer(c);
                b.setOrderWait(o);

                bookingDetails.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookingDetails;
    }

    public ArrayList<BookingDetail> searchBookingDetails(String value, String condition) {
        ArrayList<BookingDetail> bookingDetails = new ArrayList<>();
        try {
            String sql = "select distinct Booking_Detail.orderWaitID, \n"
                    + "        Customer.CustomerID, CustomerName, [Address], Phone, Email, \n"
                    + "        CheckIn, CheckOut, noOfRooms, deptName, Rented, cancel \n"
                    + "from Booking_Detail \n"
                    + "inner join OrderWait on OrderWait.orderWaitID = Booking_Detail.orderWaitID \n"
                    + "inner join Customer on OrderWait.CustomerID = Customer.CustomerID\n"
                    + "where CustomerName like ?";
            if (condition.equals("0")) {
                sql += " AND cancel = 0";
            }
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + value + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                BookingDetail b = new BookingDetail();
                b.setCancel(rs.getBoolean("cancel"));

                OrderWait o = new OrderWait();
                o.setOrderWaitID(rs.getInt("orderWaitID"));
                o.setNoOfRoom(rs.getInt("noOfRooms"));
                o.setRented(true);
                o.setCheckIn(rs.getDate("CheckIn"));
                o.setCheckOut(rs.getDate("CheckOut"));
                Department d = new Department();
                d.setDeptName(rs.getString("deptName"));
                o.setDepartment(d);
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setCustomerName(rs.getString("CustomerName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                o.setCustomer(c);
                b.setOrderWait(o);

                bookingDetails.add(b);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookingDetails;
    }

//    public static void main(String[] args) {
//        BookingDBContext bdb = new BookingDBContext();
////        BookingDetail b = bdb.getBookingDetail(2);
////        System.out.println(b.getOrderWait().getCheckIn());
//        for (BookingDetail b : bdb.getAllBookingDetails(2, 1, "false")) {
//            System.out.println(b.getOrderWait().getOrderWaitID() + " " + b.getOrderWait().getCustomer().getCustomerName());
//        }
//        System.out.println(bdb.totalRowBookingDetail("false"));
//        System.out.println(bdb.computeTotalPrice(17));
//    }
}
