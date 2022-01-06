package com.geekbrains.backend.test.miniMarket.model;

public class ProductResponse {
    private Product product;
    private String error;

    public boolean ok() {
        return product != null;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "product=" + product +
                ", error='" + error + '\'' +
                '}';
    }
}
