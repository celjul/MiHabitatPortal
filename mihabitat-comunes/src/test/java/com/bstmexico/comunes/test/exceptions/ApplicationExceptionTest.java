package com.bstmexico.comunes.test.exceptions;

import org.junit.Assert;
import org.junit.Test;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

public class ApplicationExceptionTest {

	@Test
	public void testDefaultMessage() {
		ApplicationException ex = new FactoryException("NO_EXISTE_EL_CODIGO");		
		Assert.assertEquals("Excepción no definida.", ex.getMessage());
	}
	
	@Test
	public void testEmptyExceptions() {
		ApplicationException ex = new FactoryException();		
		Assert.assertEquals("Excepción no definida.", ex.getMessage());
		Assert.assertEquals("EXC000", ex.getCode());
	}
	
	@Test
	public void testCodeExceptios() {
		String code = "EXC001";
		ApplicationException ex = new FactoryException(code);		
		Assert.assertEquals("Excepción no definida.", ex.getMessage());
		Assert.assertEquals(code, ex.getCode());
	}
	
	@Test
	public void testCodeExceptiosCause() {
		String code = "EXC001";
		ApplicationException ex = new FactoryException(code, new RuntimeException());
		
		Assert.assertEquals("Excepción no definida.", ex.getMessage());
		Assert.assertEquals(code, ex.getCode());
		Assert.assertTrue(ex.getCause() instanceof RuntimeException);
	}
	
	@Test
	public void testCauseExceptios() {
		ApplicationException ex = new FactoryException(new RuntimeException());		
		Assert.assertEquals("Excepción no definida.", ex.getMessage());
		Assert.assertEquals("EXC000", ex.getCode());
		Assert.assertTrue(ex.getCause() instanceof RuntimeException);
	}
}
