package com.example.appbanhang.models;

import java.util.List;

public class BrandModel {
    boolean success;
    String message;
    List<Brand> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Brand> getResult() {
        return result;
    }

    public void setResult(List<Brand> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
