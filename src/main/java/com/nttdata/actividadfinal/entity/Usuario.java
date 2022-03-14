package com.nttdata.actividadfinal.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table
public class Usuario implements UserDetails{

	@Id
	@Column
	private String username;
	
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombreYapellidos;
	
	@Column(nullable = false)
	private String password;
	
	@OneToOne (optional=false)
	private Rol rol;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombreYapellidos() {
		return nombreYapellidos;
	}

	public void setNombreYapellidos(String nombreYapellidos) {
		this.nombreYapellidos = nombreYapellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(this.rol);
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
	
}
