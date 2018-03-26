package com.bstmexico.mihabitat.contactos.test.json;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.test.dummy.ContactoDummy;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactoJsonTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoJsonTest.class);
	
	private ObjectMapper mapper = new ObjectMapper();
			
	@Test
	public void testJson() throws JsonProcessingException {
		Condominio condominio = CondominioFactory.newInstance(1L);
		Contacto contacto = ContactoDummy.getFullContacto(condominio, 0);
		
		LOGGER.info(mapper.writeValueAsString(contacto));
	}
	
	@Test
	public void testDeserialize() throws JsonParseException, JsonMappingException, IOException {
		String strContacto = "{\"apellidoMaterno\":\"Gómez\",\"apellidoPaterno\":\"Madrid\",\"emails\":[{\"direccion\":\"jose.manuel@mihabitat.com.mx\",\"tipo\":{\"id\":4},\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.EmailPersona\"}],\"nombre\":\"José Manuel\",\"telefonos\":[{\"lada\":\"775\",\"numero\":\"12355644\",\"tipo\":{\"id\":6},\"@class\":\"com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona\"}],\"usuario\":{\"activo\":true,\"password\":\"123\",\"user\":\"jose.madrid\",\"roles\":[2],\"email\":\"jose.manuel@mihabitat.com.mx\"}}";
		
		Contacto contacto = mapper.readValue(strContacto, Contacto.class);
		LOGGER.info(contacto.toString());
	}
}
