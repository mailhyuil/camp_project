package com.sb.camp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import javax.validation.constraints.NotBlank;

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
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;

	@NaturalId
	@Column(columnDefinition = "VARCHAR(25)", nullable = false)
	@NotBlank(message = "아이디를 입력해주세요")
	private String username;

	@Column(columnDefinition = "VARCHAR(255)", nullable = false)
	@NotBlank(message = "비밀번호는 필수 입력 값입니다")
	private String password;
	@Transient
	private String re_password;
	@Column(nullable = false)
	@NotBlank(message = "이메일을 입력해주세요")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "닉네임을 입력해주세요")
	private String nickname;

	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean enabled;

	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean accountNonExpired;

	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean accountNonLocked;

	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean credentialsNonExpired;

	@Transient
	Collection<? extends GrantedAuthority> authorities;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Authority> auths;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Bbs> bbs = new ArrayList<>();
}
