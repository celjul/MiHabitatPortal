package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class AdeudoAnios extends Adeudo {
	
	private BigDecimal anio_1;
	
	private BigDecimal anio_2;
	
	private BigDecimal anio_3;
	
	private BigDecimal anio_4;
	
	private BigDecimal anio_5;
	
	private BigDecimal total;
	
	public AdeudoAnios() {
		super();
		this.anio_1 = BigDecimal.ZERO;
		this.anio_2 = BigDecimal.ZERO;
		this.anio_3 = BigDecimal.ZERO;
		this.anio_4 = BigDecimal.ZERO;
		this.anio_5 = BigDecimal.ZERO;
	}

	public BigDecimal getAnio_1() {
		return anio_1;
	}

	public void setAnio_1(BigDecimal anio_1) {
		this.anio_1 = anio_1;
	}

	public BigDecimal getAnio_2() {
		return anio_2;
	}

	public void setAnio_2(BigDecimal anio_2) {
		this.anio_2 = anio_2;
	}

	public BigDecimal getAnio_3() {
		return anio_3;
	}

	public void setAnio_3(BigDecimal anio_3) {
		this.anio_3 = anio_3;
	}

	public BigDecimal getAnio_4() {
		return anio_4;
	}

	public void setAnio_4(BigDecimal anio_4) {
		this.anio_4 = anio_4;
	}

	public BigDecimal getAnio_5() {
		return anio_5;
	}

	public void setAnio_5(BigDecimal anio_5) {
		this.anio_5 = anio_5;
	}

	public BigDecimal getTotal() {
		total = getAnio_1().add(getAnio_2()).add(getAnio_3()).add(getAnio_4()).add(getAnio_5());
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
