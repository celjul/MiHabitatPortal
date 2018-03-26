package com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 26-10-2017
 * @version 1.0
 *
 */
public class ListadoTransaccionesResponse implements Serializable {

	private static final long serialVersionUID = -3132371138552094426L;

	/**
	 * Código del resultado de la petición.
	 */
	private String responseCode;

	/**
	 * Número total de transacciones.
	 */
	@JsonProperty("TotalTransacciones")
	private Integer totalTransacciones;

	/**
	 * Listado de transacciones y sus atributos.
	 */
	@JsonProperty("TransactionsDetail")
	private List<TransactionDetail> transactionsDetail;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getTotalTransacciones() {
		return totalTransacciones;
	}

	public void setTotalTransacciones(Integer totalTransacciones) {
		this.totalTransacciones = totalTransacciones;
	}

	public List<TransactionDetail> getTransactionsDetail() {
		return transactionsDetail;
	}

	public void setTransactionsDetail(List<TransactionDetail> transactionsDetail) {
		this.transactionsDetail = transactionsDetail;
	}
}
