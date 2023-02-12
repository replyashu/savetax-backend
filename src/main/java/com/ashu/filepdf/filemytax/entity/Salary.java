package com.ashu.filepdf.filemytax.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "user_salary")
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long salaryId;
    public Long ctc;
    public boolean isOldRegimeUser;
    public String state;
    public String city;
    public String address;
}