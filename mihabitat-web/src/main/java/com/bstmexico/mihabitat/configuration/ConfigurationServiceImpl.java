package com.bstmexico.mihabitat.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;

@Service
public class ConfigurationServiceImpl {

	//EMAILS
	@Value("${persona.email.personal}")
	private Long emailPersonal;

	@Value("${roles.superadministrador}")
	private Long idRolSuperAdministrador;

	@Value("${roles.administrador}")
	private Long idRolAdministrador;

	@Value("${vigencia}")
	private int vigencia;

	@Value("${roles.contacto}")
	private Long idRolContacto;

	@Value("${host}")
	private String host;

	@Value("${sendingblue.host}")
	private String sendingblueHost;

	@Value("${sendingblue.key}")
	private String sendingblueKey;

	@Value("${email.direccion.origen.email}")
	private String emailDirOrigenEmail;

	@Value("${email.direccion.origen.nombre}")
	private String emailDirOrigenNombre;

	@Value("${email.contacto}")
	private String emailContacto;

	@Value("${cuentas.padre}")
	private String cuentasPadre;

	@Value("${cuentas.padre.banco}")
	private String cuentaBanco;

	@Value("${cuentas.padre.noCuenta}")
	private String cuentasPadreNoCuenta;

	@Value("${cuentas.hijas.ingresos}")
	private String cuentasHijasIngresos;

	@Value("${cuentas.hijas.ingresos.noCuenta}")
	private String cuentasHijasIngresosNoCuenta;

	@Value("${cuentas.hijas.egresos}")
	private String cuentasHijasEgresos;

	@Value("${cuentas.hijas.egresos.noCuenta}")
	private String cuentasHijasEgresosNoCuenta;

	@Value("${cuentas.hijas.cajas}")
	private String cuentasHijasCajas;

	@Value("${cuentas.hijas.cajas.noCuenta}")
	private String cuentasHijasCajasNoCuenta;

	@Value("${cuentas.hijas.bancos}")
	private String cuentasHijasBancos;

	@Value("${cuentas.hijas.bancos.noCuenta}")
	private String cuentasHijasBancosNoCuenta;

	// /////////////////////////////////////////////////////////////////PAGOS///
	@Value("${pago.estatus.aprobado}")
	private Long pagoAprobado;

	@Value("${pago.estatus.pendiente}")
	private Long pagoPendiente;

	@Value("${pago.estatus.rechazado}")
	private Long pagoRechazado;

	@Value("${pago.estatus.cancelado}")
	private Long pagoCancelado;

	//////////////////////////////////////////////////////////////CARGOS///////////

	@Value("${cargo.tipos.mantenimiento}")
	private Long cargoMantenimiento;

	@Value("${cargo.tipos.consumo}")
	private Long cargoConsumo;

	@Value("${cargo.tipos.instalacion}")
	private Long cargoInstalacion;

	@Value("${cargo.tipos.extraordinarios}")
	private Long cargoExtraordinarios;


	//////////////////////////////////////////////////////////////RECARGOS, TIPOS DE INTERES///////////

	@Value("${tipoInteres.simple}")
	private Long tipoInteresSimple;

	@Value("${tipoInteres.compuesto}")
	private Long tipoInteresCompuesto;

	@Value("${tipoInteres.unico}")
	private Long tipoInteresUnico;

	// /////////////////////////////////////////////////////////////////RESERVACIONES///
	@Value("${reservacion.estatus.pendiente}")
	private Long reservacionPendiente;

	@Value("${reservacion.estatus.reservada}")
	private Long reservacionAprobada;

	@Value("${reservacion.estatus.rechazada}")
	private Long reservacionRechazada;

	@Value("${reservacion.estatus.cancelada}")
	private Long reservacionCancelada;

	@Value("${reservacion.unidades.dia}")
	private Long unidadDia;

	@Value("${reservacion.unidades.hora}")
	private Long unidadHora;

