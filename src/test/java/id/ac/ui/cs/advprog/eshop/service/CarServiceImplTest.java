package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota");
        car.setCarColor("Black");
        car.setCarQuantity(10);

        when(carRepository.create(car)).thenReturn(car);

        Car result = carService.create(car);

        assertNotNull(result);
        assertEquals("car-1", result.getCarId());
        assertEquals("Toyota", result.getCarName());
        verify(carRepository).create(car);
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Toyota");

        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda");

        Iterator<Car> iterator = Arrays.asList(car1, car2).iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        assertEquals("car-1", result.get(0).getCarId());
        assertEquals("car-2", result.get(1).getCarId());
        verify(carRepository).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(carRepository.findAll()).thenReturn(List.<Car>of().iterator());

        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carRepository).findAll();
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarId("car-1");

        when(carRepository.findById("car-1")).thenReturn(car);

        Car result = carService.findById("car-1");

        assertNotNull(result);
        assertEquals("car-1", result.getCarId());
        verify(carRepository).findById("car-1");
    }

    @Test
    void testFindByIdNotFound() {
        when(carRepository.findById("missing")).thenReturn(null);

        Car result = carService.findById("missing");

        assertNull(result);
        verify(carRepository).findById("missing");
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota");
        car.setCarColor("Black");
        car.setCarQuantity(10);

        carService.update("car-1", car);

        verify(carRepository).update("car-1", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("car-1");

        verify(carRepository).delete("car-1");
    }
}