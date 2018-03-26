package com.bstmexico.mihabitat.web.dto;

import com.bstmexico.mihabitat.pagos.model.Pago;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2017
 */
public class PagoDto {
	
	private Pago pago;
	
	private com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago pagoTarjeta;

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago getPagoTarjeta() {
		return pagoTarjeta;
	}

	public void setPagoTarjeta(
			com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago pagoTarjeta) {
		this.pagoTarjeta = pagoTarjeta;
	}
}