	// /////////////////////////////////////////////////////////////////INCIDENCIAS///
	@Value("${incidencia.tipo.proyecto}")
	private Long incidenciaTipoProyecto;

	@Value("${incidencia.tipo.incidencia}")
	private Long incidenciaTipoIncidencia;

	@Value("${incidencia.estatus.solicitada}")
	private Long incidenciaEstatusSolicitada;

	@Value("${incidencia.estatus.pendiente}")
	private Long incidenciaEstatusPendiente;

	@Value("${incidencia.estatus.repetida}")
	private Long incidenciaEstatusRepetida;

	@Value("${incidencia.estatus.cancelado}")
	private Long incidenciaEstatusCancelado;

	@Value("${incidencia.estatus.concluido}")
	private Long incidenciaEstatusConcluido;

	// /////////////////////////////////////////////////////////////////BLOGS///
	@Value("${blog.tipo.general}")
	private Long blogTipoGeneral;

	@Value("${blog.tipo.condominio}")
	private Long blogTipoCondominio;

	@Value("${blog.general.bienvenidos}")
	private Long blogBienvenidos;

	@Value("${blog.general.noticias}")
	private Long blogNoticias;

	@Value("${blog.general.expertos}")
	private Long blogExpertos;

	@Value("${blog.condominio.avisos}")
	private Long blogAvisos;

	@Value("${blog.condominio.temas}")
	private Long blogTemas;

	@Value("${blog.condominio.documentos}")
	private Long blogDocumentos;

	@Value("${blog.condominio.incidencias}")
	private Long blogIncidencias;

	@Value("${blog.condominio.anuncios}")
	private Long blogAnuncios;

	@Value("${blog.general.howto}")
	private Long blogHowto;

	@Value("${blog.general.faqs}")
	private Long blogFaqs;

	@Value("${blog.general.soporte}")
	private Long blogSoporte;


	// ///////////////////////////////////////////////////////////MOVIMIENTOS///
	@Value("${movimiento.tipo.cargo}")
	private Long cargo;

	@Value("${movimiento.tipo.recargo}")
	private Long recargo;

	@Value("${movimiento.tipo.descuento}")
	private Long descuento;

	@Value("${movimiento.tipo.pagorecargo}")
	private Long pagoRecargo;

	@Value("${movimiento.tipo.pagocargo}")
	private Long pagoCargo;

	@Value("${movimiento.tipo.pagodescuento}")
	private Long pagoDescuento;

	@Value("${movimiento.tipo.cancelacionrecargo}")
	private Long cancelacionRecargo;

	@Value("${movimiento.tipo.cancelaciondescuento}")
	private Long cancelacionDescuento;

	@Value("${movimiento.tipo.cancelacioncargo}")
	private Long cancelacionCargo;

	@Value("${movimiento.tipo.cancelacionpagorecargo}")
	private Long cancelacionPagoRecargo;

	@Value("${movimiento.tipo.cancelacionpagocargo}")
	private Long cancelacionPagoCargo;

	@Value("${movimiento.tipo.cancelacionpagodescuento}")
	private Long cancelacionPagoDescuento;

	@Value("${movimiento.tipo.ajustecargo}")
	private Long ajusteCargo;

	@Value("${movimiento.tipo.saldoafavorgenerado}")
	private Long saldoAFavorGenerado;

	@Value("${movimiento.tipo.aplicaciondesaldoafavor}")
	private Long aplicacionDeSaldoAFavor;

	@Value("${movimiento.tipo.cancelaciondesaldoafavor}")
	private Long cancelacionDeSaldoAFavor;

	@Value("${movimiento.tipo.cancelaciondeaplicacion}")
	private Long cancelacionDeAplicacion;

	// /////////////////////////////TIPO CONSUMO///

	@Value("${tipoConsumo.simple}")
	private Long consumoSimple;

	@Value("${tipoConsumo.prorrateo}")
	private Long consumoProrrateo;

	@Value("${tipoConsumo.indiviso}")
	private Long consumoIndiviso;

