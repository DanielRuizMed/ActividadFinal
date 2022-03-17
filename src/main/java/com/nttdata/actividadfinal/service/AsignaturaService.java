package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.entity.Asignatura;

public interface AsignaturaService {

	public List<Asignatura> listar() throws Exception;

	public Asignatura getById(Integer id) throws Exception;

	public void deleteAll() throws Exception;

	public void deleteById(Integer id) throws Exception;

	public Asignatura insertar(Asignatura asig) throws Exception;
}
