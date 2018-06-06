package com.bstmexico.mihabitat.web.controllers.contacto;


import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;

import mx.openpay.client.Charge;
import mx.openpay.client.Customer;
import mx.openpay.client.core.OpenpayAPI;
import mx.openpay.client.core.requests.transactions.CreateCardChargeParams;
import mx.openpay.client.exceptions.OpenpayServiceException;
import mx.openpay.client.exceptions.ServiceUnavailableException;

@Controller
@RequestMapping(value = "contacto/pagoOpenPay")
public class PagosOpenPay {
	
	OpenpayAPI api = new OpenpayAPI("https://sandbox-api.openpay.mx", 
			  "sk_a502fa18d86447fdbc155aabe75beb95", "mz8cpzsq1p5nyvv1tyny");
	
	@Autowired
	DepartamentoService departamentoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		 Condominio condominio = (Condominio) session.getAttribute("condominio");	
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("items", list);
		return "contacto/pagoOpenPay/nuevo";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "pagar")
	public String pagar(WebRequest request ,Model model, HttpSession session) {
		 Condominio condominio = (Condominio) session.getAttribute("condominio");
		 Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		 String tokenid =  request.getParameter("token_id");
		 String deviceSessionId =  request.getParameter("deviceSessionId");
		 String txt_nombretitular =  request.getParameter("txt_nombretitular");
		 String txt_apellidotitular =  request.getParameter("txt_apellidotitular");
		 String txtMonto =  request.getParameter("txtMonto");
		 
		 CreateCardChargeParams request2 = new CreateCardChargeParams();
			
		Customer customer = new Customer();
		customer.setName(txt_nombretitular);
		customer.setLastName(txt_apellidotitular);
		customer.setEmail(usuario.getEmail());

		request2.cardId(tokenid); // =source_id
		request2.amount(new BigDecimal(txtMonto));
		request2.currency("MXN");
		request2.deviceSessionId(deviceSessionId);
		request2.customer(customer);

		try {
			Charge charge = api.charges().create(request2);
			
			System.out.println(charge.toString());
			
		} catch (OpenpayServiceException | ServiceUnavailableException e) {
		
			e.printStackTrace();
		
		}
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("items", list);
		return "contacto/pagoOpenPay/nuevo";
	}

}