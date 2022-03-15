package com.nttdata.actividadfinal.restcontroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.actividadfinal.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@RestController
@RequestMapping("api/asignatura")
public class AsignaturaRestController {

	@Autowired
	AsignaturaService asignaturaService;
	
	@GetMapping
	@Cacheable(value="asignatura")
	public ResponseEntity<List<Asignatura>> listarAsignatura(){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Message", "Listado de todas las asignaturas");
			return new ResponseEntity<> (asignaturaService.listar(), headers, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/{id}")
	@Cacheable(value="asignatura")
	public ResponseEntity<Asignatura> listarAsignaturaPorId(@PathVariable("id") Integer id){
		try {
			HttpHeaders headers = new HttpHeaders();
			Asignatura asig = asignaturaService.getById(id);
			if( asig == null ) {
				headers.set("Message", "No Existe la asignatura con id "+id);
				return new ResponseEntity<> (null, headers, HttpStatus.NOT_FOUND);
			}else {
				headers.set("Message", "Asignatura con id "+id);
				return new ResponseEntity<> (asig, headers, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping
	@CacheEvict(value="asignatura", allEntries = true)
	public ResponseEntity<Asignatura> EliminarAsignatura(){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Message", "Todas las asignaturas borradas");
			asignaturaService.deleteAll();
			return new ResponseEntity<> (null, headers, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/{id}")
	@CacheEvict(value="asignatura", allEntries = true)
	public ResponseEntity<Asignatura> EliminarAsignatura(@PathVariable("id") Integer id){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Message", "Asignatura borrada con id "+id);
			asignaturaService.deleteById(id);
			return new ResponseEntity<> (null, headers, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	@CacheEvict(value="asignatura", allEntries = true)
	public ResponseEntity<Asignatura> modificarAsignatura (@RequestBody Asignatura asig) {
		try {
			HttpHeaders headers = new HttpHeaders();
			if (asig.getId()==null) {
				headers.set("Message", "Modificar una Asignatura debe ir con su id");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
			else if (asig.getNombre().equals("") || asig.getDescripcion().equals("")
				|| asig.getCurso() == null) {
				headers.set("Message", "Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			
			Asignatura asignatura = asignaturaService.insertar(asig);
			URI newPath = new URI("/api/asignatura/"+asignatura.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura insertada correctamente con id: "+asignatura.getId());
			
			return new ResponseEntity<> (asignatura, headers, HttpStatus.OK);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	@CacheEvict(value="asignatura", allEntries = true)
	public ResponseEntity<Asignatura> agregarAsignatura (@RequestBody Asignatura asig) {
		try {
			HttpHeaders headers = new HttpHeaders();
			if (asig.getId()!=null) {
				headers.set("Message", "Para agregar una Asignatura id debe ser null");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
			else if (asig.getNombre().equals("") || asig.getDescripcion().equals("")
				|| asig.getCurso() == null) {
				headers.set("Message", "Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			
			Asignatura asignatura = asignaturaService.insertar(asig);
			URI newPath = new URI("/api/asignatura/"+asignatura.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura insertada correctamente con id: "+asignatura.getId());
			
			return new ResponseEntity<> (asignatura, headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
