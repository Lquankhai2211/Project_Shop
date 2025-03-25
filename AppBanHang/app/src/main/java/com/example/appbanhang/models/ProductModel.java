package com.example.appbanhang.models;

import java.util.List;

public class ProductModel {
    boolean success;
    String message;
    List<Product> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getResult() {
        return result;
    }

    public void setResult(List<Product> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
