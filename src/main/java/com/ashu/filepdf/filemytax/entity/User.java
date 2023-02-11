package com.ashu.filepdf.filemytax.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "tax_user")
@Entity
@Getter
@Setter
public class User {
    @Id
    public String userId;
    public String email;
    public String name;
    public String state;
    public String city;
    public String address;
    public String imageUrl;
    public String pushNotificationToken;
    public String isFukraUSer;
    public String source;
}
