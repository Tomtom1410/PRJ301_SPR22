/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.FeedBackDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Feedback;

/**
 *
 * @author conmu
 */
public class FeedbackController extends HttpServlet {

    private final int pagesize = 10;

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
        String tagMenu = "feedback";
        String raw_page = request.getParameter("page");
        if (raw_page == null || raw_page.length() == 0) {
            raw_page = "1";
        }
        int pageIndex = Integer.parseInt(raw_page);
        FeedBackDBContext fdb = new FeedBackDBContext();
        String key = request.getParameter("key");
        if (key != null) {
            ArrayList<Feedback> feedback = fdb.getAllFeedback(key, pageIndex, pagesize);
            int totalFeed = fdb.toltalFeedback(key);
            int totalPage = (totalFeed % pagesize == 0) ? totalFeed / pagesize : totalFeed / pagesize + 1;
            String url = "feedback?key=" + key + "&page=";
            request.setAttribute("url", url);
            request.setAttribute("tagMenu", tagMenu);
            request.setAttribute("feedbacks", feedback);
            request.setAttribute("pageindex", pageIndex);
            request.setAttribute("totalPage", totalPage);
        } else {
            ArrayList<Feedback> feedback = fdb.getAllFeedback(null, pageIndex, pagesize);
            int totalFeed = fdb.toltalFeedback(null);
            int totalPage = (totalFeed % pagesize == 0) ? totalFeed / pagesize : totalFeed / pagesize + 1;
            String url = "feedback?page=";
            request.setAttribute("url", url);
            request.setAttribute("tagmenu", tagMenu);
            request.setAttribute("feedbacks", feedback);
            request.setAttribute("pageindex", pageIndex);
            request.setAttribute("totalPage", totalPage);
        }
        request.getRequestDispatcher("../view/AdminDirector/Feedback.jsp").forward(request, response);
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
        String id = request.getParameter("feedId");
        FeedBackDBContext fdb = new FeedBackDBContext();
        Feedback feedbackByID = fdb.getFeedbackByID(Integer.parseInt(id));
        request.setAttribute("feedback", feedbackByID);
        request.setAttribute("tagmenu", "feedback");
        request.getRequestDispatcher("../view/AdminDirector/FeedbackDetails.jsp").forward(request, response);
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
