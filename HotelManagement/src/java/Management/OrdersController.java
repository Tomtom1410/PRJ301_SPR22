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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingDetail;
import model.Department;
import model.OrderWait;

/**
 *
 * @author conmu
 */
public class OrdersController extends HttpServlet {

    private final int pagesize = 2;

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
        String raw_page = request.getParameter("page");
        if (raw_page == null || raw_page.length() == 0) {
            raw_page = "1";
        }
        int pageIndex = Integer.parseInt(raw_page);
        OrderWaitDBContext odb = new OrderWaitDBContext();
        String rented = request.getParameter("rented");
        if (rented == null || rented.trim().length() == 0) {
            rented = "0";
        }

        String key = request.getParameter("key");
        if (key != null && key.trim().length() != 0) {
            ArrayList<OrderWait> OrderWait = odb.getInformationOrderWait(pageIndex, pagesize, rented, key);
            request.setAttribute("orders", OrderWait);
            String tag = "order";
            request.setAttribute("tagMenu", tag);

            String title = "wait";
            request.setAttribute("title", title);

            int totalRow = odb.totalRow(rented, key);
            int totalPage = (totalRow % pagesize == 0) ? totalRow / pagesize : totalRow / pagesize + 1;
            String url = "orders?rented=" + rented + "&key=" + key + "&page=";
            request.setAttribute("url", url);
            request.setAttribute("key", key);
            request.setAttribute("totalPage", totalPage);

        } else {
            ArrayList<OrderWait> OrderWait = odb.getInformationOrderWait(pageIndex, pagesize, rented, null);
            request.setAttribute("orders", OrderWait);
            String tag = "order";
            request.setAttribute("tagMenu", tag);

            String title = "wait";
            request.setAttribute("title", title);

            int totalRow = odb.totalRow(rented, null);
            int totalPage = (totalRow % pagesize == 0) ? totalRow / pagesize : totalRow / pagesize + 1;
            String url = "orders?rented=" + rented + "&page=";
            request.setAttribute("url", url);
            request.setAttribute("totalPage", totalPage);
        }
        request.setAttribute("rented", rented);
        request.setAttribute("pageindex", pageIndex);
        request.getRequestDispatcher("../view/AdminDirector/OrdersList.jsp").forward(request, response);
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
        String orderID = request.getParameter("orderWaitID");
        String rented = request.getParameter("rented");
        OrderWaitDBContext odb = new OrderWaitDBContext();
        DepartmentDBContext ddb = new DepartmentDBContext();

        if (rented.equals("0")) {
//            String roomName = request.getParameter("roomName");
            OrderWait order = odb.getOrderById(Integer.parseInt(orderID));
            ArrayList<Department> room = ddb.getRoomByName(order.getDepartment().getDeptName());
            request.setAttribute("order", order);
            request.setAttribute("rooms", room);

        } else {
            BookingDBContext bdb = new BookingDBContext();
            BookingDetail booking = bdb.getBookingDetail(Integer.parseInt(orderID));
            request.setAttribute("booking", booking);
            ArrayList<Department> roomModel = ddb.getRoomModel();
            request.setAttribute("roomType", roomModel);
            ArrayList<Department> rooms = ddb.getRoomByName(booking.getOrderWait().getDepartment().getDeptName());
            request.setAttribute("rooms", rooms);
            request.setAttribute("deptName", booking.getOrderWait().getDepartment().getDeptName());
        }
        request.setAttribute("rented", rented);
//        response.getWriter().print(orderID + " " + rented);
        request.getRequestDispatcher("../view/AdminDirector/OrderDetails.jsp").forward(request, response);
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
