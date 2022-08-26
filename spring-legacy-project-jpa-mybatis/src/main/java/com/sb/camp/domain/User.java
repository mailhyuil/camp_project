package com.sb.camp.domain;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "users")
public class User implements UserDetails  {

	private static final long serialVersionUID = 1L;
	
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @NaturalId
	@Column(columnDefinition = "VARCHAR(25)")
	private String username;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String password;
	
	@Column(columnDefinition = "boolean default false")
	private boolean enabled;
	
	@Column(columnDefinition = "boolean default false")
	private boolean accountNonExpired;
	
	@Column(columnDefinition = "boolean default false")
	private boolean accountNonLocked;
	
	@Column(columnDefinition = "boolean default false")
	private boolean credentialsNonExpired;
	
	@Transient
	Collection<? extends GrantedAuthority> authorities;
	
	private String email;
	private String nickname;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<Authority> auths;
}
