package com.geekbrains.backend.test.miniMarket.model;

public class CategoryResponse {
    private Category category;
    private String error;

    public boolean ok() {
        return category != null;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "category=" + category +
                ", error='" + error + '\'' +
                '}';
    }
}
