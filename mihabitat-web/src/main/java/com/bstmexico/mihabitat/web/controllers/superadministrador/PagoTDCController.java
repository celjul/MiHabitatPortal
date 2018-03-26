package com.bstmexico.mihabitat.web.controllers.superadministrador;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes.ListadoTransaccionesResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaReporteService;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 21-02-2018
 * @version 1.0
 *
 */
@Controller
@RequestMapping(value = "super-administrador/pagos-tdc")
public class PagoTDCController {

	@Autowired
	private FeeniciaReporteService feeniciaReporteService;
	


	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "reportes/transacciones")
	public ListadoTransaccionesResponse getTransacciones(HttpSession session) throws Exception {
		Condominio condominio = (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue());
		ListadoTransaccionesResponse transacciones = feeniciaReporteService.get(condominio);
		return transacciones;
	}
}
