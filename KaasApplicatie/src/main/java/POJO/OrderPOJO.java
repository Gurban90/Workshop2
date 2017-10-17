package POJO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Orders")
public class OrderPOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "ClientID", referencedColumnName = "clientID")
    private ClientPOJO client;

    private LocalDateTime orderDate;
    private LocalDateTime processedDate;
    private BigDecimal totalPrice;

    @Transient
    private int clientID;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailPOJO> orderDetails = new ArrayList<OrderDetailPOJO>();

    public OrderPOJO() {
    }

    public OrderPOJO(int orderID, LocalDateTime orderDate, BigDecimal totalPrice, LocalDateTime processedDate, int clientID) {

        this.orderID = orderID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.processedDate = processedDate;
        this.clientID = clientID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<OrderDetailPOJO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailPOJO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ClientPOJO getClient() {
        return client;
    }

    public void setClient(ClientPOJO client) {
        this.client = client;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDateTime processedDate) {
        this.processedDate = processedDate;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "OrderID: " + orderID + ", OrderDate: " + orderDate + ", TotalPrice: " + totalPrice + ", ProcessedDate: " + processedDate + ", Client " + client.getClientID()+ 
                "\n" + ", OrderDetails: " + orderDetails;
    }

}
