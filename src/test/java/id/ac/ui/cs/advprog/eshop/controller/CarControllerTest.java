package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mvc;
    private CarService carService;
    private Car car;

    @BeforeEach
    void setUp() {
        carService = Mockito.mock(CarService.class);
        CarController carController = new CarController(carService);

        ViewResolver dummyViewResolver = (viewName, locale) -> {
            if (viewName.startsWith("redirect:")) {
                return new org.springframework.web.servlet.view.RedirectView(
                        viewName.substring("redirect:".length())
                );
            }

            return new View() {
                @Override
                public String getContentType() {
                    return "text/html";
                }

                @Override
                public void render(java.util.Map<String, ?> model,
                                   jakarta.servlet.http.HttpServletRequest request,
                                   jakarta.servlet.http.HttpServletResponse response) {
                    // no-op
                }
            };
        };

        mvc = MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(dummyViewResolver)
                .build();

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
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        when(carService.create(any(Car.class))).thenReturn(car);

        mvc.perform(post("/car/createCar")
                        .param("carName", "Toyota")
                        .param("carColor", "Black")
                        .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        when(carService.findAll()).thenReturn(List.of(car));

        mvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));

        verify(carService).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        when(carService.findById("car-1")).thenReturn(car);

        mvc.perform(get("/car/editCar/car-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService).findById("car-1");
    }

    @Test
    void testEditCarPost() throws Exception {
        doNothing().when(carService).update(eq("car-1"), any(Car.class));

        mvc.perform(post("/car/editCar")
                        .param("carId", "car-1")
                        .param("carName", "Honda")
                        .param("carColor", "White")
                        .param("carQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).update(eq("car-1"), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).deleteCarById("car-1");

        mvc.perform(post("/car/deleteCar")
                        .param("carId", "car-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).deleteCarById("car-1");
    }
}