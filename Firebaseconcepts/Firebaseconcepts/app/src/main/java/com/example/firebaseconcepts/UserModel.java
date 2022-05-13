package com.example.firebaseconcepts;

public class UserModel {
    private String userEmail,userId,userImage,userName,userPassword,userNumber;

    public UserModel(String userEmail, String userId, String userImage, String userName, String userPassword, String userNumber) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userImage = userImage;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNumber = userNumber;
    }

    public UserModel() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
