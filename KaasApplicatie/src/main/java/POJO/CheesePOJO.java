/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Gerben
 */
@Entity
@Table(name = "Cheese")
public class CheesePOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cheeseID;
    private String cheeseName;
    private BigDecimal price;
    private int stock;

    @OneToMany(mappedBy = "cheese")
    private List<OrderDetailPOJO> orderDetails = new ArrayList<OrderDetailPOJO>();

    public CheesePOJO() {};
    
    public CheesePOJO(Integer id, String name, BigDecimal price, Integer stock) {
        this.cheeseID = id;
        this.cheeseName = name;
        this.price = price;
        this.stock = stock;
    }

    public int getCheeseID() {
        return cheeseID;
    }

    public void setCheeseID(int cheeseID) {
        this.cheeseID = cheeseID;
    }

    public List<OrderDetailPOJO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailPOJO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCheeseName() {
        return cheeseName;
    }

    public void setCheeseName(String cheeseName) {
        this.cheeseName = cheeseName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "CheeseID:" + cheeseID + ", CheeseName:" + cheeseName + ", Price:" + price + ", Stock:" + stock;
    }

}
