package Controller;

import Interface.OrderDAOInterface;
import Interface.OrderDetailDAOInterface;
import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class OrderController {

    private Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private Integer orderID;
    private Integer orderDetailID;
    private OrderDAOInterface orderdao;
    private OrderPOJO orderpojo;
    private OrderDetailPOJO orderdetailpojo;
    private OrderDetailDAOInterface orderdetaildao;

    public OrderController(OrderDAOInterface orderdao, OrderDetailDAOInterface orderdetaildao) {
        this.orderdao = orderdao;
        this.orderpojo = new OrderPOJO();
        this.orderdetailpojo = new OrderDetailPOJO();
        this.orderdetaildao = orderdetaildao;
    }

    public Integer setOrder(LocalDateTime orderDate, BigDecimal totalPrice, LocalDateTime processedDate, int ClientID) {
        LOGGER.info("start");
        orderpojo.setOrderDate(orderDate);
        orderpojo.setProcessedDate(processedDate);
        orderpojo.setTotalPrice(totalPrice);
        orderpojo.setClientID(ClientID);
        orderID = orderdao.addOrder(orderpojo);
        LOGGER.info("end");
        return orderID;
    }

    public Integer setOrderDetail(int quantity, int orderID, int cheeseID) {
        LOGGER.info("setOrderDetail start");
        orderdetailpojo.setQuantity(quantity);
        orderdetailpojo.setOrderID(orderID);
        orderdetailpojo.setCheeseID(cheeseID);

        orderDetailID = orderdetaildao.addOrderDetail(orderdetailpojo);
        LOGGER.info("setorderdetail end");
        return orderDetailID;
    }

    public String removeOrder(int orderID) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        orderdao.deleteOrder(orderpojo);
        LOGGER.info("end");
        return "order removed. ";
    }

    public String removeOrderDetail(int orderDetailID) {
        LOGGER.info("start");
        orderdetailpojo.setOrderDetailID(orderDetailID);
        orderdetaildao.deleteOrderDetail(orderdetailpojo);
        LOGGER.info("end");
        return "orderDetail removed. ";
    }

    public List<OrderDetailPOJO> searchOrderDetail(int orderID) {
        LOGGER.info("start");
        orderdetailpojo.setOrderID(orderID);
        List<OrderDetailPOJO> returnedOrderDetail = orderdetaildao.getOrderDetail(orderdetailpojo);
        LOGGER.info("end");
        return returnedOrderDetail;
    }

    public List<OrderPOJO> getAllOrders() {
        return orderdao.getAllOrder();
    }

    public OrderPOJO searchOrder(int orderID) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        OrderPOJO returnedOrder = orderdao.getOrder(orderpojo);
        LOGGER.info("end");
        return returnedOrder;

    }

    public void editOrderTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        orderpojo = orderdao.getOrder(orderpojo);
        orderpojo.setOrderDate(x);
        orderdao.updateOrder(orderpojo);
        LOGGER.info("end");
    }

    public void editOrderDeliverTime(int orderID, LocalDateTime x) {
        orderpojo.setOrderID(orderID);
        orderpojo = orderdao.getOrder(orderpojo);
        orderpojo.setProcessedDate(x);
        orderdao.updateOrder(orderpojo);
    }

    public String editOrderDetailCheese(int orderDetailID, int cheeseID) {
        LOGGER.info("start");
        orderdetailpojo.setOrderDetailID(orderDetailID);
        orderdetailpojo = orderdetaildao.getOrderDetailWithID(orderdetailpojo);
        orderdetailpojo.setCheeseID(cheeseID);
        orderdetaildao.updateOrderDetail(orderdetailpojo);
        LOGGER.info("end");
        return "orderdetail cheese editted";
    }

    public String editOrderDetailAmmount(int orderDetailID, int cheeseAmmount) {
        LOGGER.info("start");
        orderdetailpojo.setOrderDetailID(orderDetailID);
        orderdetailpojo = orderdetaildao.getOrderDetailWithID(orderdetailpojo);
        orderdetailpojo.setQuantity(cheeseAmmount);
        orderdetaildao.updateOrderDetail(orderdetailpojo);
        LOGGER.info("end");
        return "orderdetailammount eddited";
    }

}
