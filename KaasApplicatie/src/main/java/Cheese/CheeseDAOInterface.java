package Cheese;

import java.util.List;

public interface CheeseDAOInterface {

    public Integer addCheese(CheesePOJO cheese);

    public List<CheesePOJO> getAllCheese();

    public CheesePOJO getCheese(CheesePOJO cheese);
    
    public List<CheesePOJO> getCheeseWithName(CheesePOJO cheese);

    public void updateCheese(CheesePOJO cheese);

    public void deleteCheese(CheesePOJO cheese);
    
    public void finalize();

}
