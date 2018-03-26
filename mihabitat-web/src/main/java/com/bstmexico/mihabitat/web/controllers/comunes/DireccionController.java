package com.bstmexico.mihabitat.web.controllers.comunes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.direcciones.service.ColoniaService;
import com.bstmexico.mihabitat.comunes.direcciones.service.EstadoService;
import com.bstmexico.mihabitat.comunes.direcciones.service.MunicipioService;
import com.bstmexico.mihabitat.comunes.direcciones.service.PaisService;

@Controller
@RequestMapping(value = "direccion")
public class DireccionController {

	@Autowired
	private PaisService paisService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private ColoniaService coloniaService;

	@RequestMapping(value = "pais/{idPais}/estados")
	public @ResponseBody Collection<Estado> getCatalogoEstados(
			@PathVariable Long idPais) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pais", PaisFactory.newInstance(idPais));

		return estadoService.search(parameters);
	}

	@RequestMapping(value = "estado/{idEstado}/municipios")
	public @ResponseBody Collection<Municipio> getCatalogoMunicipios(
			@PathVariable Long idEstado) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("estado", EstadoFactory.newInstance(idEstado));

		return municipioService.search(parameters);
	}

	@RequestMapping(value = "municipio/{idMunicipio}/colonias")
	public @ResponseBody Collection<Colonia> getCatalogoColonias(
			@PathVariable Long idMunicipio) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("municipio", MunicipioFactory.newInstance(idMunicipio));

		return coloniaService.search(parameters);
	}
}
