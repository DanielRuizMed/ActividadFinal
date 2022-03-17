package com.nttdata.actividadfinal.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsignaturaTest {

	private Asignatura a1 = new Asignatura(),
			   a2 = new Asignatura();	
	
	@Test
	void testGetAndSetId() {
		a1.setId(1);
		a2.setId(1);
		assertEquals(1, a1.getId(), "Mismo id (testGetAndSetId)");
		assertEquals(a1, a1, "Mismo objeto (testGetAndSetId)");
		
		Integer num=0;
		assertNotEquals(a1, num, "Distinto objeto (testGetAndSetId)");
		assertEquals(a1, a2, "dos objetos, mismo id (testGetAndSetId)");
		
		assertEquals(a1.hashCode(), a2.hashCode(), "Mismo hash coder (testGetAndSetId)");
		
		a2.setId(null);
		assertNull(a2.getId(), "id is null (testGetAndSetId)");
		assertNotEquals(a1, a2, "objeto == null (testGetAndSetId)");
		
	}

	//@Test
	void testGetNombre() {
		a1.setNombre("JAVA");
		assertEquals(a1, a1.getNombre(), "Mismo nombre (testGetAndSetId)");
	}

	//@Test
	void testGetDescripcion() {
		a1.setDescripcion("BootCamp de Java");
		assertEquals(a1, a1.getDescripcion(), "Misma descripcion (testGetAndSetId)");
	}

	//@Test
	void testGetCurso() {
		a1.setCurso(1);
		assertEquals(a1, a1.getCurso(), "Mismo curso (testGetAndSetId)");
	}

}
