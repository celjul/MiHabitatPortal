package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class AdeudoDiasProveedor extends AdeudoProveedor {

	private BigDecimal _1_30;

	private BigDecimal _31_60;

	private BigDecimal _61_90;

	private BigDecimal _91_180;

	private BigDecimal _181;

	private BigDecimal total;

	public AdeudoDiasProveedor() {
		super();
		this._1_30 = BigDecimal.ZERO;
		this._31_60 = BigDecimal.ZERO;
		this._61_90 = BigDecimal.ZERO;
		this._91_180 = BigDecimal.ZERO;
		this._181 = BigDecimal.ZERO;
	}

	public BigDecimal get_1_30() {
		return _1_30;
	}

	public void set_1_30(BigDecimal _1_30) {
		this._1_30 = _1_30;
	}

	public BigDecimal get_31_60() {
		return _31_60;
	}

	public void set_31_60(BigDecimal _31_60) {
		this._31_60 = _31_60;
	}

	public BigDecimal get_61_90() {
		return _61_90;
	}

	public void set_61_90(BigDecimal _61_90) {
		this._61_90 = _61_90;
	}

	public BigDecimal get_91_180() {
		return _91_180;
	}

	public void set_91_180(BigDecimal _91_180) {
		this._91_180 = _91_180;
	}

	public BigDecimal get_181() {
		return _181;
	}

	public void set_181(BigDecimal _181) {
		this._181 = _181;
	}

	public BigDecimal getTotal() {
		total = get_1_30().add(get_31_60()).add(get_61_90()).add(get_91_180())
				.add(get_181());
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
