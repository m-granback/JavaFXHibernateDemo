package se.verran.javafxhibernatedemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.verran.javafxhibernatedemo.DAO.CarDAO;
import se.verran.javafxhibernatedemo.entities.Car;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        CarDAO carDAO = new CarDAO();
        Car testCar = new Car("Blue","Volvo");
        if(carDAO.saveCar(testCar)){
            System.out.println("Car saved");
        } else {
            System.out.println("Not saved");
        }


        Car carFromDatabase = carDAO.getCarById(1);
        System.out.println("Hämtad från db och har färgen: " + carFromDatabase.getColor());

        System.out.println("Storlek på lista med bilar är " + carDAO.getAllCars().size());

        carDAO.deleteCar(testCar);








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