<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<meta charset="utf-8">
<meta name="viewport"
	  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/pace/pace.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/libs/jquery-2.0.2.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/libs/jquery-ui-1.10.3.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/libs/jquery.impl.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/libs/knockout-3.3.0.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/libs/knockout.extenders.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/bootstrap/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/jquery-validate/jquery.validate.es.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/masked-input/jquery.maskedinput.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/smartwidgets/jarvis.widget.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/fastclick/fastclick.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/notification/SmartNotification.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/select2/select2.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/bootbox/bootbox.min.js"></script>
<script
	src="${pageContext.request.contextPath}/recursos/js/plugin/blockui/jquery.blockui.min.js"></script>
<script src="${pageContext.request.contextPath}/recursos/js/scroll.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/numeral.js?v=${project-version}"></script>

<script src="${pageContext.request.contextPath}/recursos/js/app/util.js?v=${project-version}"></script>
<script src="${pageContext.request.contextPath}/recursos/js/app/object-utils.js?v=${project-version}"></script>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
	var AppConfig = {
		catalogos : {
			usuario : {				
				roles : {
					contacto : <spring:eval expression="@propertyConfigurer.getProperty('roles.contacto')"/>
				}
			},
			persona : {
				telefono: {
					oficina : <spring:eval expression="@propertyConfigurer.getProperty('persona.telefono.oficina')"/>
				}
			},
			cargo : {
				tipos : {
					mantenimiento : <spring:eval expression="@propertyConfigurer.getProperty('cargo.tipos.mantenimiento')"/>,
					consumo : <spring:eval expression="@propertyConfigurer.getProperty('cargo.tipos.consumo')"/>,
					instalacion : <spring:eval expression="@propertyConfigurer.getProperty('cargo.tipos.instalacion')"/>,
					extraordinarios : <spring:eval expression="@propertyConfigurer.getProperty('cargo.tipos.extraordinarios')"/>,
				},
				conceptos : {
					mantenimiento : "<spring:eval expression="@propertyConfigurer.getProperty('cargo.tipo.mantenimiento.concepto')"/>"
				},
				recargos : {
					tiposInteres : {
						unico : "<spring:eval expression="@propertyConfigurer.getProperty('tipoInteres.unico')"/>",
						simple : "<spring:eval expression="@propertyConfigurer.getProperty('tipoInteres.simple')"/>",
						compuesto : "<spring:eval expression="@propertyConfigurer.getProperty('tipoInteres.compuesto')"/>"
					}
				},
				tiposCobroMantenimiento: {
					fijo: "<spring:eval expression="@propertyConfigurer.getProperty('tipocobromantenimiento.fijo')"/>",
					indiviso: "<spring:eval expression="@propertyConfigurer.getProperty('tipocobromantenimiento.indiviso')"/>",

				}
			},
			movimientos  : {
			    tipos : {
			        cargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cargo')"/>,
			        recargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.recargo')"/>,
			        descuento : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.descuento')"/>,
			        pagorecargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.pagorecargo')"/>,
			        pagocargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.pagocargo')"/>,
			        pagodescuento : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.pagodescuento')"/>,
			        cancelacionrecargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelacionrecargo')"/>,
			        cancelaciondescuento : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelaciondescuento')"/>,
			        cancelacioncargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelacioncargo')"/>,
			        cancelacionpagorecargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelacionpagorecargo')"/>,
			        cancelacionpagocargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelacionpagocargo')"/>,
			        cancelacionpagodescuento : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelacionpagodescuento')"/>,
			        ajustecargo : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.ajustecargo')"/>,
					cfdi : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cfdi.detalle')"/>,
					pago : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cfdi.pago')"/>,
					retenido : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cfdi.impuestos.retenido')"/>,
					trasladado : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cfdi.impuestos.trasladado')"/>,
					transferencia : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.transferencia')"/>,
					saldoAFavorGenerado : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.saldoafavorgenerado')"/>,
					aplicacionDeSaldoAFavor : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.aplicaciondesaldoafavor')"/>,
					cancelacionDeSaldoAFavor : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelaciondesaldoafavor')"/>,
					cancelacionDeAplicacion : <spring:eval expression="@propertyConfigurer.getProperty('movimiento.tipo.cancelaciondeaplicacion')"/>
			    }  
			},
			metodos : {
			    deposito : <spring:eval expression="@propertyConfigurer.getProperty('metodos.deposito')"/>,
			    cheque : <spring:eval expression="@propertyConfigurer.getProperty('metodos.cheque')"/>,
			    transferencia : <spring:eval expression="@propertyConfigurer.getProperty('metodos.transferencia')"/>,
			    efectivo : <spring:eval expression="@propertyConfigurer.getProperty('metodos.efectivo')"/>,
			    saldofavor : <spring:eval expression="@propertyConfigurer.getProperty('metodos.saldofavor')"/>,
			    tarjeta : <spring:eval expression="@propertyConfigurer.getProperty('metodos.tarjeta')"/>
			},
			estatuspago: {
				pendiente : <spring:eval expression="@propertyConfigurer.getProperty('pago.estatus.pendiente')"/>,
				aprobado : <spring:eval expression="@propertyConfigurer.getProperty('pago.estatus.aprobado')"/>,
				rechazado : <spring:eval expression="@propertyConfigurer.getProperty('pago.estatus.rechazado')"/>,
				cancelado : <spring:eval expression="@propertyConfigurer.getProperty('pago.estatus.cancelado')"/>
			},
			estatusreservacion: {
				pendiente : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.pendiente')"/>,
				reservada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.reservada')"/>,
				rechazada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.rechazada')"/>,
				cancelada : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.estatus.cancelada')"/>
			},
			estatusotrosingresos: {
				registrado : <spring:eval expression="@propertyConfigurer.getProperty('ingresonoidentificado.estatus.registrado')"/>,
				aplicado : <spring:eval expression="@propertyConfigurer.getProperty('ingresonoidentificado.estatus.aplicado')"/>,
				cancelado : <spring:eval expression="@propertyConfigurer.getProperty('ingresonoidentificado.estatus.cancelado')"/>
			},
			unidadesreservacion: {
				dia : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.unidades.dia')"/>,
				hora : <spring:eval expression="@propertyConfigurer.getProperty('reservacion.unidades.hora')"/>
			},
			cuentas: {
				padre:"<spring:eval expression="@propertyConfigurer.getProperty('cuentas.padre')"/>".split(","),
				hijasIngresos:"<spring:eval expression="@propertyConfigurer.getProperty('cuentas.hijas.ingresos')"/>".split(","),
				banco :"<spring:eval expression="@propertyConfigurer.getProperty('cuentas.padre.banco')"/>"
			},
			consumos: {
				tipos: {
					simple : <spring:eval expression="@propertyConfigurer.getProperty('tipoConsumo.simple')"/>,
					prorrateo : <spring:eval expression="@propertyConfigurer.getProperty('tipoConsumo.prorrateo')"/>,
					indiviso : <spring:eval expression="@propertyConfigurer.getProperty('tipoConsumo.indiviso')"/>
				}	
			},
			directorio: {
				tipos: {
					emergencias : <spring:eval expression="@propertyConfigurer.getProperty('tipoDirectorio.emergencias')"/>,
					atencion : <spring:eval expression="@propertyConfigurer.getProperty('tipoDirectorio.atencion')"/>,
					servicios : <spring:eval expression="@propertyConfigurer.getProperty('tipoDirectorio.servicios')"/>,
					condominio : <spring:eval expression="@propertyConfigurer.getProperty('tipoDirectorio.condominio')"/>
				}
			},
			meses: {
			    descripcion : "<spring:eval expression="@propertyConfigurer.getProperty('meses.descripcion')"/>"
			},
			blogs: {
				bienvenidos : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.bienvenidos')"/>,
				noticias : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.noticias')"/>,
				expertos : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.expertos')"/>,
				avisos : <spring:eval expression="@propertyConfigurer.getProperty('blog.condominio.avisos')"/>,
				temas : <spring:eval expression="@propertyConfigurer.getProperty('blog.condominio.temas')"/>,
				documentos : <spring:eval expression="@propertyConfigurer.getProperty('blog.condominio.documentos')"/>,
				incidencias : <spring:eval expression="@propertyConfigurer.getProperty('blog.condominio.incidencias')"/>,
				anuncios : <spring:eval expression="@propertyConfigurer.getProperty('blog.condominio.anuncios')"/>,
				howto : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.howto')"/>,
				faqs : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.faqs')"/>,
				soporte : <spring:eval expression="@propertyConfigurer.getProperty('blog.general.soporte')"/>
			},
			tiponotificaciones: {
				app : "<spring:eval expression="@propertyConfigurer.getProperty('notificacion.configuracion.tipoEnvio.aplicacion')"/>",
				email : "<spring:eval expression="@propertyConfigurer.getProperty('notificacion.configuracion.tipoEnvio.email')"/>",
				ninguno : "<spring:eval expression="@propertyConfigurer.getProperty('notificacion.configuracion.tipoEnvio.ninguno')"/>"
			}
		}
	}
	
	$(document).ready(function() {
	/*
		try {
			if (localStorage.getItem) {
				storagee = localStorage;
				minified = localStorage.getItem('minified');
				if (!(minified === "true") && !$('body').hasClass("minified")) {
					$("body").addClass("minified");
				} else if ($("body").hasClass("minified")){
					$("body").removeClass("minified")
				}
			}
		} catch(e) {
			storage = {};
		}
	*/
		
		$('.modal').on('hidden.bs.modal', function( event ) {
           $(this).removeClass( 'fv-modal-stack' );
           $('body').data( 'fv_open_modals', $('body').data( 'fv_open_modals' ) - 1 );
        });


	    $( '.modal' ).on( 'shown.bs.modal', function ( event ) {
               if ( typeof( $('body').data( 'fv_open_modals' ) ) == 'undefined' ) {
                   $('body').data( 'fv_open_modals', 0 );
               }
               if ( $(this).hasClass( 'fv-modal-stack' ) ) {
                    return;
               }
               
            $(this).addClass( 'fv-modal-stack' );
            $('body').data( 'fv_open_modals', $('body').data( 'fv_open_modals' ) + 1 );
            $(this).css('z-index', 1040 + (10 * $('body').data( 'fv_open_modals' )));
            $( '.modal-backdrop' ).not( '.fv-modal-stack' ).css( 'z-index', 1039 + (10 * $('body').data( 'fv_open_modals' )));
            $( '.modal-backdrop' ).not( 'fv-modal-stack' ).addClass( 'fv-modal-stack' );
	    });
	});
</script>