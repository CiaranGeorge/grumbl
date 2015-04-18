package com.complaints.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="roles")
public class RoleEntity {

        @Id
        @GeneratedValue
        @Getter @Setter private Integer id;
        @Getter @Setter private String role;
        
}
