package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.entity.Asignatura;
import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.service.AsignaturaService;

@Service
public class AsignaturaServiceImpl implements AsignaturaService{

	@Autowired
	AsignaturaRepoJPA actividadJPA;
	
	@Override
	public List<Asignatura> listar() throws Exception{
		return actividadJPA.findAll();
	}

	@Override
	public Asignatura getById(Integer id) throws Exception {
		return actividadJPA.findById(id).orElse(null);
	}

	@Override
	public void deleteAll()  throws Exception {
		actividadJPA.deleteAll();
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		actividadJPA.deleteById(id);
	}

	@Override
	public Asignatura insertar(Asignatura asig) throws Exception {
		return actividadJPA.save(asig);
	}

}
