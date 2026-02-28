package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository {

    private final List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        if (car.getCarId() == null || car.getCarId().isBlank()) {
            car.setCarId(UUID.randomUUID().toString());
        }
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId() != null && car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Car update(String id, Car updatedCar) {
        if (updatedCar == null) return null;

        Car existing = findById(id);
        if (existing == null) return null;

        existing.setCarName(updatedCar.getCarName());
        existing.setCarColor(updatedCar.getCarColor());
        existing.setCarQuantity(updatedCar.getCarQuantity());
        return existing;
    }

    public boolean delete(String id) {
        return carData.removeIf(car -> car.getCarId().equals(id));
    }
}