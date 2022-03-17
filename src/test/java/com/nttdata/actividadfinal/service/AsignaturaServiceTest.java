package com.nttdata.actividadfinal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.entity.Asignatura;
import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class AsignaturaServiceTest {

	private Asignatura asig1, asig2;
	
	@Autowired
	AsignaturaRepoJPA repo;
	
	@Autowired
	AsignaturaService service;
	
	
	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
		
		asig1 = new Asignatura();
		asig1.setNombre("LGG");
		asig1.setCurso(1);
		asig1.setDescripcion("iNVETADA");
		asig1=repo.save(asig1);
	
		asig2 = new Asignatura();
		asig2.setNombre("PGV");
		asig2.setCurso(4);
		asig2.setDescripcion("JUEGO UNREAL ENGINE");
		asig2=repo.save(asig2);
	}

	@AfterEach
	void tearDown() throws Exception {
		repo.deleteAll();
	}

	@Test
	void testListar() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de inserción (testListar Service)");
		
		//WHEN
		List<Asignatura> a = service.listar();
		
		//THEN
		assertEquals(2, a.size(), "Hay 2 asignaturas en BBDD tras inserción (testListar Service");
		assertEquals(asig1, a.get(0), "asignaturas1 insertada igual en BDD (testListar Service");
		assertEquals(asig2, a.get(1), "asignaturas2 insertada igual en BDD (testListar Service");
	}

	@Test
	void testGetById() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de buscar por id (testLGetById Service)");
		
		//WHEN
		Asignatura u1 = service.getById(asig1.getId());
		Asignatura u2 = service.getById(99);
				
		//THEN
		assertEquals (asig1.getId(), u1.getId(), "Misma Asignatura (testLGetById Service)");
		assertNotNull (u1, "Asignatura válida, no null (testLGetById Service)");
		assertNull (u2, "Asignatura no existente en BDD (testLGetById Service)");
	}

	@Test
	void testDeleteAll() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar (testDeleteAll Service)");
		
		//WHEN
		service.deleteAll();
				
		//THEN
		assertEquals(0, service.listar().size(), "0 asig en BBDD despues de borrar (testDeleteAll Service)");
	}

	@Test
	void testDeleteById() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testDeleteById Service)");
				
		//WHEN
		//Integer id = asig1.getId();
		service.deleteById(asig1.getId());
		Asignatura u1 = service.getById(asig1.getId());//buscamos su el id ya se ha eliminado y devuelve null
				
		//THEN
		assertEquals(1, service.listar().size(), "0 asig en BBDD despues de borrar por id (testDeleteById Service)");
		assertNull (u1, "Asignatura eliminada no encontrada (testDeleteById Service)");
		
	}

	@Test
	void testInsertar() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de insertar (testInsertar Service)");
		
		//WHEN
		Asignatura u1 = new Asignatura();
		u1.setNombre("NEW");
		u1.setCurso(3);
		u1.setDescripcion("NEW ASIG");
		u1=repo.save(u1);
				
		//THEN
		assertEquals(3, service.listar().size(), "3 asig en BBDD DESDEPUES de insertar (testInsertar Service)");
		assertEquals(u1, service.getById(u1.getId()), "Objeto nuevo insertado bien (testInsertar Service)");
	}

}
