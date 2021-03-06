/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HotelController;

import dal.DepartmentDBContext;
import dal.OrderWaitDBContext;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.Department;
import model.OrderWait;

/**
 *
 * @author Tom
 */
public class BookingController extends HttpServlet {

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
        response.sendRedirect("room");
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
//        request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("customerName");
        String deptName = request.getParameter("roomName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String noOfRooms = request.getParameter("noOfRoom");

        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setEmail(email);
        customer.setPhone(phone);

        Department d = new Department();
        d.setDeptName(deptName);

        OrderWait o = new OrderWait();
        o.setCheckIn(Date.valueOf(checkIn));
        o.setCheckOut(Date.valueOf(checkOut));
        o.setNoOfRoom(Integer.parseInt(noOfRooms));
        o.setDepartment(d);
        o.setCustomer(customer);

        if (o.getCheckIn().after(o.getCheckOut())) {
            DepartmentDBContext ddb = new DepartmentDBContext();
            Department dt = ddb.getRoomByName(deptName, "rented").get(0);
            String url_image = dt.getUrl().get(0);
            dt.getUrl().remove(url_image);
            request.setAttribute("order", o);
            request.setAttribute("note", false);
            request.setAttribute("room", dt);
            request.setAttribute("url_image", url_image);
            String tag = "room";
            request.setAttribute("tag", tag);
            request.getRequestDispatcher("view/Hotel/RoomDetail.jsp").forward(request, response);
        } else {
            OrderWaitDBContext odb = new OrderWaitDBContext();
            odb.insertOrder(o);
            response.sendRedirect("room");
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
