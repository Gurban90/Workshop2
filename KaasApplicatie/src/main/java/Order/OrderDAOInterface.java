package Order;

import Client.ClientPOJO;
import java.util.List;

public interface OrderDAOInterface {
    
    public Integer addOrder(OrderPOJO order);
    
    public List<OrderPOJO> getAllOrder();
    
    public OrderPOJO getOrder(OrderPOJO order);
    
    public List<OrderPOJO> getOrderWithClient(ClientPOJO client);
    
    public void updateOrder(OrderPOJO order);
    
    public void deleteOrder (OrderPOJO order);
    
    public void finalize();
}
