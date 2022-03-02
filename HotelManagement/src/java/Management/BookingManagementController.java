/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Management;

import dal.BookingDBContext;
import dal.DepartmentDBContext;
import dal.FeedBackDBContext;
import dal.InvoiceDBContext;
import dal.OrderWaitDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingDetail;
import model.Customer;
import model.Department;
import model.Feedback;
import model.Invoice;
import model.OrderWait;

/**
 *
 * @author conmu
 */
@WebServlet(name = "BookingManagementController", urlPatterns = {"/management/booking/*"})
public class BookingManagementController extends HttpServlet {

    private final String bookingDetailPath = "/booking/details";
    private final String bookingHistoryPath = "/booking/history";
    private final String bookingHistoryDetailPath = "/booking/historydetails";
//    private final String bookingSearchPath = "/search";
    private int pageSize = 20;

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
        String URI = request.getRequestURI().replaceFirst("\\w+/", "");
        response.getWriter().println(URI);
//        if (URI.compareTo("/management/booking") == 0) {
//            super.doGet(request, response);
//        } else
        if (URI.contains(bookingDetailPath)) {
            doGetBookingDetails(request, response);
        } else if (URI.contains(bookingHistoryPath)) {
            doGetBookingHistory(request, response);
        } else if ((URI.contains(bookingHistoryDetailPath))) {
            doGetBookingHistoryDetails(request, response);
        }
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
        String URI = request.getRequestURI().replaceFirst("\\w+/", "");
//        if (URI.compareTo("/director/setting") == 0) {
//            super.doGet(request, response);
//        } else 
        if (URI.contains(bookingDetailPath)) {
            doPostBookingDetails(request, response);
        } else if (URI.contains(bookingHistoryPath)) {
            doPostBookingHistoty(request, response);
        } else if ((URI.contains(bookingHistoryDetailPath))) {
            doPostBookingHistoryDetails(request, response);
        }
    }

    private void doGetBookingDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_page = request.getParameter("page");
        if (raw_page == null || raw_page.length() == 0) {
            raw_page = "1";
        }
        int pageIndex = Integer.parseInt(raw_page);

        int orderWaitID = Integer.parseInt(request.getParameter("orderWaitID"));
