package se.verran.javafxhibernatedemo.DAO;

import jakarta.persistence.*;
import se.verran.javafxhibernatedemo.entities.Car;

import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    // CRUD-operations

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create
    public boolean saveCar(Car car){

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(car);
            car.setColor("Purple");
            entityManager.persist(car);
            transaction.commit();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }finally {
            entityManager.close();
        }

    }

    // Read One/All
    public Car getCarById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Car carToReturn = entityManager.find(Car.class, id);
        entityManager.close();
        return carToReturn;
    }
// "FROM Car c WHERE c.make = :variabel
    public List<Car> getAllCars(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Car> listToReturn = new ArrayList<>();
        TypedQuery<Car> result = entityManager.createQuery("FROM Car", Car.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Update
    public void updateCar(Car carToUpdate){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(entityManager.contains(carToUpdate)){
                System.out.println("Bilen finns i poolen");
                entityManager.persist(carToUpdate);

            } else {
                System.out.println("Finns inte i poolen");
                Car revivedCar = entityManager.merge(carToUpdate);
                System.out.println(revivedCar.getId() + " is alive");
            }
            entityManager.merge(carToUpdate);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Delete

    public void deleteCar(Car car){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(!entityManager.contains(car)){
                car = entityManager.merge(car);
            }
            entityManager.remove(car);

            // Ett annat sätt att skriva det på:
            //entityManager.remove(entityManager.contains(car) ? car : entityManager.merge(car));

            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
        finally {
            entityManager.close();
        }
    }
    public boolean deleteCarById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Car carToDelete = entityManager.find(Car.class, id);
            entityManager.remove(entityManager.contains(carToDelete) ? carToDelete : entityManager.merge(carToDelete));
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
}
