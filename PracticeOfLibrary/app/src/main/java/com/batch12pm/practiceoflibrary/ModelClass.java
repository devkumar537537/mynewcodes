package com.batch12pm.practiceoflibrary;

public class ModelClass {
    private String nameText,emailText,number,passwordText,userId;

    public ModelClass(String nameText, String emailText, String number, String passwordText, String userId) {
        this.nameText = nameText;
        this.emailText = emailText;
        this.number = number;
        this.passwordText = passwordText;
        this.userId = userId;
    }

    public ModelClass() {
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
