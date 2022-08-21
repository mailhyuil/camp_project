package com.sb.camp.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@Id
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
	
    @OneToMany(targetEntity = Authority.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
	private Collection<? extends GrantedAuthority> authorities;
	
	private String email;
	private String nickname;
}
