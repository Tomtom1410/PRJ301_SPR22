/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HotelController;

import dal.FeedBackDBContext;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.Feedback;

/**
 *
 * @author Tom
 */
public class ContactController extends HttpServlet {

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
        String tag = "contact";

        request.setAttribute("tag", tag);
        request.getRequestDispatcher("view/Hotel/Contact.jsp").forward(request, response);
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
        Customer c = new Customer();
        c.setCustomerName(request.getParameter("name"));
        c.setEmail(request.getParameter("email"));
        c.setPhone(request.getParameter("phone"));
        c.setAddress(request.getParameter("address"));

        Feedback f = new Feedback();
        f.setCustomer(c);
        f.setFeedbackContent(request.getParameter("feedback"));
        LocalDate date = java.time.LocalDate.now();
        f.setDate(Date.valueOf(date));

        FeedBackDBContext fdb = new FeedBackDBContext();
        fdb.insertFeedBack(f);
        response.sendRedirect("Contact");
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
