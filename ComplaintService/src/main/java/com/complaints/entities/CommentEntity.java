package com.complaints.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
public class CommentEntity {
    
    @Id
    @GeneratedValue
    @Column(unique = true)
    @Getter @Setter private Integer id;
    
    //@Size(min=4, max=50)
    //@NotNull
    //@Getter @Setter private String summary;
    
    @Size(min=10, max=250)
    @NotNull
    @Getter @Setter private String message;
    
    @Getter @Setter private Date timeCreated;
    
    @Getter @Setter private Date timeUpdated;
    
    @Getter @Setter private Integer complaintId;
}
