package com.bstmexico.mihabitat.web.controller.test.administrador;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring-mvc.xml", "/spring-context.xml"})
public class DepartamentoControllerTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DepartamentoControllerTest.class);
	
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
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("	\"activo\": true,");
		json.append("	\"condominio\": {");
		json.append("		\"id\": 1");
		json.append("	},");
		json.append("	\"contactos\": [");
		json.append("		{");
		json.append("			\"id\": {");
		json.append("				\"contacto\": {");
		json.append("					\"id\": 9,");
		json.append("					\"persona\": {");
		json.append("						\"apellidoMaterno\": \"Torres\",");
		json.append("						\"apellidoPaterno\": \"Robles\",");
		json.append("						\"emails\": [");
		json.append("							{");
		json.append("								\"direccion\": \"angelin_co@hotmail.com\",");
		json.append("								\"id\": 8,");
		json.append("								\"tipo\": {");
		json.append("									\"descripcion\": \"Personal\",");
		json.append("									\"id\": 4");
		json.append("								}");
		json.append("							}");
		json.append("						],");
		json.append("						\"id\": 9,");
		json.append("						\"nombre\": \"Sandra\"");
//		json.append("						\"usuario\": {");
//		json.append("							\"id\": 3,");
//		json.append("							\"activo\": true,");
//		json.append("							\"user\": \"usuario\",");
//		json.append("							\"roles\": [");
//		json.append("								3");
//		json.append("							],");
//		json.append("							\"email\": \"angelin_co@hotmail.com\"");
//		json.append("						}");
		json.append("					},");
		
		json.append("					\"activo\": true,");
		json.append("					\"condominio\": {");
		json.append("						\"id\": 1,");
		json.append("						\"nombre\": \"Mi Habitat\",");
		json.append("						\"direccion\": {");
		json.append("							\"calle\": \"Bruselas\",");
		json.append("							\"colonia\": {");
		json.append("								\"codigoPostal\": \"06200\",");
		json.append("								\"id\": 1,");
		json.append("								\"municipio\": {");
		json.append("									\"id\": 1,");
		json.append("									\"estado\": {");
		json.append("										\"id\": 1,");
		json.append("										\"pais\": {");
		json.append("											\"id\": 1");
		json.append("										}");
		json.append("									}");
		json.append("								},");
		json.append("								\"nombre\": \"Juárez\"");
		json.append("							},");
		json.append("							\"id\": 1,");
		json.append("							\"noExterior\": \"6\",");
		json.append("							\"referencias\": \"Cerca del Museo de Cera\"");
		json.append("						},");
		json.append("						\"administradores\": [");
		json.append("							{");
		json.append("								\"id\": 1");
		json.append("							}");
		json.append("						]");
		json.append("					},");
		json.append("					\"departamentos\": [");
		json.append("						{");
		json.append("							\"id\": {");
		json.append("								\"departamento\": {");
		json.append("									\"activo\": true,");
		json.append("									\"condominio\": {");
		json.append("										\"id\": 1,");
		json.append("										\"nombre\": \"Mi Habitat\",");
		json.append("										\"direccion\": {");
		json.append("											\"calle\": \"Bruselas\",");
		json.append("											\"colonia\": {");
		json.append("												\"codigoPostal\": \"06200\",");
		json.append("												\"id\": 1,");
		json.append("												\"municipio\": {");
		json.append("													\"id\": 1,");
		json.append("													\"estado\": {");
		json.append("														\"id\": 1,");
		json.append("														\"pais\": {");
		json.append("															\"id\": 1");
		json.append("														}");
		json.append("													}");
		json.append("												},");
		json.append("												\"nombre\": \"Juárez\"");
		json.append("											},");
		json.append("											\"id\": 1,");
		json.append("											\"noExterior\": \"6\",");
		json.append("											\"referencias\": \"Cerca del Museo de Cera\"");
		json.append("										},");
		json.append("										\"administradores\": [");
		json.append("											{");
		json.append("												\"id\": 1");
		json.append("											}");
		json.append("										]");
		json.append("									},");
		json.append("									\"id\": 7,");
		json.append("									\"mantenimiento\": {");
		json.append("										\"condominio\": {");
		json.append("											\"id\": 1,");
		json.append("											\"nombre\": \"Mi Habitat\",");
		json.append("											\"direccion\": {");
		json.append("												\"calle\": \"Bruselas\",");
		json.append("												\"colonia\": {");
		json.append("													\"codigoPostal\": \"06200\",");
		json.append("													\"id\": 1,");
		json.append("													\"municipio\": {");
		json.append("														\"id\": 1,");
		json.append("														\"estado\": {");
		json.append("															\"id\": 1,");
		json.append("															\"pais\": {");
		json.append("																\"id\": 1");
		json.append("															}");
		json.append("														}");
		json.append("													},");
		json.append("													\"nombre\": \"Juárez\"");
		json.append("												},");
		json.append("												\"id\": 1,");
		json.append("												\"noExterior\": \"6\",");
		json.append("												\"referencias\": \"Cerca del Museo de Cera\"");
		json.append("											},");
		json.append("											\"administradores\": [");
		json.append("												{");
		json.append("													\"id\": 1");
		json.append("												}");
		json.append("											]");
		json.append("										},");
		json.append("										\"descripcion\": \"Básico\",");
		json.append("										\"id\": 1,");
		json.append("										\"monto\": 2000");
		json.append("									},");
		json.append("									\"nombre\": \"A 001\"");
		json.append("								}");
		json.append("							},");
		json.append("							\"habitante\": false,");
		json.append("							\"principal\": false,");
		json.append("							\"propietario\": false");
		json.append("						}");
		json.append("					]");
		
		json.append("				},");
		json.append("				\"departamento\": {");
		json.append("					\"mantenimiento\": {");
		json.append("						\"monto\": 0");
		json.append("					}");
		json.append("				}");
		json.append("			},");
		json.append("			\"habitante\": true,");
		json.append("			\"principal\": true,");
		json.append("			\"propietario\": true");
		json.append("		}");
		json.append("	],");
		json.append("	\"grupos\": [");
		json.append("		{");
		json.append("			\"id\": 1");
		json.append("		}");
		json.append("	],");
		json.append("	\"mantenimiento\": {");
		json.append("		\"id\": 1,");
		json.append("		\"monto\": 0");
		json.append("	},");
		json.append("	\"nombre\": \"A 002\",");
		json.append("	\"observaciones\": \"Nuevo\"");
		json.append("}");

		LOGGER.debug(json.toString());

		mockMvc.perform(MockMvcRequestBuilders.post("/administrador/departamentos/guardar?notificaciones=angelin_co@hotmail.com")
				.contentType(TestControllerUtils.APPLICATION_JSON_UTF8)
				.content(json.toString().getBytes()))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

}
