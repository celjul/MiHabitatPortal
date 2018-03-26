package com.bstmexico.mihabitat.web.controller.test.administrador;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring-mvc.xml", "/spring-context.xml"})
@DatabaseSetup(value = { "/datasets/catalogos.xml", "/datasets/paises.xml", 
		"/datasets/estados.xml", "/datasets/municipios.xml", 
		"/datasets/colonias.xml", "/datasets/personas.xml", 
		"/datasets/usuarios.xml", "/datasets/condominios.xml", 
		"/datasets/mantenimientos.xml", "/datasets/contactos.xml",
		"/datasets/departamentos.xml", "/datasets/grupos.xml"
		})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class ContactoControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	 @Before
	public void setUp() {
		//We have to reset our mock between tests because the mock objects
		//are managed by the Spring container. If we would not reset them,
		//stubbing and verified behavior would "leak" from one test to another.
//			Mockito.reset(todoServiceMock);
 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	 
	@Test
	public void primerTest() throws Exception {
		String json = "{\"activo\":true,\"apellidoMaterno\":\"Dummymaterno\",\"apellidoPaterno\":\"Dummypaterno\",\"id\":null,\"nombre\":\"Dummynombre\",\"condominio\":{\"administradores\":null,\"direccion\":null,\"id\":1,\"nombre\":null},\"emails\":[{\"@class\":\"com.bstmexico.mihabitat.contactos.model.EmailContacto\",\"direccion\":\"dummy1@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.contactos.model.EmailContacto\",\"direccion\":\"dummy2@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.contactos.model.EmailContacto\",\"direccion\":\"dummy3@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}}],\"persona\":{\"activo\":true,\"apellidoMaterno\":\"Dummymaterno\",\"apellidoPaterno\":\"Dummypaterno\",\"id\":null,\"nombre\":\"Dummynombre\",\"usuario\":null,\"telefonos\":[{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona\",\"extension\":\"111\",\"id\":null,\"lada\":\"111\",\"numero\":\"11-111-111\",\"tipo\":{\"id\":10,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona\",\"extension\":\"222\",\"id\":null,\"lada\":\"222\",\"numero\":\"22-222-222\",\"tipo\":{\"id\":10,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona\",\"extension\":\"333\",\"id\":null,\"lada\":\"333\",\"numero\":\"33-333-333\",\"tipo\":{\"id\":10,\"descripcion\":null}}],\"emails\":[{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.EmailPersona\",\"direccion\":\"dummy1@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.EmailPersona\",\"direccion\":\"dummy2@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.EmailPersona\",\"direccion\":\"dummy3@mihabitat.com.mx\",\"id\":null,\"tipo\":{\"id\":1,\"descripcion\":null}}]},\"telefonos\":[{\"@class\":\"com.bstmexico.mihabitat.contactos.model.TelefonoContacto\",\"extension\":\"111\",\"id\":null,\"lada\":\"111\",\"numero\":\"11-111-111\",\"tipo\":{\"id\":10,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.contactos.model.TelefonoContacto\",\"extension\":\"222\",\"id\":null,\"lada\":\"222\",\"numero\":\"22-222-222\",\"tipo\":{\"id\":10,\"descripcion\":null}},{\"@class\":\"com.bstmexico.mihabitat.contactos.model.TelefonoContacto\",\"extension\":\"333\",\"id\":null,\"lada\":\"333\",\"numero\":\"33-333-333\",\"tipo\":{\"id\":10,\"descripcion\":null}}],\"departamentos\":null}";
		mockMvc.perform(MockMvcRequestBuilders.post("/administrador/contactos/guardar?notificaciones=angelin_co@hotmail.com")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json.getBytes()))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

}
