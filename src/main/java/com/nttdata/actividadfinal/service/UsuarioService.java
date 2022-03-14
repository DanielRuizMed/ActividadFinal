package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.entity.Usuario;

public interface UsuarioService {

	List<Usuario> listar();
	Usuario buscarPorUsername(String username);
	List<Usuario> ListarUsuariosPorRol(String rol);
}
