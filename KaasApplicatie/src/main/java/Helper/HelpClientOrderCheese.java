/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Cheese.CheeseController;
import Order.OrderController;
import DatabaseConnector.DomXML;
import Order.HibernateOrderDAO;
import Order.HibernateOrderDetailDAO;
import Order.OrderDAOInterface;
import Order.OrderDetailDAOInterface;
import Cheese.CheesePOJO;
import Order.OrderDetailPOJO;
import Order.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jasper Thielen
 */
@Component
@ComponentScan("DatabaseConnector")
public class HelpClientOrderCheese {

    private int orderID;
    private int cheeseID;

    private int clientYear;
    private int clientMonth;
    private int clientDay;
    private int clientHour;
    private int clientMin;
    private final int sec = 0;
    private LocalDateTime processedDate;

    private int deliveryYear;
    private int deliveryMonth;
    private int deliveryDay;
    private int deliveryHour;
    private int deliveryMin;
    private LocalDateTime orderDate;

    private final BigDecimal zeroTotalPrice = new BigDecimal(0);
    private BigDecimal totalPrice = new BigDecimal(0);
    private int ammountCheese;
    private BigDecimal cheesePrice;

    private OrderPOJO returnedOrderPOJO;
    private CheesePOJO returnedCheesePOJO;

    @Autowired
    private CheeseController cheeseController;
    @Autowired
    private OrderController orderController;
    @Autowired
    private DomXML data;
    @Autowired
    private OrderDAOInterface orderDAO;
    @Autowired
    private OrderDetailDAOInterface orderDetailDAO;

    public HelpClientOrderCheese() {

    }

    public LocalDateTime setNewOrderByClient(int year, int month, int day, int hour, int min) {
        clientYear = year;
        clientMonth = month;
        clientDay = day;
        clientHour = hour;
        clientMin = min;

        orderDate = LocalDateTime.of(clientYear, clientMonth, clientDay, clientHour, clientMin, sec);
        return orderDate;
    }

    public LocalDateTime setOrderDelivery(int year, int month, int day, int hour, int min) {
        deliveryYear = year;
        deliveryMonth = month;
        deliveryDay = day;
        deliveryHour = hour;
        deliveryMin = min;

        processedDate = LocalDateTime.of(deliveryYear, deliveryMonth, deliveryDay, deliveryHour, deliveryMin, sec);
        return processedDate;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public void setOrderDetail(int cheeseID, int ammountCheese) {
        this.ammountCheese = ammountCheese;
        this.cheeseID = cheeseID;
    }

    public void getSingleCheesePrice() {

        returnedCheesePOJO = new CheesePOJO();
        returnedCheesePOJO = cheeseController.findCheese(cheeseID);
        this.cheesePrice = returnedCheesePOJO.getPrice();
    }

    public void getOrderDetail() {

        orderController.addOrderDetail(ammountCheese, orderID, cheeseID);
    }

    public BigDecimal addUpCheese() {

        BigDecimal ammountCheeseBD = new BigDecimal(ammountCheese);

        totalPrice = totalPrice.add(cheesePrice.multiply(ammountCheeseBD));

        return totalPrice;

    }

    public String saveTotalPrice() {
        OrderPOJO order = new OrderPOJO();
        order.setOrderID(orderID);
        returnedOrderPOJO = orderDAO.getOrder(order);

        if (returnedOrderPOJO.getTotalPrice() == new BigDecimal(0)) {
        } else {
            returnedOrderPOJO.setTotalPrice(returnedOrderPOJO.getTotalPrice());
        }
        BigDecimal price = returnedOrderPOJO.getTotalPrice();
        price = price.add(totalPrice);
        returnedOrderPOJO.setTotalPrice(price);
        orderDAO.updateOrder(returnedOrderPOJO);
        return "Total price updated";
    }

    public String minusCheese(int orderDetailId, int orderId, boolean edit) {

        OrderDetailPOJO orderdetail = new OrderDetailPOJO();
        orderdetail.setOrderDetailID(orderDetailId);
        OrderDetailPOJO returnOrderDetail = orderDetailDAO.getOrderDetailWithID(orderdetail);
        OrderPOJO order = new OrderPOJO();
        order.setOrderID(orderId);
        OrderPOJO returnorder = orderDAO.getOrder(order);

        if (edit) {
            BigDecimal overallPrice = returnorder.getTotalPrice();
            BigDecimal quantity = new BigDecimal(returnOrderDetail.getQuantity());
            CheesePOJO cheese = cheeseController.findCheese(returnOrderDetail.getCheese().getCheeseID());
            BigDecimal price = cheese.getPrice();

            overallPrice = overallPrice.add(price.multiply(quantity));

            returnorder.setTotalPrice(overallPrice);
            orderDAO.updateOrder(returnorder);

        } else {
            BigDecimal overallPrice = returnorder.getTotalPrice();
            BigDecimal quantity = new BigDecimal(returnOrderDetail.getQuantity());
            CheesePOJO cheese = cheeseController.findCheese(returnOrderDetail.getCheese().getCheeseID());
            BigDecimal price = cheese.getPrice();

            overallPrice = overallPrice.subtract(price.multiply(quantity));

            returnorder.setTotalPrice(overallPrice);
            orderDAO.updateOrder(returnorder);
        }

        return "Total price updated";

    }

    public String adjustStock() {

        int oldStock = 0;
        int newStock = 0;

        CheesePOJO x = cheeseController.findCheese(cheeseID);

        oldStock = x.getStock();

        if (oldStock - ammountCheese >= 0) {
            newStock = oldStock - ammountCheese;
            cheeseController = new CheeseController();
            cheeseController.editCheeseStock(cheeseID, newStock);
            return "Stock altered ";

        } else if (oldStock - ammountCheese < 0) {
            newStock = oldStock - ammountCheese;
            cheeseController.editCheeseStock(cheeseID, newStock);
            return " Not enough stock, order still placed, Stock is now negative ";

        } else if (oldStock <= 0) {
            return "Nothing in stock (maybe you can place something like a validator to prevent people from ordering?) ";

        } else {
            return "Something went wrong";
        }

    }

}
