/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HotelController;

import dal.DepartmentDBContext;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tom
 */
public class HomeController extends HttpServlet {

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
        DepartmentDBContext ddb = new DepartmentDBContext();
        request.setAttribute("roomType", ddb.getRoomType());
        String home = "home";
        request.setAttribute("tag", home);
        boolean flag = true;
        request.setAttribute("flag", flag);
        request.getRequestDispatcher("view/Hotel/Home.jsp").forward(request, response);

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
        String typeRoom = request.getParameter("typeRoom");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
//        String noOfPeople = request.getParameter("noOfPeople");
        boolean check = false;
        if (!checkIn.equals("") && !checkOut.equals("")) {
            Date a = Date.valueOf(request.getParameter("checkIn"));
            Date b = Date.valueOf(request.getParameter("checkOut"));
            check = a.after(b);
        }

        if (checkIn.equals("") || checkOut.equals("") || check) {
            DepartmentDBContext ddb = new DepartmentDBContext();
            request.setAttribute("roomType", ddb.getRoomType());
            String home = "home";
            request.setAttribute("tag", home);
            boolean flag = false;
            request.setAttribute("flag", flag);
            request.getRequestDispatcher("view/Hotel/Home.jsp").forward(request, response);
        } else {
            if (typeRoom.equals("all")) {
                response.sendRedirect("room");
            } else {
                DepartmentDBContext ddb = new DepartmentDBContext();
                request.setAttribute("depts", ddb.searchRoom(typeRoom));
                String tag = "room";
                request.setAttribute("tag", tag);
                request.getRequestDispatcher("view/Hotel/Room.jsp").forward(request, response);
            }
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
