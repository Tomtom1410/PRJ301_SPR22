package model;

/**
 *
 * @author Tom
 */
public class Invoice {
//    private BookingDetail bookingDetail;
    private Customer customer;
    private int invoiceID;
    private double totalPrice;

    public Invoice() {
    }

//    public BookingDetail getBookingDetail() {
//        return bookingDetail;
//    }
//
//    public void setBookingDetail(BookingDetail bookingDetail) {
//        this.bookingDetail = bookingDetail;
//    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
