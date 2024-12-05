package se.verran.javafxhibernatedemo.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import se.verran.javafxhibernatedemo.entities.Customer;
import se.verran.javafxhibernatedemo.entities.MobilePhone;

import java.util.Optional;

public class CustomerDAO {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public boolean deleteCustomerById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customerToDelete = entityManager.find(Customer.class, id);
            entityManager.remove(entityManager.contains(customerToDelete) ? customerToDelete : entityManager.merge(customerToDelete));
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }
        finally {
            entityManager.close();
        }
    }

    public Customer getCustomerById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customerToReturn = entityManager.find(Customer.class,id);
            transaction.commit();
            return customerToReturn;
        } catch (Exception e){
            if(entityManager != null && transaction != null && transaction.isActive()){
                System.out.println(e.getMessage());
            }
            return null;
        }
        finally {
            entityManager.close();
        }
    }

    public boolean saveCustomer(Customer customer) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }
        finally {
            entityManager.close();
        }
    }
}
