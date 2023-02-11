package com.ashu.filepdf.filemytax.utils;

public class GoogleUserToken {

    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private boolean email_verified;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private float iat;
    private float exp;


    // Getter Methods

    public String getIss() {
        return iss;
    }

    public String getAzp() {
        return azp;
    }

    public String getAud() {
        return aud;
    }

    public String getSub() {
        return sub;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEmail_verified() {
        return email_verified;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getLocale() {
        return locale;
    }

    public float getIat() {
        return iat;
    }

    public float getExp() {
        return exp;
    }

    // Setter Methods

    public void setIss(String iss) {
        this.iss = iss;
    }

    public void setAzp(String azp) {
        this.azp = azp;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setIat(float iat) {
        this.iat = iat;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }
}
