package Controller;

import Helper.HibernateDaoFactory;
import HibernateDao.HibernateCheeseDAO;
import HibernateDao.HibernateClientDAO;
import HibernateDao.HibernateOrderDAO;
import HibernateDao.HibernateOrderDetailDAO;
import Interface.CheeseDAOInterface;
import Interface.ClientDAOInterface;
import Interface.OrderDAOInterface;
import Interface.OrderDetailDAOInterface;
import POJO.CheesePOJO;
import POJO.ClientPOJO;
import POJO.OrderDetailPOJO;
import POJO.OrderPOJO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

public class OrderController {

    private Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private OrderDAOInterface orderdao;
    private OrderPOJO orderpojo;
    private OrderDetailPOJO orderdetailpojo;
    private OrderDetailDAOInterface orderdetaildao;
    private CheeseDAOInterface cheesedao;
    private ClientDAOInterface clientdao;
    private ClientPOJO clientpojo;
    private CheesePOJO cheesepojo;

    public OrderController() {
        orderdao = (HibernateOrderDAO) HibernateDaoFactory.getInstance().getDao("order");
        this.orderpojo = new OrderPOJO();
        this.orderdetailpojo = new OrderDetailPOJO();
        clientpojo = new ClientPOJO();
        cheesepojo = new CheesePOJO();
        orderdetaildao = (HibernateOrderDetailDAO) HibernateDaoFactory.getInstance().getDao("orderdetail");
        cheesedao = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");
        clientdao = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

    }

    public OrderController(OrderDAOInterface orderdao, OrderDetailDAOInterface orderdetaildao) {
        this.orderdao = orderdao;
        this.orderpojo = new OrderPOJO();
        this.orderdetailpojo = new OrderDetailPOJO();
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
        orderdetaildao.finalize();
        System.out.println("OrderDetail is added and has ID: " + orderdetailpojo.getOrderDetailID());
        LOGGER.info("setorderdetail end");
    }

    public String removeOrder(int orderID) {
        LOGGER.info("start");
        try {
            orderpojo.setOrderID(orderID);
            orderdao.deleteOrder(orderpojo);
            orderdao.finalize();
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
            orderdetaildao.finalize();
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
        orderdetaildao.finalize();
        LOGGER.info("end");
    }

    public void getAllOrders() {
        LOGGER.info("start");
        System.out.println(orderdao.getAllOrder());
        orderdao.finalize();
        LOGGER.info("end");
    }

    public void searchOrder(int orderID) {
        LOGGER.info("start");
        orderpojo.setOrderID(orderID);
        System.out.println(orderdao.getOrder(orderpojo));
        orderdao.finalize();
        LOGGER.info("end");
    }

    public void editOrderTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        try {
            orderpojo.setOrderID(orderID);
            orderpojo = orderdao.getOrder(orderpojo);
            orderpojo.setOrderDate(x);
            orderdao.updateOrder(orderpojo);
            orderdao.finalize();
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
            orderdao.finalize();
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
            orderdetaildao.finalize();
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
            orderdetaildao.finalize();
            LOGGER.info("end");
            return "Orderdetail ammount eddited.";
        } catch (Exception E) {
            return "OrderDetail not found.";
        }
    }

}
