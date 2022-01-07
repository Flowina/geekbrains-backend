package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.qameta.allure.Description;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniMarketApiTests {
    MiniMarketService ms;
    private static Product lastStateProduct;


    @BeforeEach
    void init() {
        ms = new MiniMarketService();
    }

    @Test
    @Order(1)
    @Description("Create Product")
    void createProduct() throws IOException {
        Product product = new Product();
        product.setTitle("tomato");
        product.setPrice(125);
        product.setCategoryTitle("Food");
        ProductResponse resp = ms.createProduct(product);
        lastStateProduct = resp.getProduct();


        assertThat(resp.ok(), is(true));
        assertEquals(lastStateProduct.getCategoryTitle(), product.getCategoryTitle());
        assertEquals(lastStateProduct.getPrice(), product.getPrice());
        assertEquals(lastStateProduct.getTitle(), product.getTitle());

    }

    @Test
    @Order(2)
    @Description("Get Products")
    void getProducts() throws IOException {
        ProductsResponse resp = ms.getProducts();
        assertThat(resp.ok(), is(true));
        assertThat(resp.getProducts().size() > 0, is(true));
    }

    @Test
    @Order(3)
    @Description("Update Product")
    void updateProducts() throws IOException {
        Product product = new Product();
        product.setTitle("potato");
        product.setPrice(500);
        product.setCategoryTitle("Food");
        product.setId(lastStateProduct.getId());
        ProductResponse resp = ms.updateProduct(product);
        lastStateProduct = resp.getProduct();

        assertEquals(lastStateProduct.getId(), lastStateProduct.getId());
        assertEquals(lastStateProduct.getCategoryTitle(), product.getCategoryTitle());
        assertEquals(lastStateProduct.getPrice(), product.getPrice());
        assertEquals(lastStateProduct.getTitle(), product.getTitle());
    }

    @Test
    @Order(4)
    @Description("Get Product by Id")
    void getProductByID() throws IOException {
        ProductResponse resp = ms.getProduct(lastStateProduct.getId());

        assertThat(resp.ok(), is(true));
        assertEquals(resp.getProduct().getId(), lastStateProduct.getId());
        assertEquals(resp.getProduct().getPrice(), lastStateProduct.getPrice());
        assertEquals(resp.getProduct().getTitle(), lastStateProduct.getTitle());
        assertEquals(resp.getProduct().getCategoryTitle(), lastStateProduct.getCategoryTitle());
    }

    @Test
    @Order(5)
    @Description("Delete Product")
    void deleteProductByID() throws IOException {
        DeleteResponse resp = ms.deleteProduct(lastStateProduct.getId());
        assertThat(resp.ok(), is(true));
        ProductResponse productResponse = ms.getProduct(lastStateProduct.getId());
        assertThat(productResponse.ok(), is(false));
    }

    @Test
    @Description("Get Category")
    void getCategory() throws IOException {
        Long categoryId = 1L;
        CategoryResponse resp = ms.getCategory(categoryId);
        assertThat(resp.ok(), is(true));
        assertThat(resp.getCategory().getId() == categoryId, is(true));
    }
}
