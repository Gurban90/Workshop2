package Order;

import Account.AccountController;
import Helper.HibernateDaoFactory;
import Cheese.HibernateCheeseDAO;
import Client.HibernateClientDAO;
import Cheese.CheeseDAOInterface;
import Client.ClientDAOInterface;
import Cheese.CheesePOJO;
import Client.ClientPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class OrderController {

    private Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    @Autowired
    private OrderDAOInterface orderdao;
    @Autowired
    private OrderPOJO orderpojo;
    @Autowired
    private OrderDetailPOJO orderdetailpojo;
    @Autowired
    private OrderDetailDAOInterface orderdetaildao;
    @Autowired
    private CheeseDAOInterface cheesedao;
    @Autowired
    private ClientDAOInterface clientdao;
    @Autowired
    private ClientPOJO clientpojo;
    @Autowired
    private CheesePOJO cheesepojo;

    public OrderController() {

    }

    public OrderController(OrderDAOInterface orderdao, OrderDetailDAOInterface orderdetaildao) {
        this.orderdao = orderdao;
        this.orderdetaildao = orderdetaildao;
    }

    public Integer addOrder(LocalDateTime orderDate, BigDecimal totalPrice, LocalDateTime processedDate, int ClientID) {
        LOGGER.info("start");
        orderpojo.setOrderDate(orderDate);
        orderpojo.setProcessedDate(processedDate);
        orderpojo.setTotalPrice(totalPrice);
        clientpojo.setClientID(ClientID);
        orderpojo.setClient(clientdao.getClient(clientpojo));
        orderdao.addOrder(orderpojo);
        LOGGER.info("end");
        return orderpojo.getOrderID();
    }

    public void addOrderDetail(int quantity, int orderID, int cheeseID) {
        LOGGER.info("setOrderDetail start");
        orderdetailpojo.setQuantity(quantity);
        orderpojo.setOrderID(orderID);
        orderdetailpojo.setOrder(orderdao.getOrder(orderpojo));
        cheesepojo.setCheeseID(cheeseID);
        orderdetailpojo.setCheese(cheesedao.getCheese(cheesepojo));
        try {
            orderdetailpojo.getCheese().getCheeseID();
            orderdetailpojo.getOrder().getOrderID();
        } catch (Exception E) {
            System.out.println("First add the Order and Cheese.");
            return;
        }
        orderdetaildao.addOrderDetail(orderdetailpojo);
        System.out.println("OrderDetail is added and has ID: " + orderdetailpojo.getOrderDetailID());
        LOGGER.info("setorderdetail end");
    }

    public String removeOrder(int orderID) {
        LOGGER.info("start");
        try {
            orderpojo.setOrderID(orderID);
            orderdao.deleteOrder(orderpojo);
        } catch (Exception E) {
            System.out.println("Has to be an existing Order.");
        }
        LOGGER.info("end");
        return "order removed.";
    }

    public String removeOrderDetail(int orderDetailID) {
        LOGGER.info("start");
        try {
            orderdetailpojo.setOrderDetailID(orderDetailID);
            orderdetaildao.deleteOrderDetail(orderdetailpojo);
        } catch (Exception E) {
            System.out.println("Has to be an existing OrderDetail.");
        }
        LOGGER.info("end");
        return "orderDetail removed.";
    }

    public void searchOrderDetailWithOrder(int orderID) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        orderpojo = orderdao.getOrder(orderpojo);
        orderdetailpojo.setOrder(orderpojo);
        System.out.println(orderdetaildao.getOrderDetail(orderdetailpojo));
        LOGGER.info("end");
    }

    public void getAllOrders() {
        LOGGER.info("start");
        System.out.println(orderdao.getAllOrder());
        LOGGER.info("end");
    }

    public void searchOrder(int orderID) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        System.out.println(orderdao.getOrder(orderpojo));
        LOGGER.info("end");
    }

    public void editOrderTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        try {
            orderpojo.setOrderID(orderID);
            orderpojo = orderdao.getOrder(orderpojo);
            orderpojo.setOrderDate(x);
            orderdao.updateOrder(orderpojo);
            LOGGER.info("end");
        } catch (Exception E) {
            System.out.println("Order not found.");
        }
    }

    public void editOrderDeliverTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        try {
            orderpojo.setOrderID(orderID);
            orderpojo = orderdao.getOrder(orderpojo);
            orderpojo.setProcessedDate(x);
            orderdao.updateOrder(orderpojo);
            LOGGER.info("end");
        } catch (Exception E) {
            System.out.println("Order not found.");
        }
    }

    public String editOrderDetailCheese(int orderDetailID, int cheeseID) {
        LOGGER.info("start");
        try {
            orderdetailpojo.setOrderDetailID(orderDetailID);
            orderdetailpojo = orderdetaildao.getOrderDetailWithID(orderdetailpojo);
            cheesepojo.setCheeseID(cheeseID);
            orderdetailpojo.setCheese(cheesedao.getCheese(cheesepojo));
            orderdetaildao.updateOrderDetail(orderdetailpojo);
            LOGGER.info("end");
            return "Orderdetail cheese editted.";
        } catch (Exception E) {
            return "OrderDetail or Cheese not found.";
        }
    }

    public String editOrderDetailAmmount(int orderDetailID, int cheeseAmmount) {
        LOGGER.info("start");
        try {
            orderdetailpojo.setOrderDetailID(orderDetailID);
            orderdetailpojo = orderdetaildao.getOrderDetailWithID(orderdetailpojo);
            orderdetailpojo.setQuantity(cheeseAmmount);
            orderdetaildao.updateOrderDetail(orderdetailpojo);
            LOGGER.info("end");
            return "Orderdetail ammount eddited.";
        } catch (Exception E) {
            return "OrderDetail not found.";
        }
    }

}
