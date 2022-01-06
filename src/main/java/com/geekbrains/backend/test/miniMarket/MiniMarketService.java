package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MiniMarketService {

    private final MiniMarketApi api;


    public MiniMarketService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MiniMarketApi.class);
    }

    public ProductResponse getProduct(Long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        ProductResponse productResponse = new ProductResponse();
        if (response.isSuccessful()) {
            productResponse.setProduct(response.body());
        } else {
            productResponse.setError(response.errorBody().string());
        }
        return productResponse;
    }

    public ProductsResponse getProducts() throws IOException {
        Response<List<Product>> response = api.getProducts().execute();
        ProductsResponse productsResponse = new ProductsResponse();
        if (response.isSuccessful()) {
            productsResponse.setProducts(response.body());
        } else {
            productsResponse.setError(response.errorBody().string());
        }
        return productsResponse;
    }

    public ProductResponse createProduct(Product product) throws IOException {
        Response<Product> response = api.createProduct(product).execute();
        ProductResponse createResponse = new ProductResponse();
        if (response.isSuccessful()) {
            createResponse.setProduct(response.body());
        } else {
            createResponse.setError(response.errorBody().string());
        }
        return createResponse;
    }

    public ProductResponse updateProduct(Product product) throws IOException {
        Response<Product> response = api.updateProduct(product).execute();
        ProductResponse updateResponse = new ProductResponse();
        if (response.isSuccessful()) {
            updateResponse.setProduct(response.body());
        } else {
            updateResponse.setError(response.errorBody().string());
        }
        return updateResponse;
    }

    public DeleteResponse deleteProduct(Long id) throws IOException {
        Response<Void> response = api.deleteProduct(id).execute();
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setOk(response.isSuccessful());

        if (!response.isSuccessful()) {
            deleteResponse.setError(response.errorBody().string());
        }
        return deleteResponse;
    }

    public CategoryResponse getCategory(Long id) throws IOException {
        Response<Category> response = api.getCategory(id).execute();
        CategoryResponse categoryResponse = new CategoryResponse();
        if (response.isSuccessful()) {
            categoryResponse.setCategory(response.body());
        } else {
            categoryResponse.setError(response.errorBody().string());
        }
        return categoryResponse;
    }


}
