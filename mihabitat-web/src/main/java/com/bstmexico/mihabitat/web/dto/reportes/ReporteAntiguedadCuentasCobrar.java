package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteAntiguedadCuentasCobrar extends ReporteCuentasCobrar {

	private Integer anio;
	
	private Collection<Integer> anios;
	
	private BigDecimal monto;
	
	private Integer dias;

	public ReporteAntiguedadCuentasCobrar() {
		super();
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Collection<Integer> getAnios() {
		return anios;
	}

	public void setAnios(Collection<Integer> anios) {
		this.anios = anios;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public void addAdeudoDias(AdeudoDias adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<Adeudo>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (Adeudo a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoDias) a).set_1_30(((AdeudoDias) a).get_1_30().add(adeudo.get_1_30()));
					((AdeudoDias) a).set_31_60(((AdeudoDias) a).get_31_60().add(adeudo.get_31_60()));
					((AdeudoDias) a).set_61_90(((AdeudoDias) a).get_61_90().add(adeudo.get_61_90()));
					((AdeudoDias) a).set_91_180(((AdeudoDias) a).get_91_180().add(adeudo.get_91_180()));
					((AdeudoDias) a).set_181(((AdeudoDias) a).get_181().add(adeudo.get_181()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
	
	public void addAdeudoMeses(AdeudoMeses adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<Adeudo>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (Adeudo a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoMeses) a).setEnero(((AdeudoMeses) a).getEnero().add(adeudo.getEnero()));
					((AdeudoMeses) a).setFebrero(((AdeudoMeses) a).getFebrero().add(adeudo.getFebrero()));
					((AdeudoMeses) a).setMarzo(((AdeudoMeses) a).getMarzo().add(adeudo.getMarzo()));
					((AdeudoMeses) a).setAbril(((AdeudoMeses) a).getAbril().add(adeudo.getAbril()));
					((AdeudoMeses) a).setMayo(((AdeudoMeses) a).getMayo().add(adeudo.getMayo()));
					((AdeudoMeses) a).setJunio(((AdeudoMeses) a).getJunio().add(adeudo.getJunio()));
					((AdeudoMeses) a).setJulio(((AdeudoMeses) a).getJulio().add(adeudo.getJulio()));
					((AdeudoMeses) a).setAgosto(((AdeudoMeses) a).getAgosto().add(adeudo.getAgosto()));
					((AdeudoMeses) a).setSeptiembre(((AdeudoMeses) a).getSeptiembre().add(adeudo.getSeptiembre()));
					((AdeudoMeses) a).setOctubre(((AdeudoMeses) a).getOctubre().add(adeudo.getOctubre()));
					((AdeudoMeses) a).setNoviembre(((AdeudoMeses) a).getNoviembre().add(adeudo.getNoviembre()));
					((AdeudoMeses) a).setDiciembre(((AdeudoMeses) a).getDiciembre().add(adeudo.getDiciembre()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
	
	public void addAdeudoAnios(AdeudoAnios adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<Adeudo>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (Adeudo a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoAnios) a).setAnio_1(((AdeudoAnios) a).getAnio_1().add(adeudo.getAnio_1()));
					((AdeudoAnios) a).setAnio_2(((AdeudoAnios) a).getAnio_2().add(adeudo.getAnio_2()));
					((AdeudoAnios) a).setAnio_3(((AdeudoAnios) a).getAnio_3().add(adeudo.getAnio_3()));
					((AdeudoAnios) a).setAnio_4(((AdeudoAnios) a).getAnio_4().add(adeudo.getAnio_4()));
					((AdeudoAnios) a).setAnio_5(((AdeudoAnios) a).getAnio_5().add(adeudo.getAnio_5()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
}
