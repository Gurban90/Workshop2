package Controller;

import Helper.HibernateDaoFactory;
import HibernateDao.HibernateCheeseDAO;
import HibernateDao.HibernateClientDAO;
import HibernateDao.HibernateOrderDAO;
import HibernateDao.HibernateOrderDetailDAO;
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
    private HibernateOrderDAO hibOrderDAO;
    private HibernateOrderDetailDAO hibOrderDetailDAO;
    private HibernateCheeseDAO hibCheeseDAO;
    private HibernateClientDAO hibClientDAO;

    public OrderController() {
        this.hibOrderDAO = (HibernateOrderDAO) HibernateDaoFactory.getInstance().getDao("order");
        this.orderpojo = new OrderPOJO();
        this.orderdetailpojo = new OrderDetailPOJO();
        this.hibOrderDetailDAO = (HibernateOrderDetailDAO) HibernateDaoFactory.getInstance().getDao("orderdetail");
        this.hibCheeseDAO = (HibernateCheeseDAO) HibernateDaoFactory.getInstance().getDao("cheese");
        this.hibClientDAO = (HibernateClientDAO) HibernateDaoFactory.getInstance().getDao("client");

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
        orderpojo.setClient(hibClientDAO.findById(ClientPOJO.class, ClientID));
        hibOrderDAO.create(orderpojo);
        LOGGER.info("end");
        return orderpojo.getOrderID();
    }

    public void addOrderDetail(int quantity, int orderID, int cheeseID) {
        LOGGER.info("setOrderDetail start");
        try {
            orderdetailpojo.setQuantity(quantity);
            orderdetailpojo.setOrder(hibOrderDAO.findById(OrderPOJO.class, orderID));
            orderdetailpojo.setCheese(hibCheeseDAO.findById(CheesePOJO.class, cheeseID));
            hibOrderDetailDAO.create(orderdetailpojo);
            hibOrderDetailDAO.finalize();
            System.out.println("OrderDetail is added and has ID: " + orderdetailpojo.getOrderDetailID());
            LOGGER.info("setorderdetail end");
        } catch (PersistenceException E) {
            System.out.println("First add the Order and Cheese.");
        }
    }

    public String removeOrder(int orderID) {
        LOGGER.info("start");
        hibOrderDAO.delete(OrderPOJO.class, orderID);
        hibOrderDAO.finalize();
        LOGGER.info("end");
        return "order removed.";
    }

    public String removeOrderDetail(int orderDetailID) {
        LOGGER.info("start");
        hibOrderDetailDAO.delete(OrderDetailPOJO.class, orderDetailID);
        hibOrderDetailDAO.finalize();
        LOGGER.info("end");
        return "orderDetail removed.";
    }

    public List<OrderDetailPOJO> searchOrderDetailWithOrder(int orderID) {
        LOGGER.info("start");
        List<OrderDetailPOJO> returnedOrderDetail = hibOrderDetailDAO.getWithOrder(orderID);
        hibOrderDetailDAO.finalize();
        LOGGER.info("end");
        return returnedOrderDetail;
    }

    public List<OrderPOJO> getAllOrders() {
        LOGGER.info("start");
        List<OrderPOJO> returnedOrders = hibOrderDAO.getAll();
        hibOrderDAO.finalize();
        LOGGER.info("end");
        return returnedOrders;
    }

    public OrderPOJO searchOrder(int orderID) {
        LOGGER.info("start");
        OrderPOJO returnedOrder = hibOrderDAO.findById(OrderPOJO.class, orderID);
        hibOrderDAO.finalize();
        LOGGER.info("end");
        return returnedOrder;
    }

    public void editOrderTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        orderpojo = hibOrderDAO.findById(OrderPOJO.class, orderID);
        orderpojo.setOrderDate(x);
        hibOrderDAO.update(orderpojo);
        hibOrderDAO.finalize();
        LOGGER.info("end");
    }

    public void editOrderDeliverTime(int orderID, LocalDateTime x) {
        LOGGER.info("start");
        orderpojo = hibOrderDAO.findById(OrderPOJO.class, orderID);
        orderpojo.setProcessedDate(x);
        hibOrderDAO.update(orderpojo);
        hibOrderDAO.finalize();
        LOGGER.info("end");
    }

    public String editOrderDetailCheese(int orderDetailID, int cheeseID) {
        LOGGER.info("start");
        try {
            orderdetailpojo = hibOrderDetailDAO.findById(OrderDetailPOJO.class, orderDetailID);
            orderdetailpojo.setCheese(hibCheeseDAO.findById(CheesePOJO.class, cheeseID));
            hibOrderDetailDAO.update(orderpojo);
            hibOrderDetailDAO.finalize();
            LOGGER.info("end");
            return "Orderdetail cheese editted.";
        } catch (PersistenceException E) {
            return "Has to be an existing Cheese.";
        }
    }

    public String editOrderDetailAmmount(int orderDetailID, int cheeseAmmount) {
        LOGGER.info("start");
        orderdetailpojo = hibOrderDetailDAO.findById(OrderDetailPOJO.class, orderDetailID);
        orderdetailpojo.setQuantity(cheeseAmmount);
        hibOrderDetailDAO.update(orderpojo);
        hibOrderDetailDAO.finalize();
        LOGGER.info("end");
        return "Orderdetail ammount eddited.";
    }

}
