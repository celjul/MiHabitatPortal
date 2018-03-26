package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteAntiguedadCuentasPagar extends ReporteCuentasPagar {

	private Integer anio;
	
	private Collection<Integer> anios;
	
	private BigDecimal monto;
	
	private Integer dias;

	public ReporteAntiguedadCuentasPagar() {
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

	public void addAdeudoDias(AdeudoDiasProveedor adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<AdeudoProveedor>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (AdeudoProveedor a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoDiasProveedor) a).set_1_30(((AdeudoDiasProveedor) a).get_1_30().add(adeudo.get_1_30()));
					((AdeudoDiasProveedor) a).set_31_60(((AdeudoDiasProveedor) a).get_31_60().add(adeudo.get_31_60()));
					((AdeudoDiasProveedor) a).set_61_90(((AdeudoDiasProveedor) a).get_61_90().add(adeudo.get_61_90()));
					((AdeudoDiasProveedor) a).set_91_180(((AdeudoDiasProveedor) a).get_91_180().add(adeudo.get_91_180()));
					((AdeudoDiasProveedor) a).set_181(((AdeudoDiasProveedor) a).get_181().add(adeudo.get_181()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
	
	public void addAdeudoMeses(AdeudoMesesProveedor adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<AdeudoProveedor>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (AdeudoProveedor a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoMesesProveedor) a).setEnero(((AdeudoMesesProveedor) a).getEnero().add(adeudo.getEnero()));
					((AdeudoMesesProveedor) a).setFebrero(((AdeudoMesesProveedor) a).getFebrero().add(adeudo.getFebrero()));
					((AdeudoMesesProveedor) a).setMarzo(((AdeudoMesesProveedor) a).getMarzo().add(adeudo.getMarzo()));
					((AdeudoMesesProveedor) a).setAbril(((AdeudoMesesProveedor) a).getAbril().add(adeudo.getAbril()));
					((AdeudoMesesProveedor) a).setMayo(((AdeudoMesesProveedor) a).getMayo().add(adeudo.getMayo()));
					((AdeudoMesesProveedor) a).setJunio(((AdeudoMesesProveedor) a).getJunio().add(adeudo.getJunio()));
					((AdeudoMesesProveedor) a).setJulio(((AdeudoMesesProveedor) a).getJulio().add(adeudo.getJulio()));
					((AdeudoMesesProveedor) a).setAgosto(((AdeudoMesesProveedor) a).getAgosto().add(adeudo.getAgosto()));
					((AdeudoMesesProveedor) a).setSeptiembre(((AdeudoMesesProveedor) a).getSeptiembre().add(adeudo.getSeptiembre()));
					((AdeudoMesesProveedor) a).setOctubre(((AdeudoMesesProveedor) a).getOctubre().add(adeudo.getOctubre()));
					((AdeudoMesesProveedor) a).setNoviembre(((AdeudoMesesProveedor) a).getNoviembre().add(adeudo.getNoviembre()));
					((AdeudoMesesProveedor) a).setDiciembre(((AdeudoMesesProveedor) a).getDiciembre().add(adeudo.getDiciembre()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
	
	public void addAdeudoAnios(AdeudoAniosProveedor adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<AdeudoProveedor>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (AdeudoProveedor a : this.adeudos) {
				if (a.equals(adeudo)) {
					((AdeudoAniosProveedor) a).setAnio_1(((AdeudoAniosProveedor) a).getAnio_1().add(adeudo.getAnio_1()));
					((AdeudoAniosProveedor) a).setAnio_2(((AdeudoAniosProveedor) a).getAnio_2().add(adeudo.getAnio_2()));
					((AdeudoAniosProveedor) a).setAnio_3(((AdeudoAniosProveedor) a).getAnio_3().add(adeudo.getAnio_3()));
					((AdeudoAniosProveedor) a).setAnio_4(((AdeudoAniosProveedor) a).getAnio_4().add(adeudo.getAnio_4()));
					((AdeudoAniosProveedor) a).setAnio_5(((AdeudoAniosProveedor) a).getAnio_5().add(adeudo.getAnio_5()));
				}
			}
		} else {
			if (adeudo.getTotal().compareTo(BigDecimal.ZERO) != 0) {
				this.adeudos.add(adeudo);
			}
		}
	}
}