	// /////////////////////////////TIPO DIRECTORIO///

	@Value("${tipoDirectorio.emergencias}")
	private Long directorioEmergencias;

	@Value("${tipoDirectorio.atencion}")
	private Long directorioAtencion;

	@Value("${tipoDirectorio.servicios}")
	private Long directorioServicios;

	@Value("${tipoDirectorio.condominio}")
	private Long directorioCondominio;

	// /////////////////////////////CONCEPTO TIPO MANTENIMIENTO
	@Value("${cargo.tipo.mantenimiento.concepto}")
	private String conceptoMantenimiento;

	/////////////////TIPOS DE COBRO DE LOS MANTENIMIENTOS/////////////////
	@Value("${tipocobromantenimiento.fijo}")
	private Long tipoCobroMantenimientoFijo;

	@Value("${tipocobromantenimiento.indiviso}")
	private Long tipoCobroMantenimientoIndiviso;

	// ////////////////MESES
	@Value("${meses.id}")
	private String idMeses;

	@Value("${meses.descripcion}")
	private String descripcionMeses;

	@Value("${metodos.saldofavor}")
	private Long metodoPagoSaldoFavor;
	
	@Value("${metodos.tarjeta}")
	private Long metodoTarjeta;

	@Value("${logo.reportes}")
	private String logo;

	@Value("${ingresonoidentificado.tipocargo.ingresonoidentificado}")
	private Long ingresonoidentificado;

	@Value("${ingresonoidentificado.tipocargo.ingresonoidentificadocancelado}")
	private Long ingresonoidentificadocancelado;

	@Value("${ingresonoidentificado.tipocargo.ingresonoidentificadobanco}")
	private Long ingresonoidentificadobanco;

	@Value("${ingresonoidentificado.tipocargo.ingresonoidentificadobancocancelado}")
	private Long ingresonoidentificadobancocancelado;

	//Tipos de Notificaciones
	@Value("${notificacion.configuracion.tipoEnvio.aplicacion}")
	private Long notConfAplicacion;

	@Value("${notificacion.configuracion.tipoEnvio.email}")
	private Long notConfEmail;

	@Value("${notificacion.configuracion.tipoEnvio.ninguno}")
	private Long notConfNinguno;

	//Pagos Feenifia
	@Value("${feenicia.produccion.host}")
	private String feeniciaProduccionHost;

	@Value("${feenicia.qa.host}")
	private String feeniciaQaHost;

	public CatalogoRol getRolSuperAministrador() {
		return CatalogoFactory.newInstance(CatalogoRol.class,
				idRolSuperAdministrador);
	}

	public CatalogoRol getRolAdministrador() {
		return CatalogoFactory.newInstance(CatalogoRol.class,
				idRolAdministrador);
	}

	public CatalogoRol getRolContacto() {
		return CatalogoFactory.newInstance(CatalogoRol.class, idRolContacto);
	}

	public String getHost() {
		return host;
	}

	public int getVigencia() {
		return vigencia;
	}

	public String getCuentaBanco() {
		return cuentaBanco.trim();
	}

