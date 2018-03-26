package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.List;

import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReportePagos {

	private Short anio;

	private Condominio condominio;

	private List<PagoDetalle> pagos;

	public ReportePagos() {
		super();
	}

	public Short getAnio() {
		return anio;
	}

	public void setAnio(Short anio) {
		this.anio = anio;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<PagoDetalle> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagoDetalle> pagos) {
		this.pagos = pagos;
	}

	public void addPago(PagoDetalle pago) {
		if (this.pagos == null) {
			this.pagos = new ArrayList<PagoDetalle>();
		}
		if (this.pagos.contains(pago)) {
			for (PagoDetalle p : this.pagos) {
				if (p.equals(pago)) {
					p.setEnero(p.getEnero().add(pago.getEnero()));
					p.setFebrero(p.getFebrero().add(pago.getFebrero()));
					p.setMarzo(p.getMarzo().add(pago.getMarzo()));
					p.setAbril(p.getAbril().add(pago.getAbril()));
					p.setMayo(p.getMayo().add(pago.getMayo()));
					p.setJunio(p.getJunio().add(pago.getJunio()));
					p.setJulio(p.getJulio().add(pago.getJulio()));
					p.setAgosto(p.getAgosto().add(pago.getAgosto()));
					p.setSeptiembre(p.getSeptiembre().add(pago.getSeptiembre()));
					p.setOctubre(p.getOctubre().add(pago.getOctubre()));
					p.setNoviembre(p.getNoviembre().add(pago.getNoviembre()));
					p.setDiciembre(p.getDiciembre().add(pago.getDiciembre()));
					break;
				}
			}
		} else {
			this.pagos.add(pago);
		}
	}
}