//        OrderWaitDBContext odb = new OrderWaitDBContext();
//        ArrayList<OrderWait> OrderWait = odb.getInformationOrderWait(pageIndex, pageSize, false);
//        OrderWait o = new OrderWait();
//        for (OrderWait od : OrderWait) {
//            if (od.getOrderWaitID() == orderWaitID) {
//                o = od;
//                break;
//            }
//        }
//        String title = "wait";
//        request.setAttribute("title", title);
//        int totalRow = odb.totalRow(true);
//        int totalPage = (totalRow % pageSize == 0) ? totalRow / pageSize : totalRow / pageSize + 1;
//        String url = "InformationOfCustomerWait?page=";
//        request.setAttribute("urlPage", url);
//        request.setAttribute("pageIndex", pageIndex);
//        request.setAttribute("totalPage", totalPage);
//
//        DepartmentDBContext ddb = new DepartmentDBContext();
//
//        ArrayList<Department> roomByName = ddb.getRoomForOrder(o.getDepartment().getDeptName());
//        request.setAttribute("roomByName", roomByName);
//        request.setAttribute("o", o);
//        request.setAttribute("orders", OrderWait);
//        String tag = "order";
//        request.setAttribute("tagMenu", tag);
//        request.getRequestDispatcher("../../view/Management/OrderWait.jsp").forward(request, response);
    }

    private void doPostBookingDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String buttonValue = request.getParameter("button");
        BookingDBContext bdb = new BookingDBContext();
        if (buttonValue.equals("delete")) {
            OrderWaitDBContext odb = new OrderWaitDBContext();
            odb.deleteOrder(request.getParameter("oID"));
            response.sendRedirect("../InformationOfCustomerWait");
        } else {
            OrderWait o = new OrderWait();
            o.setOrderWaitID(Integer.parseInt(request.getParameter("oID")));
            o.setNoOfRoom(Integer.parseInt(request.getParameter("noOfRoom")));
            Customer c = new Customer();
            c.setCustomerID(Integer.parseInt(request.getParameter("customerID")));
            o.setCustomer(c);
            String[] roomIDs = request.getParameterValues("deptID");

            ArrayList<Department> rooms = new ArrayList<>();
            for (String roomID : roomIDs) {
                Department d = new Department();
                d.setDeptID(Integer.parseInt(roomID));
                rooms.add(d);
            }
            BookingDetail b = new BookingDetail();
            b.setOrderWait(o);
            b.setDepartments(rooms);
            bdb.insertBooking(b);
        }
        response.sendRedirect("../InformationOfCustomerWait");
    }

    private void doGetBookingHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_page = request.getParameter("page");
        if (raw_page == null || raw_page.length() == 0) {
            raw_page = "1";
        }
        int pageIndex = Integer.parseInt(raw_page);
        BookingDBContext bdb = new BookingDBContext();
        ArrayList<BookingDetail> allBookingDetails = bdb.getAllBookingDetails(pageIndex, pageSize, "null");
        request.setAttribute("bookingHistory", allBookingDetails);

        int totalRow = bdb.totalRowBookingDetail("null");
        int totalPage = (totalRow % pageSize == 0) ? totalRow / pageSize : totalRow / pageSize + 1;
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPage", totalPage);
        String url = "BookingHistory?page=";
        request.setAttribute("url", url);

        String tagMenu = "history";
        request.setAttribute("tagMenu", tagMenu);
        request.getRequestDispatcher("../../view/Management/BookingHistory.jsp").forward(request, response);

    }

    private void doPostBookingHistoty(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("sort");
//        response.getWriter().print(value);
        if (value == null) {
            String keySearch = request.getParameter("keySearch");
            BookingDBContext bdb = new BookingDBContext();
            ArrayList<BookingDetail> searchBookingDetails = bdb.searchBookingDetails(keySearch, "all");
            if (searchBookingDetails.isEmpty()) {
                response.getWriter().print("Not found customer with name contains " + keySearch);
            } else {
                String tag = "order";
                request.setAttribute("tagMenu", tag);
                String title = "hadRoom";
                request.setAttribute("title", title);
                request.setAttribute("bookingHistory", searchBookingDetails);
                request.setAttribute("value", keySearch);
                request.getRequestDispatcher("../../view/Management/BookingHistory.jsp").forward(request, response);
            }
        } else {
            BookingDBContext bdb = new BookingDBContext();
            if (value.equals("all")) {
                response.sendRedirect("BookingHistory");
            } else {
                if (value.equals("cancel")) {
                    ArrayList<BookingDetail> allBookingDetails = bdb.getBookingWithOutPage("true");
                    request.setAttribute("bookingHistory", allBookingDetails);
                } else if (value.equals("notCancel")) {
                    ArrayList<BookingDetail> allBookingDetails = bdb.getBookingWithOutPage("false");
                    request.setAttribute("bookingHistory", allBookingDetails);
                }
                String tagMenu = "history";
                request.setAttribute("tagMenu", tagMenu);
                request.setAttribute("tag", value);
                request.getRequestDispatcher("../../view/Management/BookingHistory.jsp").forward(request, response);
            }
        }
    }

    private void doGetBookingHistoryDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_page = request.getParameter("page");
        if (raw_page == null || raw_page.length() == 0) {
            raw_page = "1";
        }
        int pageIndex = Integer.parseInt(raw_page);
        BookingDBContext bdb = new BookingDBContext();
        ArrayList<BookingDetail> allBookingDetails = bdb.getAllBookingDetails(pageIndex, pageSize, "null");
        request.setAttribute("bookingHistory", allBookingDetails);

        int totalRow = bdb.totalRowBookingDetail("null");
        int totalPage = (totalRow % pageSize == 0) ? totalRow / pageSize : totalRow / pageSize + 1;
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPage", totalPage);
        String url = "BookingHistory?page=";
        request.setAttribute("url", url);

        String tagMenu = "history";
        request.setAttribute("tagMenu", tagMenu);
        BookingDetail b = bdb.getBookingDetail(Integer.parseInt(request.getParameter("orderWaitID")));
        request.setAttribute("bookingDetail", b);
        request.setAttribute("tagMenu", tagMenu);

        InvoiceDBContext idb = new InvoiceDBContext();
        Invoice invoice = idb.getInvoiceByCustomer(b.getOrderWait().getCustomer().getCustomerID());
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher("../../view/Management/BookingHistory.jsp").forward(request, response);

    }

    private void doPostBookingHistoryDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGetBookingHistoryDetails(request, response);
    }

    private void processSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("value");
        String keyName = request.getParameter("keyName");

        value = value.replaceAll("\\s+", " ").trim();
        if (keyName.equals("orderWait")) {
            OrderWaitDBContext odb = new OrderWaitDBContext();
            ArrayList<OrderWait> searchOrder = odb.searchOrder(value);
            if (searchOrder.isEmpty()) {
                response.getWriter().print("Not found customer with name contains " + value);
            } else {
                request.setAttribute("orders", searchOrder);
                String tag = "order";
                request.setAttribute("tagMenu", tag);
                request.setAttribute("value", value);
                String title = "wait";
                request.setAttribute("title", title);
                request.getRequestDispatcher("../../view/Management/OrderWait.jsp").forward(request, response);
            }
        } else if (keyName.equals("haveRoom")) {
            BookingDBContext bdb = new BookingDBContext();
            ArrayList<BookingDetail> searchBookingDetails = bdb.searchBookingDetails(value, "0");
            if (searchBookingDetails.isEmpty()) {
                response.getWriter().print("Not found customer with name contains " + value);
            } else {
                String tag = "order";
                request.setAttribute("tagMenu", tag);
                String title = "hadRoom";
                request.setAttribute("title", title);
                request.setAttribute("allBookingNotCancel", searchBookingDetails);
                request.setAttribute("value", value);
                request.getRequestDispatcher("../../view/Management/OrderHaveRoom.jsp").forward(request, response);

            }
        } else if (keyName.equals("booking")) {
            BookingDBContext bdb = new BookingDBContext();
            ArrayList<BookingDetail> searchBookingDetails = bdb.searchBookingDetails(value, "all");
            if (searchBookingDetails.isEmpty()) {
                response.getWriter().print("Not found customer with name contains " + value);
            } else {
                String tag = "order";
                request.setAttribute("tagMenu", tag);
                String title = "hadRoom";
                request.setAttribute("title", title);
                request.setAttribute("bookingHistory", searchBookingDetails);
                request.setAttribute("value", value);
                request.getRequestDispatcher("../../view/Management/BookingHistory.jsp").forward(request, response);
            }
        } else {
            FeedBackDBContext fdb = new FeedBackDBContext();
            ArrayList<Feedback> searchFeedbacks = fdb.searchFeedbacks(value);
            String tagMenu = "feedback";
            request.setAttribute("tagMenu", tagMenu);
            request.setAttribute("feedbacks", searchFeedbacks);
            request.setAttribute("value", value);

            request.getRequestDispatcher("../../view/Management/Feedback.jsp").forward(request, response);
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
