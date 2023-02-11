package com.ashu.filepdf.filemytax.model.user;

public class RegisterUserRequest {

    public String token;

    public String phoneNumber;

    public String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegisterUserRequest(String token, String phoneNumber, String email) {
        this.token = token;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public RegisterUserRequest() {
    }

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "token='" + token + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
