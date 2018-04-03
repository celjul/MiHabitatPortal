package com.bstmexico.mihabitat.web.service;

import com.bstmexico.mihabitat.cargos.model.CargoAgrupador;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.notificaciones.model.Notification;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.pagos.model.Pago;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collection;

public interface NotificationHelperService {

	SimpMessagingTemplate getTemplate();
	void setTemplate(SimpMessagingTemplate template);
	void enviarNotificacionNuevoTema(Tema tema);
	//void enviarNotificacionNuevoPost(Post post);

	/*void enviarNotificacionIncidenciaPendiente(Incidencia incidencia);
	void enviarNotificacionCambioIncidencia(Incidencia incidencia);*/

	void enviarNotificacionPagoPendiente(Pago pago);
	void enviarNotificacionPagoValidado(Pago pago);
	void enviarNotificacionNuevosCargos(CargoAgrupador cargo);
	void enviarNotificacionNuevoCargo(CargoDepartamento cargo);
	void enviarNotificacionReservacionValidada(Reservacion reservacion);
	void enviarNotificacionReservacionPendiente(Reservacion reservacion);
	void enviarNotificacionPorDepartamento(Departamento departamento, Integer tipo,
												  Notification notificacion);
	void enviarNotificacionPorPerfil(Condominio condominio, CatalogoRol rol, Notification notificacion);
	void enviarNotificacionPorUsuario(Usuario usuario,
									  Notification notificacion);
	void enviarNotificacionPorUsuarios(Collection<Usuario> usuarios, Notification notificacion);

	void enviarNotificacionParaTodos(Notification notificacion);

	Collection<Notification> listaNotificacionesBlogs(Condominio condominio, Usuario usuario);

	Collection<Notification> listPendientesAdmin(Condominio condominio);

	Collection<Notification> listarNotificacionesContacto(Condominio condominio, Usuario usuario);

	void enviarNotificacionNuevoMovimiento(MovimientoCargo movimientoCargo);
	
	void enviarNotificacionNuevoArrendatario(Arrendatario arrendatario);
}
