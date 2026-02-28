package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ProductControllerTest {

    private MockMvc mockMvc;
    private FakeProductService fakeService;

    @BeforeEach
    void setUp() {
        fakeService = new FakeProductService();

        // matches your controller (constructor injection)
        ProductController controller = new ProductController(fakeService);

        View dummyView = new AbstractView() {
            @Override
            protected void renderMergedOutputModel(Map<String, Object> model,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
            }
        };

        mockMvc = standaloneSetup(controller)
                .setViewResolvers((viewName, locale) -> {
                    if (viewName != null && viewName.startsWith("redirect:")) {
                        String target = viewName.substring("redirect:".length());
                        RedirectView rv = new RedirectView(target);
                        rv.setContextRelative(false);
                        return rv;
                    }
                    return dummyView;
                })
                .build();
    }

    @Test
    void createProductPage_shouldReturnCreateProductView_andProvideProductModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("createProduct"));
    }

    @Test
    void createProductPost_shouldCallServiceCreate_andRedirectToList() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Item A")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        assertEquals(1, fakeService.createdProducts.size());
        assertEquals("Item A", fakeService.createdProducts.get(0).getProductName());
        assertEquals(10, fakeService.createdProducts.get(0).getProductQuantity());
    }

    @Test
    void productListPage_shouldReturnProductListView_andContainProducts() throws Exception {
        Product p1 = new Product();
        p1.setProductId("1");
        p1.setProductName("A");
        p1.setProductQuantity(1);

        Product p2 = new Product();
        p2.setProductId("2");
        p2.setProductName("B");
        p2.setProductQuantity(2);

        fakeService.storage.put("1", p1);
        fakeService.storage.put("2", p2);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("productList"));

        // optional: verify size in model
        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) mockMvc.perform(get("/product/list"))
                .andReturn()
                .getModelAndView()
                .getModel()
                .get("products");
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void editProductPage_shouldReturnEditProductView_andProvideProductModel() throws Exception {
        Product p = new Product();
        p.setProductId("10");
        p.setProductName("Old");
        p.setProductQuantity(3);

        fakeService.storage.put("10", p);

        mockMvc.perform(get("/product/edit/10"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("editProduct"));
    }

    @Test
    void editProductPost_shouldCallServiceUpdate_andRedirect() throws Exception {
        Product existing = new Product();
        existing.setProductId("99");
        existing.setProductName("Before");
        existing.setProductQuantity(1);
        fakeService.storage.put("99", existing);

        mockMvc.perform(post("/product/edit")
                        .param("productId", "99")
                        .param("productName", "After")
                        .param("productQuantity", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        assertEquals("99", fakeService.lastUpdateId);
        assertNotNull(fakeService.lastUpdatedProduct);
        assertEquals("After", fakeService.lastUpdatedProduct.getProductName());
        assertEquals(7, fakeService.lastUpdatedProduct.getProductQuantity());

        // ensure storage updated too
        Product updated = fakeService.storage.get("99");
        assertNotNull(updated);
        assertEquals("After", updated.getProductName());
        assertEquals(7, updated.getProductQuantity());
    }

    @Test
    void deleteProduct_shouldCallServiceDelete_andRedirect() throws Exception {
        Product p = new Product();
        p.setProductId("123");
        p.setProductName("X");
        p.setProductQuantity(1);
        fakeService.storage.put("123", p);

        mockMvc.perform(post("/product/delete")
                        .param("productId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        assertEquals("123", fakeService.lastDeletedId);
        assertFalse(fakeService.storage.containsKey("123"));
    }

    static class FakeProductService implements ProductService {
        Map<String, Product> storage = new LinkedHashMap<>();
        List<Product> createdProducts = new ArrayList<>();

        String lastUpdateId;
        Product lastUpdatedProduct;
        String lastDeletedId;

        @Override
        public Product create(Product product) {
            createdProducts.add(product);
            if (product.getProductId() != null) {
                storage.put(product.getProductId(), product);
            }
            return product;
        }

        @Override
        public List<Product> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Product findById(String id) {
            return storage.get(id);
        }

        @Override
        public void update(String id, Product product) {
            lastUpdateId = id;
            lastUpdatedProduct = product;

            Product existing = storage.get(id);
            if (existing == null) return;

            existing.setProductName(product.getProductName());
            existing.setProductQuantity(product.getProductQuantity());
        }

        @Override
        public void deleteProductById(String id) {
            lastDeletedId = id;
            storage.remove(id);
        }
    }
}