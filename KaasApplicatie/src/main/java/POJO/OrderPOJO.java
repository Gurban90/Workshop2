package POJO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderPOJO {

    private int orderID;
    private ClientPOJO client; //id is first input
    private LocalDateTime orderDate; //when it is ordered (now time)
    private LocalDateTime processedDate; //when the order is finished and send to client

    private int clientID;
    private OrderDetailPOJO orderDetail; //zero
    private BigDecimal totalPrice; //zero

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

    public OrderDetailPOJO getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailPOJO orderDetail) {
        this.orderDetail = orderDetail;
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
        return "OrderID: " + orderID + ", OrderDate: " + orderDate + ", TotalPrice: " + totalPrice + ", ProcessedDate: " + processedDate + ", ClientID " + clientID;
    }

}
