package com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 26-10-2017
 * @version 1.0
 *
 */
public class TransactionDetail implements Serializable {
	
	private static final long serialVersionUID = 3003887337624987244L;

	/**
	 * Nombre del banco.
	 */
	@JsonProperty("ClaveOperacion")
	private Integer claveOperacion;

	/**
	 * Conteo de transacciones del banco.
	 */
	@JsonProperty("ClaveVenta")
	private String claveVenta;
	
	/**
	 * Primeros 6 dígitos de la tarjeta.
	 */
	@JsonProperty("Bin")
	private String bin;
	
	/**
	 * Últimos 4 dígitos de la tarjeta.
	 */
	@JsonProperty("PanTerminacion")
	private String panTerminacion;
	
	/**
	 * Monto de la transacción.
	 */
	@JsonProperty("Importe")
	private BigDecimal importe;
	
	/**
	 * Fecha de creación.
	 */
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty("DateCreated")
	private Date dateCreated;
	
	/**
	 * Numero de orden.
	 */
	@JsonProperty("OrderId")
	private Integer orderId;
	
	/**
	 * Numero de ticket.
	 */
	private Integer ticketId;
	
	/**
	 * Numero de transacción.
	 */
	private Integer transactionId;
	
	/**
	 * Id del recibo.
	 */
	private String receiptId;
	
	private String cardHolder;
	
	private String numAuth;
	
	private String entryMode;

	public Integer getClaveOperacion() {
		return claveOperacion;
	}

	public void setClaveOperacion(Integer claveOperacion) {
		this.claveOperacion = claveOperacion;
	}

	public String getClaveVenta() {
		return claveVenta;
	}

	public void setClaveVenta(String claveVenta) {
		this.claveVenta = claveVenta;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getPanTerminacion() {
		return panTerminacion;
	}

	public void setPanTerminacion(String panTerminacion) {
		this.panTerminacion = panTerminacion;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getNumAuth() {
		return numAuth;
	}

	public void setNumAuth(String numAuth) {
		this.numAuth = numAuth;
	}

	public String getEntryMode() {
		return entryMode;
	}

	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}
}
