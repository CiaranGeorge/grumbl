package com.complaints.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class UserEntity implements UserDetails
{
    public UserEntity() {
        timeUpdated = new Date();
    }
    
	@Id
	@GeneratedValue
	@Column(unique = true)
	@Getter @Setter private Integer id;
	
	@Column(unique = true, nullable = false)
	@NotNull
	@Pattern(regexp="^[A-Za-z0-9_-]+$")
	@Size(min=4, max=12)
	@Setter private String username;
	
	@Size(min=2, max=20)
	@NotNull
	@Pattern(regexp="^[A-Za-z ,.'-]+$")
	@Getter @Setter private String firstname;
	
	@Size(min=2, max=20)
	@NotNull
	@Pattern(regexp="^[A-Za-z ,.'-]+$")
	@Getter @Setter private String lastname;
	
	@Column(unique = true) 
	@NotNull
	@Getter @Setter private String email;
	
    @NotNull
    @Getter @Setter private boolean facebookAccount;
	
	@Getter @Setter private Date timeCreated;
	@Getter @Setter private Date timeUpdated;
	
	@NotNull
	@Setter private String password;

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="ownerId") 
	@OrderBy("timeUpdated DESC")
    @Getter @Setter private Set<ComplaintEntity> complaintEntities = new HashSet<ComplaintEntity>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns={@JoinColumn(name="USER_ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();

	public UserEntity(String username, String passwordHash) {
		this.username = username;
		this.password = passwordHash;
	}

	public Set<RoleEntity> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public void addRole(RoleEntity role) {
		this.roles.add(role);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<RoleEntity> roles = this.getRoles();

		if (roles == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (RoleEntity role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return authorities;
	}
	
	@Override
	public String getUsername() {

		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
}
