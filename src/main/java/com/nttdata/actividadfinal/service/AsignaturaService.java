package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.entity.Asignatura;

public interface AsignaturaService {

	public List<Asignatura> listar();

	public Asignatura getById(Integer id);

	public void deleteAll();

	public void deleteById(Integer id);

	public Asignatura insertar(Asignatura asig);
}
