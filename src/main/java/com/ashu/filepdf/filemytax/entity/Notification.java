package com.ashu.filepdf.filemytax.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "notifications")
@Entity
@Getter
@Setter
public class Notification {

    @Id
    public String notificationId;
    @ManyToOne
    public User user;
    public String[] notificationText;
    public boolean isActiveUser;
}
