package com.nttdata.actividadfinal.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.entity.Asignatura;
import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.service.AsignaturaService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class AsignaturaRestControllerTest {

	private Asignatura asig1, asig2;
	
	@Autowired
	AsignaturaRepoJPA repo;
	
	@Autowired
	AsignaturaService service;
	
	@Autowired
	AsignaturaRestController restcontroller;
	
	@Mock // --> simular
	AsignaturaService serviceMock;
	
	@InjectMocks
	AsignaturaRestController restcontrollerMock;
	
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
	void testListarAsignatura_v1() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de inserción (testListar v1 RestController)");
		
		//WHEN
		ResponseEntity<List<Asignatura>> a = restcontroller.listarAsignatura();
		
		//THEN
		assertEquals(2, a.getBody().size(), "Hay 2 asignaturas en BBDD tras inserción (testListar v1 RestController");
		assertEquals(asig1, a.getBody().get(0), "asignaturas1 listada bien (testListar v1 RestController");
		assertEquals(asig2, a.getBody().get(1), "asignaturas2 listada bien (testListar v1 RestController");
		assertThat ( a.getStatusCodeValue() ).isEqualTo(200);
		assertEquals("Listado de todas las asignaturas", a.getHeaders().get("Message").get(0), "cabecera correcta Message (testListar v1 RestController");
		
	}
	
	@Test
	void testListarAsignatura_v3_Exception ()  throws Exception {
		//GIVEN
		when ( serviceMock.listar() ).thenThrow (new Exception());
		
		//WHEN:
		ResponseEntity<List<Asignatura>> re = restcontrollerMock.listarAsignatura();
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción (testListar v2 RestController)");		
		
	}

	@Test
	void testListarAsignaturaPorId_v1() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de buscar por id (testListarAsignaturaPorId_v1 RestController)");
		
		//WHEN
		Integer id=99;
		ResponseEntity<Asignatura> u2 = restcontroller.listarAsignaturaPorId(id);
				
		//THEN
		assertNull (u2.getBody(), "Asignatura no existente en BDD (testListarAsignaturaPorId_v1 RestController)");
		assertEquals(HttpStatus.NOT_FOUND, u2.getStatusCode(), "Excepción (testListarAsignaturaPorId_v1 RestController)");		
		assertEquals("No Existe la asignatura con id "+id, u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testListarAsignaturaPorId_v1 RestController)");
	}
	
	@Test
	void testListarAsignaturaPorId_v2() throws Exception {
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de buscar por id (testListarAsignaturaPorId_v2 RestController)");
		
		//WHEN
		ResponseEntity<Asignatura> u2 = restcontroller.listarAsignaturaPorId(asig1.getId());
						
		//THEN
		assertNotNull (u2.getBody(), "Asignatura si existente en BDD (testListarAsignaturaPorId_v2 RestController)");
		assertEquals (asig1, u2.getBody(), "Misma Asignatura (testListarAsignaturaPorId_v2 RestController)");
		assertEquals(HttpStatus.OK, u2.getStatusCode(), "Excepción (testListarAsignaturaPorId_v2 RestController)");		
		assertEquals("Asignatura con id "+u2.getBody().getId(), u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testListarAsignaturaPorId_v2 RestController)");
	}
		
	@Test
	void testListarAsignaturaPorId_v3_Exception() throws Exception {
		//GIVEN
		when ( serviceMock.getById(asig1.getId()) ).thenThrow (new Exception());
		
		//WHEN:
		ResponseEntity<Asignatura> u2 = restcontrollerMock.listarAsignaturaPorId(asig1.getId());
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, u2.getStatusCode(), "Excepción (testListarAsignaturaPorId_v3 RestController)");
	}

	@Test
	void testEliminarAsignatura_v1() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar (testEliminarAsignatura_v1 RestController)");
		
		//WHEN
		ResponseEntity<Asignatura> u2 = restcontroller.EliminarAsignatura();
				
		//THEN
		assertEquals(0, service.listar().size(), "0 asig en BBDD despues de borrar (testEliminarAsignatura_v1 RestController)");
		assertEquals(HttpStatus.OK, u2.getStatusCode(), "Excepción (testEliminarAsignatura_v1 RestController)");		
		assertEquals("Todas las asignaturas borradas", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testEliminarAsignatura_v1 RestController)");
	}
	
	@Test
	void testEliminarAsignatura_v2_Exception() throws Exception{
		//GIVEN
		doThrow(new Exception()).when(serviceMock).deleteAll();
		
		//WHEN:
		ResponseEntity<Asignatura> u2 = restcontrollerMock.EliminarAsignatura();
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, u2.getStatusCode(), "Excepción (testEliminarAsignatura_v2_Exception RestController)");
	}

	@Test
	void testEliminarAsignaturaInteger_v1() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testEliminarAsignaturaInteger_v1 RestController)");
				
		//WHEN
		ResponseEntity<Asignatura> u2 = restcontroller.EliminarAsignatura(asig1.getId());
		Asignatura u1 = service.getById(asig1.getId());//buscamos su el id ya se ha eliminado y devuelve null
				
		//THEN
		assertEquals(1, service.listar().size(), "0 asig en BBDD despues de borrar por id (testEliminarAsignaturaInteger_v1 RestController)");
		assertNull (u1, "Asignatura eliminada no encontrada (testEliminarAsignaturaInteger_v1 RestController)");
		assertEquals(HttpStatus.OK, u2.getStatusCode(), "Excepción (testEliminarAsignatura_v1 RestController)");		
		assertEquals("Asignatura borrada con id "+asig1.getId(), u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testEliminarAsignatura_v1 RestController)");
	}
	
	@Test
	void testEliminarAsignaturaInteger_v2_Exception() throws Exception{
		//GIVEN
		doThrow(new Exception()).when(serviceMock).deleteById(asig1.getId());
		
		//WHEN:
		ResponseEntity<Asignatura> u2 = restcontrollerMock.EliminarAsignatura(asig1.getId());
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, u2.getStatusCode(), "Excepción (testEliminarAsignaturaInteger_v2_Exception RestController)");
	}

	@Test
	void testModificarAsignatura_v1() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testModificarAsignatura_v1 RestController)");
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 igual antes de modificar (testModificarAsignatura_v1 v1 RestController");	
		
		//WHEN
		Asignatura copia = new Asignatura();//copia para probar que no se modifica la BD
		copia.setId(asig1.getId());
		copia.setNombre(asig1.getNombre());
		copia.setCurso(asig1.getCurso());
		copia.setDescripcion(asig1.getDescripcion());
		asig1.setId(null);//Tras hacer la copia modifico el id a null para la prueba
		ResponseEntity<Asignatura> u2 = restcontroller.modificarAsignatura(asig1);
				
		//THEN
		assertEquals(copia, service.getById(copia.getId()), "asignaturas1 no modificada (testModificarAsignatura_v1 RestController");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testEliminarAsignatura_v1 RestController)");		
		assertEquals("Modificar una Asignatura debe ir con su id", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testEliminarAsignatura_v1 RestController)");
	}
	
	@Test
	void testModificarAsignatura_v2() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testModificarAsignatura_v2 RestController)");
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 igual antes de modificar (testModificarAsignatura_v2 RestController");	
		
		//WHEN
		Asignatura copia = new Asignatura();//copia para probar que no se modifica la BD
		copia.setId(asig1.getId());
		copia.setNombre(asig1.getNombre());
		copia.setCurso(asig1.getCurso());
		copia.setDescripcion(asig1.getDescripcion());
		asig1.setNombre("");//Tras hacer la copia modifico el nombre a "" para la prueba
		ResponseEntity<Asignatura> u2 = restcontroller.modificarAsignatura(asig1);
				
		//THEN
		assertEquals(copia, service.getById(copia.getId()), "asignaturas1 no modificada (testModificarAsignatura_v2 RestController");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testModificarAsignatura_v2 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testModificarAsignatura_v2 RestController)");
	}
	
	@Test
	void testModificarAsignatura_v3() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testModificarAsignatura_v3 RestController)");
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 igual antes de modificar (testModificarAsignatura_v3 RestController");	
		
		//WHEN
		Asignatura copia = new Asignatura();//copia para probar que no se modifica la BD
		copia.setId(asig1.getId());
		copia.setNombre(asig1.getNombre());
		copia.setCurso(asig1.getCurso());
		copia.setDescripcion(asig1.getDescripcion());
		asig1.setCurso(null);//Tras hacer la copia modifico el curso a null para la prueba
		ResponseEntity<Asignatura> u2 = restcontroller.modificarAsignatura(asig1);
		
		//THEN
		assertEquals(copia, service.getById(copia.getId()), "asignaturas1 no modificada (testModificarAsignatura_v3 RestController");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testModificarAsignatura_v3 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testModificarAsignatura_v3 RestController)");
	}
	
	@Test
	void testModificarAsignatura_v4() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testModificarAsignatura_v4 RestController)");
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 igual antes de modificar (testModificarAsignatura_v4 RestController");	
		
		//WHEN
		Asignatura copia = new Asignatura();//copia para probar que no se modifica la BD
		copia.setId(asig1.getId());
		copia.setNombre(asig1.getNombre());
		copia.setCurso(asig1.getCurso());
		copia.setDescripcion(asig1.getDescripcion());
		asig1.setDescripcion("");//Tras hacer la copia modifico el descripcion a "" para la prueba
		ResponseEntity<Asignatura> u2 = restcontroller.modificarAsignatura(asig1);
		
		//THEN
		assertEquals(copia, service.getById(copia.getId()), "asignaturas1 no modificada (testModificarAsignatura_v4 RestController");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testModificarAsignatura_v4 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testModificarAsignatura_v4 RestController)");
	}
	
	@Test
	void testModificarAsignatura_v5() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de borrar por id (testModificarAsignatura_v5 RestController)");
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 igual antes de modificar (testModificarAsignatura_v5 RestController");	
		
		//WHEN
		asig1.setDescripcion("Nueva descripcion");
		ResponseEntity<Asignatura> u2 = restcontroller.modificarAsignatura(asig1);
		
		//THEN
		assertEquals(asig1, service.getById(asig1.getId()), "asignaturas1 modificada (testModificarAsignatura_v5 RestController");
		assertEquals(HttpStatus.OK, u2.getStatusCode(), "Excepción (testModificarAsignatura_v5 RestController)");		
		assertEquals("Asignatura insertada correctamente con id: "+u2.getBody().getId(), u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testModificarAsignatura_v5 RestController)");
		assertEquals("/api/asignatura/"+u2.getBody().getId(), u2.getHeaders().getLocation().toString(), "cabecera correcta Location (testModificarAsignatura_v5 RestController)");
	}
	
	@Test
	void testModificarAsignatura_v6_Exception() throws Exception{
		//GIVEN
		Asignatura copia = new Asignatura();//copia para probar que no se modifica la BD
		copia.setId(asig1.getId());
		copia.setNombre(asig1.getNombre());
		copia.setCurso(asig1.getCurso());
		copia.setDescripcion(asig1.getDescripcion());
		asig1.setDescripcion("falla Exception");//Tras hacer la copia modifico el descripcion a "" para la prueba
		when ( serviceMock.insertar(asig1) ).thenThrow (new Exception());
		
		//WHEN:
		ResponseEntity<Asignatura> re = restcontrollerMock.modificarAsignatura(asig1);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción (testModificarAsignatura_v6_Exception RestController)");
		assertEquals(copia, service.getById(copia.getId()), "asignaturas1 no modificada (testModificarAsignatura_v6_Exception RestController");
	}

	@Test
	void testAgregarAsignatura_v1() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de agregar por id (testAgregarAsignatura_v1 RestController)");
		
		//WHEN
		Asignatura nueva = new Asignatura();//nueva asignatura
		nueva.setId(100);
		ResponseEntity<Asignatura> u2 = restcontroller.agregarAsignatura(nueva);
				
		//THEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v1 RestController)");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testAgregarAsignatura_v1 RestController)");		
		assertEquals("Para agregar una Asignatura id debe ser null", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testAgregarAsignatura_v1 RestController)");
	}
	
	@Test
	void testAgregarAsignatura_v2() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de agregar por id (testAgregarAsignatura_v2 RestController)");
		
		//WHEN
		Asignatura nueva = new Asignatura();//nueva asignatura
		nueva.setNombre("");
		ResponseEntity<Asignatura> u2 = restcontroller.agregarAsignatura(nueva);
		
		//THEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v2 RestController)");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testAgregarAsignatura_v2 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testAgregarAsignatura_v2 RestController)");
	}
	
	@Test
	void testAgregarAsignatura_v3() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de agregar por id (v RestController)");
		
		//WHEN
		Asignatura nueva = new Asignatura();//nueva asignatura
		nueva.setNombre("hola");
		nueva.setDescripcion("");
		ResponseEntity<Asignatura> u2 = restcontroller.agregarAsignatura(nueva);
		
		//THEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v3 RestController)");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testAgregarAsignatura_v3 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testAgregarAsignatura_v3 RestController)");
	}
	
	@Test
	void testAgregarAsignatura_v4() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de agregar por id (testAgregarAsignatura_v4 RestController)");
		
		//WHEN
		Asignatura nueva = new Asignatura();//nueva asignatura
		nueva.setNombre("hola");
		nueva.setDescripcion("Descripcion de hola");
		nueva.setCurso(null);
		ResponseEntity<Asignatura> u2 = restcontroller.agregarAsignatura(nueva);
		
		//THEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v4 RestController)");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, u2.getStatusCode(), "Excepción (testAgregarAsignatura_v4 RestController)");		
		assertEquals("Ni NOMBRE ni DESCRIPCION ni Curso pueden ser nulos", u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testAgregarAsignatura_v4 RestController)");
	}
	
	@Test
	void testAgregarAsignatura_v5() throws Exception{
		//GIVEN
		assertEquals(2, service.listar().size(), "2 asig en BBDD antes de agregar por id (testAgregarAsignatura_v5 RestController)");
		
		//WHEN
		Asignatura nueva = new Asignatura();//nueva asignatura
		nueva.setNombre("hola");
		nueva.setDescripcion("Descripcion de hola");
		nueva.setCurso(3);
		ResponseEntity<Asignatura> u2 = restcontroller.agregarAsignatura(nueva);
				
		//THEN
		assertEquals(3, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v5 RestController)");
		assertEquals(HttpStatus.CREATED, u2.getStatusCode(), "Excepción (testAgregarAsignatura_v5 RestController)");		
		assertEquals("Asignatura insertada correctamente con id: "+u2.getBody().getId(), u2.getHeaders().get("Message").get(0), "cabecera correcta Message (testAgregarAsignatura_v5 RestController)");
		assertEquals("/api/asignatura/"+u2.getBody().getId(), u2.getHeaders().getLocation().toString(), "cabecera correcta Location (testAgregarAsignatura_v5 RestController)");
	}
	
	@Test
	void testAgregarAsignatura_v6_exception() throws Exception{
		//GIVEN
		Asignatura nueva = new Asignatura();
		nueva.setNombre("hola");
		nueva.setDescripcion("Descripcion de hola");
		nueva.setCurso(3);
		when ( serviceMock.insertar(nueva) ).thenThrow (new Exception());
		
		//WHEN:
		ResponseEntity<Asignatura> re = restcontrollerMock.agregarAsignatura(nueva);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción (testAgregarAsignatura_v6_exception RestController)");
		assertEquals(2, service.listar().size(), "2 asig en BBDD despues de agregar por id (testAgregarAsignatura_v6_exception RestController)");
	}

}
