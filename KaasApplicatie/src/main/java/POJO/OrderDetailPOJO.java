/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author Gerben
 */

public class OrderDetailPOJO {

    private int orderDetailID;
    private CheesePOJO cheese;
    private int cheeseID;
    private int quantity;
    private OrderPOJO order;
    private int orderID;

    public OrderDetailPOJO() {
    }

    public OrderDetailPOJO(int orderDetailID, int quantity, int cheeseID, int orderID) {
        this.orderDetailID = orderDetailID;
        this.quantity = quantity;
        this.cheeseID = cheeseID;
        this.orderID = orderID;

    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public CheesePOJO getCheese() {
        return cheese;
    }

    public void setCheese(CheesePOJO cheese) {
        this.cheese = cheese;
    }

    public void setCheeseID(int cheeseID) {
        this.cheeseID = cheeseID;
    }

    public Integer getCheeseID() {
        return cheeseID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderPOJO getOrder() {
        return order;
    }

    public void setOrder(OrderPOJO order) {
        this.order = order;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    @Override
    public String toString() {
        return "OrderDetailID: " + orderDetailID + ", Quantity: " + quantity + ", CheeseID: " + cheeseID + ", OrderID: " + orderID;
    }

}
