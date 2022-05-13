package com.example.myphpquizapi.models;

public class RegisterModel {
    private String result;

    public RegisterModel(String result) {
        this.result = result;
    }

    public RegisterModel() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
