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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingDetail;
import model.Department;

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
