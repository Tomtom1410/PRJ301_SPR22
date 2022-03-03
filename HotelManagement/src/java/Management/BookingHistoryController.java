/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.BookingDBContext;
import dal.InvoiceDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingDetail;
import model.Invoice;

/**
 *
 * @author conmu
 */
public class BookingHistoryController extends HttpServlet {

    private final int pagesize = 3;
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
        BookingDBContext bdb = new BookingDBContext();
        String key = request.getParameter("key");
        if (key != null) {
            ArrayList<BookingDetail> allBookingDetails = bdb.getAllBookingDetails(pageIndex, pagesize, "all", key);
            request.setAttribute("bookings", allBookingDetails);

            int totalRow = bdb.totalRowBookingDetail("all", key);
            int totalPage = (totalRow % pagesize == 0) ? totalRow / pagesize : totalRow / pagesize + 1;
            request.setAttribute("pageindex", pageIndex);
            request.setAttribute("totalPage", totalPage);
            String url = "bookinghistory?key=" + key + "&page=";
            request.setAttribute("url", url);
            request.setAttribute("key", key);
        } else {
            String cancel = request.getParameter("status");
            if (cancel != null) {
                ArrayList<BookingDetail> allBookingDetails = bdb.getAllBookingDetails(pageIndex, pagesize, cancel, null);
                request.setAttribute("bookings", allBookingDetails);

                int totalRow = bdb.totalRowBookingDetail(cancel, null);
                int totalPage = (totalRow % pagesize == 0) ? totalRow / pagesize : totalRow / pagesize + 1;
                request.setAttribute("pageindex", pageIndex);
                request.setAttribute("totalPage", totalPage);
                String url = "bookinghistory?status=" + cancel + "&page=";
                request.setAttribute("url", url);
            } else {
                ArrayList<BookingDetail> allBookingDetails = bdb.getAllBookingDetails(pageIndex, pagesize, "all", null);
                request.setAttribute("bookings", allBookingDetails);

                int totalRow = bdb.totalRowBookingDetail("all", null);
                int totalPage = (totalRow % pagesize == 0) ? totalRow / pagesize : totalRow / pagesize + 1;
                request.setAttribute("pageindex", pageIndex);
                request.setAttribute("totalPage", totalPage);
                String url = "bookinghistory?page=";
                request.setAttribute("url", url);
            }

        }

        String tagMenu = "history";
        request.setAttribute("tagMenu", tagMenu);
        request.getRequestDispatcher("../view/AdminDirector/BookingHistory.jsp").forward(request, response);
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
        String orderId = request.getParameter("orderId");

        BookingDBContext bdb = new BookingDBContext();
        BookingDetail booking = bdb.getBookingDetail(Integer.parseInt(orderId));
        request.setAttribute("booking", booking);
        InvoiceDBContext idb = new InvoiceDBContext();
        Invoice invoice = idb.getInvoiceByCustomer(booking.getOrderWait().getCustomer().getCustomerID());
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher("../view/AdminDirector/BookingDetails.jsp").forward(request, response);
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
