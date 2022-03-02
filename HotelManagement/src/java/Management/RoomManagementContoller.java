/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.DepartmentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Department;

/**
 *
 * @author conmu
 */
public class RoomManagementContoller extends HttpServlet {

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
        String pageindex_raw = request.getParameter("pageindex");

        if (pageindex_raw == null || pageindex_raw.trim().length() == 0) {
            pageindex_raw = "1";
        }
        int pageindex = Integer.parseInt(pageindex_raw);
        String key = request.getParameter("key");
        DepartmentDBContext ddb = new DepartmentDBContext();
        if (key != null) {
            ArrayList<Department> allRooms = ddb.getAllRooms(key, "all", "all", pageindex, pagesize);
            request.setAttribute("rooms", allRooms);
            String url = "room?key=" + key + "&pageindex=";
            int totalRows = ddb.totalRooms(key, "all", "all");
            int totalPage = (totalRows % pagesize == 0) ? totalRows / pagesize : totalRows / pagesize + 1;
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("url", url);
            request.setAttribute("key", key);
        } else {
            String type = request.getParameter("type");
            String status = request.getParameter("status");
            if (type != null && status != null) {
                ArrayList<Department> allRooms = ddb.getAllRooms(null, type, status, pageindex, pagesize);
                request.setAttribute("rooms", allRooms);
                int totalRows = ddb.totalRooms(null, type, status);
                int totalPage = (totalRows % pagesize == 0) ? totalRows / pagesize : totalRows / pagesize + 1;
                request.setAttribute("totalPage", totalPage);
                String url = "room?type=" + type + "&status=" + status + "&pageindex=";
                request.setAttribute("url", url);
                request.setAttribute("type", type);
                request.setAttribute("status", status);
            } else {
                ArrayList<Department> allRooms = ddb.getAllRooms(null, "all", "all", pageindex, pagesize);
                request.setAttribute("rooms", allRooms);
                int totalRows = ddb.totalRooms(null, "all", "all");
                int totalPage = (totalRows % pagesize == 0) ? totalRows / pagesize : totalRows / pagesize + 1;
                request.setAttribute("totalPage", totalPage);
                String url = "room?pageindex=";
                request.setAttribute("url", url);
            }
        }
        request.setAttribute("pageindex", pageindex);
        request.getRequestDispatcher("../view/AdminDirector/Room.jsp").forward(request, response);
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
        doGet(request, response);

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
