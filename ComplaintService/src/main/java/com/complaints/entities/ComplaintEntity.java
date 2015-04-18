package com.complaints.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import com.complaints.constants.UserStatus;

@Entity
@Table(name = "complaint")
public class ComplaintEntity
{
	ComplaintEntity() {
		this.status = UserStatus.NEW.getStatusCode();
	}
	
    @Id
    @GeneratedValue
    @Column(unique = true)
    @Getter @Setter private Integer id;
    
    @Size(min=4, max=50)
    @NotNull
    @Getter @Setter private String title;
    
    @Size(min=2, max=20)
    @NotNull
    @Pattern(regexp="^[A-Za-z ,.'-]+$")
    @Getter @Setter private String company;
    
    @Size(min=4, max=30)
    @NotNull
    @Pattern(regexp="^[A-Za-z ,.'-]+$")
    @Getter @Setter private String location;
    
    @Size(min=10, max=250)
    @NotNull
    @Getter @Setter private String description;
    
    @Getter @Setter private Date timeCreated;
    @Getter @Setter private Date timeUpdated;
    @Getter @Setter private Integer ownerId;
    @Getter @Setter private char status;
    
    @Transient @Getter @Setter private String imageData;
    
    @Transient
    @Getter @Setter private List<CommentEntity> commentEntities = new ArrayList<CommentEntity>();

}
