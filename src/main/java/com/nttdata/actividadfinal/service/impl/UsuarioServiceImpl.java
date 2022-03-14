package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.entity.Rol;
import com.nttdata.actividadfinal.entity.Usuario;
import com.nttdata.actividadfinal.repository.UsuarioRepoJPA;
import com.nttdata.actividadfinal.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{

	@Autowired
	UsuarioRepoJPA usuarioDAO;
	
	@Override
	public List<Usuario> listar() {
		return usuarioDAO.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuarioDAO.findById(username).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarPorUsername(username);
	}

	@Override
	public List<Usuario> ListarUsuariosPorRol(String rol) {

		Integer numRol = 0;
		if( rol.equals("ADMIN") ) numRol = 1; 
		if( rol.equals("CONSULTA") ) numRol = 2; 
		
		return usuarioDAO.findByRol_idEquals(numRol);
	}

}
