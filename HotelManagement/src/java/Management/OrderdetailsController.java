/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.BookingDBContext;
import dal.DepartmentDBContext;
import dal.OrderWaitDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingDetail;
import model.Customer;
import model.Department;
import model.OrderWait;

/**
 *
 * @author conmu
 */
public class OrderdetailsController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderID = request.getParameter("orderId");
        String rented = request.getParameter("rented");
        String deptName = request.getParameter("deptName");
//        OrderWaitDBContext odb = new OrderWaitDBContext();
        DepartmentDBContext ddb = new DepartmentDBContext();
        BookingDBContext bdb = new BookingDBContext();
        BookingDetail booking = bdb.getBookingDetail(Integer.parseInt(orderID));
        request.setAttribute("booking", booking);
        ArrayList<Department> roomModel = ddb.getRoomModel();
        request.setAttribute("roomType", roomModel);
        ArrayList<Department> rooms = ddb.getRoomByName(deptName);
        request.setAttribute("rooms", rooms);
        request.setAttribute("rented", rented);
        request.setAttribute("deptName", deptName);
        request.getRequestDispatcher("../view/AdminDirector/OrderDetails.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
//       response.getWriter().print(type);
        if (type.equals("wait")) {
            setRoomForOrfer(request, response);
        } else {
            updateBooking(request, response);
        }
    }

    private void setRoomForOrfer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buttonValue = request.getParameter("button");
//        response.getWriter().print(buttonValue);
        if (buttonValue.equals("cancel")) {
            OrderWaitDBContext odb = new OrderWaitDBContext();
            odb.deleteOrder(request.getParameter("orderId"));
//            response.sendRedirect("orders");
        } else {
            BookingDBContext bdb = new BookingDBContext();
            OrderWait o = new OrderWait();
            o.setOrderWaitID(Integer.parseInt(request.getParameter("orderId")));
            o.setNoOfRoom(Integer.parseInt(request.getParameter("noOfRoom")));
            Customer c = new Customer();
            c.setCustomerID(Integer.parseInt(request.getParameter("customerId")));
            o.setCustomer(c);
            String[] roomIDs = request.getParameterValues("deptId");

            ArrayList<Department> rooms = new ArrayList<>();
            for (String roomID : roomIDs) {
                Department d = new Department();
                d.setDeptID(Integer.parseInt(roomID));
//                response.getWriter().print(d.getDeptID()+ " ");
                rooms.add(d);
            }
//            response.getWriter().print("\n" + o.getCustomer().getCustomerID() + " " + o.getNoOfRoom() + " " + o.getOrderWaitID());
            BookingDetail b = new BookingDetail();
            b.setOrderWait(o);
            b.setDepartments(rooms);
            bdb.insertBooking(b);
        }
        response.sendRedirect("orders");
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buttonValue = request.getParameter("button");
        BookingDBContext bdb = new BookingDBContext();
        if (buttonValue.equals("cancel")) {
            OrderWait o = new OrderWait();
            o.setOrderWaitID(Integer.parseInt(request.getParameter("orderId")));
            Customer c = new Customer();
            c.setCustomerID(Integer.parseInt(request.getParameter("customerID")));
            o.setCustomer(c);
            bdb.cancelBookingDetail(o);
            response.sendRedirect("orders");
        } else {
//            Date checkin = Date.valueOf(buttonValue);
            //get orderWait
            OrderWait o = new OrderWait();
            o.setOrderWaitID(Integer.parseInt(request.getParameter("orderId")));
            o.setNoOfRoom(Integer.parseInt(request.getParameter("noOfRoom")));
            o.setCheckIn(Date.valueOf(request.getParameter("checkin")));
            o.setCheckOut(Date.valueOf(request.getParameter("checkout")));
            Department d = new Department();
            d.setDeptName(request.getParameter("deptName"));
            o.setDepartment(d);
            // get customer
            Customer c = new Customer();
            c.setCustomerID(Integer.parseInt(request.getParameter("customerID")));
            c.setAddress(request.getParameter("address"));
            c.setCustomerName(request.getParameter("customerName"));
            c.setPhone(request.getParameter("phone"));
            c.setEmail(request.getParameter("email"));
            o.setCustomer(c);

            String[] roomIDs = request.getParameterValues("deptId");

            // update bookingDetail
            ArrayList<Department> rooms = new ArrayList<>();
            for (String roomID : roomIDs) {
                Department r = new Department();
                r.setDeptID(Integer.parseInt(roomID));
                rooms.add(r);
            }

            BookingDetail b = new BookingDetail();
            b.setDepartments(rooms);
            b.setOrderWait(o);
            //check checkin-out valid
            if (o.getCheckIn().after(o.getCheckOut())) {
                request.setAttribute("checkDate", false);
            } else {
                bdb.updateInforBooking(b);
                request.setAttribute("tag", "done");
            }

            DepartmentDBContext ddb = new DepartmentDBContext();
            request.setAttribute("booking", b);
            request.setAttribute("deptName", d.getDeptName());
            ArrayList<Department> roomModel = ddb.getRoomModel();
            request.setAttribute("roomType", roomModel);
            ArrayList<Department> room = ddb.getRoomByName(d.getDeptName());
            request.setAttribute("rooms", room);
            request.setAttribute("rented", "1");
//        response.getWriter().print(orderID + " " + rented);
            request.getRequestDispatcher("../view/AdminDirector/OrderDetails.jsp").forward(request, response);

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
