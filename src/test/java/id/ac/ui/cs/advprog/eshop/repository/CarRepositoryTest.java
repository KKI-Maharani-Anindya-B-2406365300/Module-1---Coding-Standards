package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;
    private Car car;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();

        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota");
        car.setCarColor("Black");
        car.setCarQuantity(10);
    }

    @Test
    void testCreateCarWithId() {
        Car result = carRepository.create(car);

        assertNotNull(result);
        assertEquals("car-1", result.getCarId());
    }

    @Test
    void testCreateCarWithoutIdGeneratesId() {
        Car carWithoutId = new Car();
        carWithoutId.setCarName("Honda");
        carWithoutId.setCarColor("White");
        carWithoutId.setCarQuantity(5);

        Car result = carRepository.create(carWithoutId);

        assertNotNull(result.getCarId());
        assertFalse(result.getCarId().isBlank());
    }

    @Test
    void testCreateCarWithBlankIdGeneratesId() {
        Car carBlankId = new Car();
        carBlankId.setCarId(" ");
        carBlankId.setCarName("Suzuki");
        carBlankId.setCarColor("Red");
        carBlankId.setCarQuantity(3);

        Car result = carRepository.create(carBlankId);

        assertNotNull(result.getCarId());
        assertFalse(result.getCarId().isBlank());
    }

    @Test
    void testFindAllCars() {
        carRepository.create(car);

        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda");
        car2.setCarColor("White");
        car2.setCarQuantity(5);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();

        assertTrue(iterator.hasNext());
        assertEquals("car-1", iterator.next().getCarId());
        assertEquals("car-2", iterator.next().getCarId());
    }

    @Test
    void testFindAllEmpty() {
        Iterator<Car> iterator = carRepository.findAll();

        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        carRepository.create(car);

        Car result = carRepository.findById("car-1");

        assertNotNull(result);
        assertEquals("car-1", result.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car result = carRepository.findById("missing");

        assertNull(result);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFindByIdSkipsStoredCarWithNullId() throws Exception {
        java.lang.reflect.Field field = CarRepository.class.getDeclaredField("carData");
        field.setAccessible(true);
        java.util.List<Car> carData = (java.util.List<Car>) field.get(carRepository);

        Car carWithNullId = new Car();
        carWithNullId.setCarId(null);
        carWithNullId.setCarName("Honda");
        carWithNullId.setCarColor("White");
        carWithNullId.setCarQuantity(5);

        carData.add(carWithNullId);

        Car result = carRepository.findById("missing");

        assertNull(result);
    }

    @Test
    void testUpdateSuccess() {
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Honda");
        updatedCar.setCarColor("White");
        updatedCar.setCarQuantity(20);

        Car result = carRepository.update("car-1", updatedCar);

        assertNotNull(result);
        assertEquals("Honda", result.getCarName());
        assertEquals("White", result.getCarColor());
        assertEquals(20, result.getCarQuantity());
    }

    @Test
    void testUpdateWithNullCarReturnsNull() {
        Car result = carRepository.update("car-1", null);

        assertNull(result);
    }

    @Test
    void testUpdateWhenCarNotFoundReturnsNull() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Honda");
        updatedCar.setCarColor("White");
        updatedCar.setCarQuantity(20);

        Car result = carRepository.update("missing", updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        carRepository.create(car);

        boolean result = carRepository.delete("car-1");

        assertTrue(result);
        assertNull(carRepository.findById("car-1"));
    }

    @Test
    void testDeleteNotFound() {
        boolean result = carRepository.delete("missing");

        assertFalse(result);
    }
    @Test
    void testDeleteRemovesOnlyMatchingCar() {
        Car car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Toyota");
        car1.setCarColor("Black");
        car1.setCarQuantity(10);

        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda");
        car2.setCarColor("White");
        car2.setCarQuantity(5);

        carRepository.create(car1);
        carRepository.create(car2);

        boolean deleted = carRepository.delete("car-1");

        assertTrue(deleted);
        assertNull(carRepository.findById("car-1"));
        assertNotNull(carRepository.findById("car-2"));
    }

}