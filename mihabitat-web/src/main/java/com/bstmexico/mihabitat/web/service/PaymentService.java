package com.bstmexico.mihabitat.web.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.web.dto.Adjunto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface PaymentService {

	/*void sendEmail(Pago pago);*/

	byte[] getRecibo(Pago pago);

	void sendRecibo(Condominio condominio, String mensaje,
					Long idPago, String... emails);

	/*void sendRecibo(final Adjunto recibo, final String mensaje, final Condominio condominio,
			final String... emails);*/
}
