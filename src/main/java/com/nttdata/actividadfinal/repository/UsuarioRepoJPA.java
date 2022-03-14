package com.nttdata.actividadfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nttdata.actividadfinal.entity.Rol;
import com.nttdata.actividadfinal.entity.Usuario;

public interface UsuarioRepoJPA extends JpaRepository<Usuario, String>{

	List<Usuario> findByRol_idEquals(Integer rol);
	
	/*@Query(value="select * from usuario where rol_id=?1", nativeQuery=true)
	List<Usuario> BuscarPorRol(Integer i);*/
}
