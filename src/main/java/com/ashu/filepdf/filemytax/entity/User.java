package com.ashu.filepdf.filemytax.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "tax_user")
@Entity
@Getter
@Setter
public class User {
    @Id
    public String userId;
    public String email;
    public String name;
    public String imageUrl;
    @OneToMany
    public Set<Salary> userSalary;
    public String pushNotificationToken;
    public String isFukraUSer;
    public String source;
}
