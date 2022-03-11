/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.BookingDBContext;
import dal.DepartmentDBContext;
import java.io.IOException;
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
public class AddNewOrder extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String deptName = request.getParameter("deptName");
        DepartmentDBContext ddb = new DepartmentDBContext();
        ArrayList<Department> roomModel = ddb.getRoomModel();
        request.setAttribute("roomType", roomModel);
        if (deptName != null) {
            ArrayList<Department> room = ddb.getRoomByName(deptName, "0");
            request.setAttribute("rooms", room);
        } else {
            ArrayList<Department> room = ddb.getRoomByName(roomModel.get(0).getDeptName(), "0");
            request.setAttribute("rooms", room);
        }
        request.setAttribute("tagmenu", "room");
        request.getRequestDispatcher("../view/AdminDirector/AddNewOrder.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        BookingDBContext bdb = new BookingDBContext();
        OrderWait o = new OrderWait();
        o.setOrderWaitID(0);
        o.setNoOfRoom(Integer.parseInt(request.getParameter("noOfRoom")));
        o.setCheckIn(Date.valueOf(request.getParameter("checkin")));
        o.setCheckOut(Date.valueOf(request.getParameter("checkout")));
        Department d = new Department();
        d.setDeptName(request.getParameter("deptName"));
        o.setDepartment(d);
        // get customer
        Customer c = new Customer();
        c.setAddress(request.getParameter("address"));
        c.setCustomerName(request.getParameter("customerName"));
        c.setPhone(request.getParameter("phone"));
        c.setEmail(request.getParameter("email"));
        o.setCustomer(c);

        String[] roomIDs = request.getParameterValues("deptId");

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
            DepartmentDBContext ddb = new DepartmentDBContext();
            request.setAttribute("booking", b);
            request.setAttribute("deptName", d.getDeptName());
            ArrayList<Department> roomModel = ddb.getRoomModel();
            request.setAttribute("roomType", roomModel);
            ArrayList<Department> room = ddb.getRoomByName(d.getDeptName(), "wait");
            request.setAttribute("rooms", room);
            request.setAttribute("tagmenu", "room");
            request.getRequestDispatcher("../view/AdminDirector/AddNewOrder.jsp").forward(request, response);
        } else {
            bdb.insertBooking(b);
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
