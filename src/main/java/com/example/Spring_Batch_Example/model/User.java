package com.example.Spring_Batch_Example.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;
}