	public Cuenta getCuentaEgresos() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[2].trim());
		return cuenta;
	}

	public Cuenta getCuentaIngresos() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[1].trim());
		return cuenta;
	}

	public Cuenta getCuentaBancos() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[0].trim());
		return cuenta;
	}

	public Cuenta getCuentaCajas() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[3].trim());
		return cuenta;
	}

	public Cuenta[] getCuentaCajasBancos() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta[] cuentas = new Cuenta[2];

		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[0].trim());
		cuentas[0] = cuenta;
		cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(arrayCuentas[3].trim());
		cuentas[1] = cuenta;
		return cuentas;
	}

	public Cuenta[] getCuentasPadre() {
		String[] arrayCuentas = cuentasPadre.split(",");
		Cuenta[] cuentas = new Cuenta[arrayCuentas.length];
		int i = 0;

		for (String nombre : arrayCuentas) {
			Cuenta cuenta = CuentaFactory.newInstance();
			cuenta.setNombre(nombre.trim());

			cuentas[i] = cuenta;
			i++;
		}

		return cuentas;
	}

	public Cuenta[] getCuentasHijasIngresos() {
		String[] arrayCuentasHijasIngresos = cuentasHijasIngresos.split(",");
		Cuenta[] cuentasHijas = new Cuenta[arrayCuentasHijasIngresos.length];
		int i = 0;
		for (String nombreCuenta : arrayCuentasHijasIngresos) {
			Cuenta cuenta = CuentaFactory.newInstance();
			cuenta.setNombre(nombreCuenta.trim());

			cuentasHijas[i] = cuenta;
			i++;
		}
		return cuentasHijas;
	}

	public Cuenta[] getCuentasHijasEgresos() {
		String[] arrayCuentasHijasEgresos = cuentasHijasEgresos.split(",");
		Cuenta[] cuentasHijas = new Cuenta[arrayCuentasHijasEgresos.length];
		int i = 0;
		for (String nombreCuenta : arrayCuentasHijasEgresos) {
			Cuenta cuenta = CuentaFactory.newInstance();
			cuenta.setNombre(nombreCuenta.trim());

			cuentasHijas[i] = cuenta;
			i++;
		}
		return cuentasHijas;
	}

	public Cuenta[] getCuentasHijasCajas() {
		String[] arrayCuentasHijasCajas = cuentasHijasCajas.split(",");
		Cuenta[] cuentasHijas = new Cuenta[arrayCuentasHijasCajas.length];
		int i = 0;
		for (String nombreCuenta : arrayCuentasHijasCajas) {
			Cuenta cuenta = CuentaFactory.newInstance();
			cuenta.setNombre(nombreCuenta.trim());

			cuentasHijas[i] = cuenta;
			i++;
		}
		return cuentasHijas;
	}

	public Cuenta[] getCuentasHijasBancos() {
		String[] arrayCuentasHijasBancos = cuentasHijasBancos.split(",");
		Cuenta[] cuentasHijas = new Cuenta[arrayCuentasHijasBancos.length];
		int i = 0;
		for (String nombreCuenta : arrayCuentasHijasBancos) {
			Cuenta cuenta = CuentaFactory.newInstance();
			cuenta.setNombre(nombreCuenta.trim());

			cuentasHijas[i] = cuenta;
			i++;
		}
		return cuentasHijas;
	}

	public Cuenta[] getCuentasPadreNoCuenta() {
		String[] arrayCuentasPadre = cuentasPadre.split(",");
		String[] arrayNoCuenta = cuentasPadreNoCuenta.split(",");
		Cuenta[] cuentas = new Cuenta[arrayCuentasPadre.length];

		if (arrayCuentasPadre.length == arrayNoCuenta.length) {
			for (int i = 0; i < arrayCuentasPadre.length; i++) {
				Cuenta cuenta = CuentaFactory.newInstance(
						arrayCuentasPadre[i].trim(), arrayNoCuenta[i].trim());
				cuentas[i] = cuenta;
			}
		}
		return cuentas;
	}

	public Cuenta[] getNoCuentaHijasIngresos() {
		String[] arrayCuentasHijasIngresos = cuentasHijasIngresos.split(",");
		String[] arrayNoCuentaHijas = cuentasHijasIngresosNoCuenta.split(",");
		Cuenta[] cuentas = new Cuenta[arrayCuentasHijasIngresos.length];

		if (arrayCuentasHijasIngresos.length == arrayNoCuentaHijas.length) {
			for (int i = 0; i <= arrayCuentasHijasIngresos.length; i++) {
				Cuenta cuenta = CuentaFactory.newInstance(
						arrayCuentasHijasIngresos[i], arrayNoCuentaHijas[i]);
				cuentas[i] = cuenta;
			}
		}
		return cuentas;
	}

	public final Long getPagoAprobado() {
		return pagoAprobado;
	}

	public Long getPagoPendiente() {
		return pagoPendiente;
	}

	public Long getPagoRechazado() {
		return pagoRechazado;
	}

	public Long getPagoCancelado() {
		return pagoCancelado;
	}

	public Long getReservacionPendiente() {
		return reservacionPendiente;
	}

	public Long getReservacionAprobada() {
		return reservacionAprobada;
	}

	public Long getReservacionRechazada() {
		return reservacionRechazada;
	}

	public Long getReservacionCancelada() {
		return reservacionCancelada;
	}

	public Long getIncidenciaTipoProyecto() {
		return incidenciaTipoProyecto;
	}

	public Long getIncidenciaTipoIncidencia() {
		return incidenciaTipoIncidencia;
	}

	public Long getIncidenciaEstatusSolicitada() {
		return incidenciaEstatusSolicitada;
	}

	public Long getIncidenciaEstatusPendiente() {
		return incidenciaEstatusPendiente;
	}

	public Long getIncidenciaEstatusRepetida() {
		return incidenciaEstatusRepetida;
	}

	public Long getIncidenciaEstatusCancelado() {
		return incidenciaEstatusCancelado;
	}

	public Long getIncidenciaEstatusConcluido() {
		return incidenciaEstatusConcluido;
	}

	public Long getCargo() {
		return cargo;
	}

	public Long getRecargo() {
		return recargo;
	}

	public Long getDescuento() {
		return descuento;
	}

	public Long getPagoRecargo() {
		return pagoRecargo;
	}

	public Long getPagoCargo() {
		return pagoCargo;
	}

	public Long getPagoDescuento() {
		return pagoDescuento;
	}

	public Long getCancelacionRecargo() {
		return cancelacionRecargo;
	}

	public Long getCancelacionDescuento() {
		return cancelacionDescuento;
	}

	public Long getCancelacionCargo() {
		return cancelacionCargo;
	}

	public Long getCancelacionPagoRecargo() {
		return cancelacionPagoRecargo;
	}

	public Long getCancelacionPagoCargo() {
		return cancelacionPagoCargo;
	}

	public Long getCancelacionPagoDescuento() {
		return cancelacionPagoDescuento;
	}

	public Long getAjusteCargo() {
		return ajusteCargo;
	}

	public Long getConsumoSimple() {
		return consumoSimple;
	}

	public Long getConsumoProrrateo() {
		return consumoProrrateo;
	}

	public Long getConsumo() {
		return consumoIndiviso;
	}

	public String getIdMeses() {
		return idMeses;
	}

	public String getDescripcionMeses() {
		return descripcionMeses;
	}

	public String getConceptoMantenimiento() {
		return conceptoMantenimiento;
	}

	// /////////////////////////////////////////////////////////////////////////

	public Cuenta getBancos() {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(cuentasPadre.split(",")[0].trim());
		return cuenta;
	}

	public Cuenta getCajas() {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(cuentasPadre.split(",")[3].trim());
		return cuenta;
	}

	public Cuenta getIngresos() {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(cuentasPadre.split(",")[1].trim());
		return cuenta;
	}

	public Cuenta getEgresos() {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(cuentasPadre.split(",")[2].trim());
		return cuenta;
	}

	public Cuenta getSaldos() {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setNombre(cuentasHijasIngresos.split(",")[2].trim());
		return cuenta;
	}

	public Long getBlogTipoCondominio() {
		return blogTipoCondominio;
	}

	public Long getBlogTipoGeneral() {
		return blogTipoGeneral;
	}

	public Long getBlogBienvenidos() {
		return blogBienvenidos;
	}

	public Long getBlogNoticias() {
		return blogNoticias;
	}

	public Long getBlogExpertos() {
		return blogExpertos;
	}

	public Long getBlogAvisos() {
		return blogAvisos;
	}

	public Long getBlogTemas() {
		return blogTemas;
	}

	public Long getBlogDocumentos() {
		return blogDocumentos;
	}

	public Long getBlogIncidencias() {
		return blogIncidencias;
	}

	public Long getBlogAnuncios() {
		return blogAnuncios;
	}

	public Long getBlogHowto() {
		return blogHowto;
	}

	public Long getBlogFaqs() {
		return blogFaqs;
	}

	public Long getBlogSoporte() {
		return blogSoporte;
	}

	// ////////////////////////////////////////////////////////////////////////

	public CatalogoMetodoPago getSaldoFavor() {
		return CatalogoFactory.newInstance(CatalogoMetodoPago.class,
				metodoPagoSaldoFavor);
	}
	
	public CatalogoMetodoPago getTarjeta() {
		return CatalogoFactory.newInstance(CatalogoMetodoPago.class,
				metodoTarjeta);
	}

	public Long getEmailPersonal() {
		return emailPersonal;
	}

	public Long getCargoMantenimiento() {
		return cargoMantenimiento;
	}

	public Long getCargoConsumo() {
		return cargoConsumo;
	}

	public Long getCargoInstalacion() {
		return cargoInstalacion;
	}

	public Long getCargoExtraordinarios() {
		return cargoExtraordinarios;
	}

	public Long getUnidadDia() {
		return unidadDia;
	}

	public Long getUnidadHora() {
		return unidadHora;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public String getLogo() {
		return logo;
	}

	public Long getDirectorioEmergencias() {
		return directorioEmergencias;
	}

	public Long getDirectorioAtencion() {
		return directorioAtencion;
	}

	public Long getDirectorioServicios() {
		return directorioServicios;
	}

	public Long getDirectorioCondominio() {
		return directorioCondominio;
	}

	public Long getIngresonoidentificado() {
		return ingresonoidentificado;
	}

	public Long getIngresonoidentificadocancelado() {
		return ingresonoidentificadocancelado;
	}

	public Long getIngresonoidentificadobanco() {
		return ingresonoidentificadobanco;
	}

	public Long getIngresonoidentificadobancocancelado() {
		return ingresonoidentificadobancocancelado;
	}

	public Long getTipoInteresSimple() {
		return tipoInteresSimple;
	}

	public Long getTipoInteresCompuesto() {
		return tipoInteresCompuesto;
	}

	public Long getTipoInteresUnico() {
		return tipoInteresUnico;
	}

	public Long getSaldoAFavorGenerado() {
		return saldoAFavorGenerado;
	}

	public Long getAplicacionDeSaldoAFavor() {
		return aplicacionDeSaldoAFavor;
	}

	public Long getCancelacionDeSaldoAFavor() {
		return cancelacionDeSaldoAFavor;
	}

	public Long getCancelacionDeAplicacion() {
		return cancelacionDeAplicacion;
	}

	public String getFeeniciaProduccionHost() {
		return feeniciaProduccionHost;
	}

	public String getFeeniciaQaHost() {
		return feeniciaQaHost;
	}

	public Long getNotConfEmail() {
		return notConfEmail;
	}

	public void setNotConfEmail(Long notConfEmail) {
		this.notConfEmail = notConfEmail;
	}

	public Long getNotConfAplicacion() {
		return notConfAplicacion;
	}

	public void setNotConfAplicacion(Long notConfAplicacion) {
		this.notConfAplicacion = notConfAplicacion;
	}

	public Long getNotConfNinguno() {
		return notConfNinguno;
	}
	
	public Long getTipoCobroMantenimientoFijo() { return tipoCobroMantenimientoFijo; }

	public Long getTipoCobroMantenimientoIndiviso() {
		return tipoCobroMantenimientoIndiviso;
	}

	public String getSendingblueHost() {
		return sendingblueHost;
	}

	public String getSendingblueKey() {
		return sendingblueKey;
	}

	public String getEmailDirOrigenEmail() {
		return emailDirOrigenEmail;
	}

	public String getEmailDirOrigenNombre() {
		return emailDirOrigenNombre;
	}
}


