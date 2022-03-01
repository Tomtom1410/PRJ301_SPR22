/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Tom
 */
public class OrderWait {

    private int orderWaitID;
    private Date checkIn;
    private Date checkOut;
    private Customer customer;
    private Department department;
    private int noOfRoom;
    private boolean Rented;

    public OrderWait() {
    }

    public boolean isRented() {
        return Rented;
    }

    public void setRented(boolean Rented) {
        this.Rented = Rented;
    }
    
    public int getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(int noOfRoom) {
        this.noOfRoom = noOfRoom;
    }

    public int getOrderWaitID() {
        return orderWaitID;
    }

    public void setOrderWaitID(int orderWaitID) {
        this.orderWaitID = orderWaitID;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
