package Interface;

import POJO.OrderDetailPOJO;
import java.util.List;

public interface OrderDetailDAOInterface {

    public Integer addOrderDetail(OrderDetailPOJO orderDetail);

    public List<OrderDetailPOJO> getAllOrderDetail();

   public List<OrderDetailPOJO> getOrderDetail(OrderDetailPOJO orderdetail);
   
   public OrderDetailPOJO getOrderDetailWithID(OrderDetailPOJO orderdetail);

    public void deleteOrderDetail(OrderDetailPOJO orderDetail);
    
    public void updateOrderDetail(OrderDetailPOJO orderDetail) ;
    
     public List<OrderDetailPOJO> getWithCheese(int CheeseID);
    
    public void finalize();
}
