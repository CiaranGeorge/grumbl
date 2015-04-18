package com.complaints.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email")
public class EmailEntity {

    @Id
    @GeneratedValue
    @Getter @Setter private Integer id;
    
    @Getter @Setter private String ip;
    
    @Getter @Setter private Integer userId;
    
    @Getter @Setter private Date emailSent;
    
    
}
