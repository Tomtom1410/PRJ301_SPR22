package model;

import java.util.ArrayList;

/**
 *
 * @author Tom
 */
public class BookingDetail {

    private int bookingID;
    private OrderWait orderWait;
    private ArrayList<Department> departments = new ArrayList<>();
    private boolean cancel;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int BookingID) {
        this.bookingID = BookingID;
    }

    public OrderWait getOrderWait() {
        return orderWait;
    }

    public void setOrderWait(OrderWait orderWait) {
        this.orderWait = orderWait;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

}
