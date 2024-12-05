package se.verran.javafxhibernatedemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.verran.javafxhibernatedemo.DAO.CarDAO;
import se.verran.javafxhibernatedemo.DAO.CustomerDAO;
import se.verran.javafxhibernatedemo.entities.Customer;
import se.verran.javafxhibernatedemo.entities.MobilePhone;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //          System.err to write in red color


        //          Initial check for the two cars inserted into database through import.sql
        CarDAO carDAO = new CarDAO();
        System.err.println("Storlek på lista med bilar är " + carDAO.getAllCars().size()); // Should be "2"


        //          Create a new customer
        Customer customerWithNewPhones = new Customer();
        customerWithNewPhones.setFirstName("Klas");
        customerWithNewPhones.setLastName("Kuling");

        //          Create a new phone
        MobilePhone mobilePhone = new MobilePhone("Ericsson");

        //          Adding phone to customer, and customer to phone
        mobilePhone.setOwner(customerWithNewPhones);
        customerWithNewPhones.getMobilePhones().add(mobilePhone);

        //          Persist customer into database, if using CASCADE.PERSIST the phone will also be added
        CustomerDAO customerDAO = new CustomerDAO();
        if (customerDAO.saveCustomer(customerWithNewPhones)) {
            System.err.println("Customer saved");
        } else {
            System.err.println("Could not save customer");
        }

        //          Make sure database had time to evolve, and for import.sql to load data into database
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //          Customer 1 has one phone, a Nokia
        //          If removing customer, phone should be removed too if using orphanRemoval
/*        System.err.println("Check for phone Nokia in database");
        System.err.println("Hit enter to remove customer 1");
        new Scanner(System.in).nextLine();
        customerDAO.deleteCustomerById(1);
        System.err.println("Check for phone Nokia in database again, it's gone!");*/

        //          FetchType.EAGER vs FetchType.LAZY
        Customer testCustomer = customerDAO.getCustomerById(1);
        System.out.println(testCustomer.getFirstName() + " " + testCustomer.getLastName());
/*        System.out.println("Äger följande telefoner:");
        for (MobilePhone phone: testCustomer.getMobilePhones())
            System.out.println("id: " + phone.getId() + " -> " + phone.getBrand());

        System.out.println(testCustomer.getMobilePhones().size());*/
//        MobilePhoneDAO mobilePhoneDAO = new MobilePhoneDAO();
//        mobilePhoneDAO.deletePhoneById(1);

/*
//          Persist another car into database
        System.err.println("Persisting another car...");
        Car testCar = new Car("Blue","Volkswagen");
        if(carDAO.saveCar(testCar))
            System.err.println(testCar.getMake() + " created!");
        else
            System.err.println("Failed to create the " + testCar.getMake());

        //          The car created just now
        Car carFromDatabase = carDAO.getCarById(3);
        System.err.println("Hämtad från db och har färgen: " + carFromDatabase.getColor()); // Should be "Blue"
*/



        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}