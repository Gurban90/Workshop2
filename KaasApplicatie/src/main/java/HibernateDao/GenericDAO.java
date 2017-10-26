/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HibernateDao;

import Helper.HibernateDaoFactory;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Gerben
 */
public abstract class GenericDAO {

   public GenericDAO(){};
    
    protected EntityManager em = HibernateDaoFactory.getEntityManger();

    public <T> T create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public <T> T findById(Class<T> type, Integer id) {
        em.getTransaction().begin();
        T entity = em.find(type, id);
        em.getTransaction().commit();
        return entity;
    }

    

    public <T> void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public <T> void delete(Class<T> type, Integer id) {
        em.getTransaction().begin();
        T entity = em.find(type, id);
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public void finalize() {
        em.close();
    }

}
