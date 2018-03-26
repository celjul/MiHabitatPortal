package com.bstmexico.mihabitat.web.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class JsonError {

	private final String message;

	public JsonError(String message) {
		this.message = message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView asModelAndView() {
		Map map = new HashMap();
		map.put("error", message);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		return new ModelAndView(jsonView, map);
	}
}
