package com.ashu.filepdf.filemytax.model.user;

public class RegistrationResponse {

    public String userUid;
    public String name;
    public String email;

    public String phoneNumber;

    public String profilePhoto;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public RegistrationResponse() {
    }

    public RegistrationResponse(String userUid, String name, String email, String phoneNumber,
                                String profilePhoto) {
        this.userUid = userUid;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "userUid='" + userUid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
