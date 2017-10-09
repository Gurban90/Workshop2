/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Controller.CheeseController;
import Controller.OrderController;
import DatabaseConnector.DomXML;
import Interface.OrderDAOInterface;
import Interface.OrderDetailDAOInterface;
import POJO.CheesePOJO;
import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Jasper Thielen
 */
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

    private CheeseController cheeseController;
    private OrderController orderController;
    private DomXML data;

    public HelpClientOrderCheese() {
        data = new DomXML();
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

    public void getOrder(int clientIDint) {

        orderController = new OrderController(DaoFactory.createOrderDao(data.getDatabaseType()), DaoFactory.createOrderDetailDao(data.getDatabaseType()));
        this.orderID = orderController.setOrder(orderDate, zeroTotalPrice, processedDate, clientIDint);
    }

    public void setOrderDetail(int cheeseID, int ammountCheese) {
        this.ammountCheese = ammountCheese;
        this.cheeseID = cheeseID;
    }

    public void getSingleCheesePrice() {
        CheeseController cheeseController = new CheeseController(DaoFactory.createCheeseDao(data.getDatabaseType()));

        returnedCheesePOJO = new CheesePOJO();
        returnedCheesePOJO = cheeseController.findCheese(cheeseID);
        this.cheesePrice = returnedCheesePOJO.getPrice();
    }

    public void getOrderDetail() {

        orderController = new OrderController(DaoFactory.createOrderDao(data.getDatabaseType()), DaoFactory.createOrderDetailDao(data.getDatabaseType()));

        orderController.setOrderDetail(ammountCheese, orderID, cheeseID);
    }

    public BigDecimal addUpCheese() {

        BigDecimal ammountCheeseBD = new BigDecimal(ammountCheese);

        totalPrice = totalPrice.add(cheesePrice.multiply(ammountCheeseBD));

        return totalPrice;

    }

    public String saveTotalPrice() {

        OrderPOJO orderPOJO = new OrderPOJO();
        OrderDAOInterface orderDAO = DaoFactory.createOrderDao(data.getDatabaseType());
        orderPOJO.setOrderID(orderID);
        returnedOrderPOJO = orderDAO.getOrder(orderPOJO);
        if (returnedOrderPOJO.getTotalPrice() == new BigDecimal(0)) {
        } else {
            returnedOrderPOJO.setTotalPrice(returnedOrderPOJO.getTotalPrice());
        }
        BigDecimal price = returnedOrderPOJO.getTotalPrice();
        price = price.add(totalPrice);
        returnedOrderPOJO.setTotalPrice(price);
        orderDAO.updateOrder(returnedOrderPOJO);

        return "total price updated";

    }

    public String minusCheese(int orderDetailId, int orderId, boolean edit) {
        OrderDetailPOJO orderdetail = new OrderDetailPOJO();
        OrderPOJO order = new OrderPOJO();

        orderdetail.setOrderDetailID(orderDetailId);
        order.setOrderID(orderId);

        OrderDAOInterface orderDAO = DaoFactory.createOrderDao(data.getDatabaseType());
        OrderDetailDAOInterface orderDetailDAO = DaoFactory.createOrderDetailDao(data.getDatabaseType());
        CheeseController cheesecontrol = new CheeseController(DaoFactory.createCheeseDao(data.getDatabaseType()));

        OrderDetailPOJO returnOrderDetail = orderDetailDAO.getOrderDetailWithID(orderdetail);
        OrderPOJO returnorder = orderDAO.getOrder(order);
        if (edit) {
            BigDecimal overallPrice = returnorder.getTotalPrice();
            BigDecimal quantity = new BigDecimal(returnOrderDetail.getQuantity());
            CheesePOJO cheese = cheesecontrol.findCheese(returnOrderDetail.getCheeseID());
            BigDecimal price = cheese.getPrice();

            overallPrice = overallPrice.add(price.multiply(quantity));

            returnorder.setTotalPrice(overallPrice);
            orderDAO.updateOrder(returnorder);

        } else {
            BigDecimal overallPrice = returnorder.getTotalPrice();
            BigDecimal quantity = new BigDecimal(returnOrderDetail.getQuantity());
            CheesePOJO cheese = cheesecontrol.findCheese(returnOrderDetail.getCheeseID());
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

        cheeseController = new CheeseController(DaoFactory.createCheeseDao(data.getDatabaseType()));

        CheesePOJO x = cheeseController.findCheese(cheeseID);

        oldStock = x.getStock();

        if (oldStock - ammountCheese >= 0) {
            newStock = oldStock - ammountCheese;
            cheeseController.editCheeseStock(cheeseID, newStock);
            return "stock altered ";

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
