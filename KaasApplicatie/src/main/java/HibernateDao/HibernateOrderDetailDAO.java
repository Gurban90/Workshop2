/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Gerben
 */
public class HibernateOrderDetailDAO extends GenericDAO {

    private Logger LOGGER = Logger.getLogger(HibernateOrderDetailDAO.class.getName());
   
    
    public HibernateOrderDetailDAO(EntityManager em) {
        super(em);
    }

    @Override
    public <T> List<T> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
