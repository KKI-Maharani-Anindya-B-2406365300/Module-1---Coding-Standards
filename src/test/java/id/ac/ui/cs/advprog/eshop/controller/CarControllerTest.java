package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CarService carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Toyota");
        car.setCarColor("Black");
        car.setCarQuantity(10);
    }

    @Test
    void testCreateCarPage() throws Exception {
        mvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        when(carService.create(org.mockito.ArgumentMatchers.any(Car.class))).thenReturn(car);

        mvc.perform(post("/car/createCar")
                        .param("carName", "Toyota")
                        .param("carColor", "Black")
                        .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testCarListPage() throws Exception {
        when(carService.findAll()).thenReturn(List.of(car));

        mvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testEditCarPage() throws Exception {
        when(carService.findById("car-1")).thenReturn(car);

        mvc.perform(get("/car/editCar/car-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCarPost() throws Exception {
        doNothing().when(carService).update(org.mockito.ArgumentMatchers.eq("car-1"),
                org.mockito.ArgumentMatchers.any(Car.class));

        mvc.perform(post("/car/editCar")
                        .param("carId", "car-1")
                        .param("carName", "Honda")
                        .param("carColor", "White")
                        .param("carQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).deleteCarById("car-1");

        mvc.perform(post("/car/deleteCar")
                        .param("carId", "car-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
    }
}