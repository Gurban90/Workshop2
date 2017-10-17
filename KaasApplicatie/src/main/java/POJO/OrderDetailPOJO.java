/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Gerben
 */
@Entity
@Table(name = "OrderDetail")
public class OrderDetailPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailID;

    @ManyToOne
    @JoinColumn(name = "CheeseID", referencedColumnName = "cheeseID")
    private CheesePOJO cheese;

    @Transient
    private int cheeseID;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "OrderID", referencedColumnName = "orderID")
    private OrderPOJO order;

    @Transient
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
        return "OrderDetailID: " + orderDetailID + ", Quantity: " + quantity + ", Order: " + order.getOrderID()+ ", Cheese: " + cheese + "\n";
    }

}
