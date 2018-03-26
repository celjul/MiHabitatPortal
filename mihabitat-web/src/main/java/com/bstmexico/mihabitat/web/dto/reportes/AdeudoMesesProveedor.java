package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class AdeudoMesesProveedor extends AdeudoProveedor {

	private BigDecimal enero;

	private BigDecimal febrero;

	private BigDecimal marzo;

	private BigDecimal abril;

	private BigDecimal mayo;

	private BigDecimal junio;

	private BigDecimal julio;

	private BigDecimal agosto;

	private BigDecimal septiembre;

	private BigDecimal octubre;

	private BigDecimal noviembre;

	private BigDecimal diciembre;

	private BigDecimal total;

	public AdeudoMesesProveedor() {
		super();
		this.enero = BigDecimal.ZERO;
		this.febrero = BigDecimal.ZERO;
		this.marzo = BigDecimal.ZERO;
		this.abril = BigDecimal.ZERO;
		this.mayo = BigDecimal.ZERO;
		this.junio = BigDecimal.ZERO;
		this.julio = BigDecimal.ZERO;
		this.agosto = BigDecimal.ZERO;
		this.septiembre = BigDecimal.ZERO;
		this.octubre = BigDecimal.ZERO;
		this.noviembre = BigDecimal.ZERO;
		this.diciembre = BigDecimal.ZERO;
	}

	public BigDecimal getEnero() {
		return enero;
	}

	public void setEnero(BigDecimal enero) {
		this.enero = enero;
	}

	public BigDecimal getFebrero() {
		return febrero;
	}

	public void setFebrero(BigDecimal febrero) {
		this.febrero = febrero;
	}

	public BigDecimal getMarzo() {
		return marzo;
	}

	public void setMarzo(BigDecimal marzo) {
		this.marzo = marzo;
	}

	public BigDecimal getAbril() {
		return abril;
	}

	public void setAbril(BigDecimal abril) {
		this.abril = abril;
	}

	public BigDecimal getMayo() {
		return mayo;
	}

	public void setMayo(BigDecimal mayo) {
		this.mayo = mayo;
	}

	public BigDecimal getJunio() {
		return junio;
	}

	public void setJunio(BigDecimal junio) {
		this.junio = junio;
	}

	public BigDecimal getJulio() {
		return julio;
	}

	public void setJulio(BigDecimal julio) {
		this.julio = julio;
	}

	public BigDecimal getAgosto() {
		return agosto;
	}

	public void setAgosto(BigDecimal agosto) {
		this.agosto = agosto;
	}

	public BigDecimal getSeptiembre() {
		return septiembre;
	}

	public void setSeptiembre(BigDecimal septiembre) {
		this.septiembre = septiembre;
	}

	public BigDecimal getOctubre() {
		return octubre;
	}

	public void setOctubre(BigDecimal octubre) {
		this.octubre = octubre;
	}

	public BigDecimal getNoviembre() {
		return noviembre;
	}

	public void setNoviembre(BigDecimal noviembre) {
		this.noviembre = noviembre;
	}

	public BigDecimal getDiciembre() {
		return diciembre;
	}

	public void setDiciembre(BigDecimal diciembre) {
		this.diciembre = diciembre;
	}

	public BigDecimal getTotal() {
		total = getEnero().add(getFebrero()).add(getMarzo()).add(getAbril())
				.add(getMayo()).add(getJunio()).add(getJulio())
				.add(getAgosto()).add(getSeptiembre()).add(getOctubre())
				.add(getNoviembre()).add(getDiciembre());
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
