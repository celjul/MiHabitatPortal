package com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Obtiene un listado de transacciones, estas transacciones están paginadas por
 * lo que se devuelven en base al número de página y la cantidad, y en este
 * mismo servicio pueden listarse las devoluciones realizadas mediante el
 * servicio de feenicia. Por cada transacción que se retorna en el listado se
 * muestra la Clave Operación, la clave de venta, el bin de la tarjeta, la
 * terminación de la tarjeta (últimos 4 dígitos), el importe, la fecha de
 * creación, el número de orden, el número de ticket, el número de transacción y
 * el número de recibo.
 * 
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 26-10-2017
 * @version 1.0
 *
 */
public class ListadoTransaccionesRequest implements Serializable {

	private static final long serialVersionUID = -2532041135046975606L;

	/**
	 * MerchantId Id del merchant.
	 */
	@JsonProperty("MerchantId")
	public String merchantId;

	/**
	 * Número de página del listado.
	 */
	@JsonProperty("Page")
	public Integer page;

	/**
	 * Número de transacciones por pagina.
	 */
	@JsonProperty("PageSize")
	public Integer pageSize;

	/**
	 * Siempre true.
	 */
	@JsonProperty("Aprobada")
	public Boolean aprobada;

	/**
	 * Refund true para listar devoluciones, false para listar ventas.
	 */
	@JsonProperty("Refund")
	public Boolean refund;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getAprobada() {
		return aprobada;
	}

	public void setAprobada(Boolean aprobada) {
		this.aprobada = aprobada;
	}

	public Boolean getRefund() {
		return refund;
	}

	public void setRefund(Boolean refund) {
		this.refund = refund;
	}
}
