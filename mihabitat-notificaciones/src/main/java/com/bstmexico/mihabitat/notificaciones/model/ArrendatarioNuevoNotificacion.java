package com.bstmexico.mihabitat.notificaciones.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("arrendatario-nuevo")
public class ArrendatarioNuevoNotificacion extends Notification {
	
	 @JsonIgnore
	    @NotNull
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "NIdRol")
	    private CatalogoRol rol; 
	
	 @JsonIgnore
	    @NotNull
	    @OneToOne(fetch = FetchType.EAGER, targetEntity = Reservacion.class)
	    @JoinColumn(name = "NIdReservacion")
	    private Arrendatario arrendatario;

	    public ArrendatarioNuevoNotificacion() {
	        this.setEmailTemplate("nuevo-arrendador.html");
	    }

	    public Arrendatario getArrendatario() {
	        return arrendatario;
	    }

	    public void setArrendatario(Arrendatario arrendatario) {
	        this.arrendatario = arrendatario;
	    }

	    public CatalogoRol getRol() {
	        return rol;
	    }

	    public void setRol(CatalogoRol rol) {
	        this.rol = rol;
	    }

	    @Override
	    public DateTime getFecha() {
	        if(super.getFecha() == null){
	            super.setFecha(new DateTime(this.getArrendatario().getFechaRegistro()));
	        }
	        return super.getFecha();
	    }

	    @Override
	    public String getTitulo() {
	        if(this.getArrendatario() != null && this.getArrendatario().getIdArrendador()!= null) {
	                    return "Nueva Solicitud de Arrendatario";
	        }
	        return super.getTitulo();
	    }

	    @Override
	    public String getEmailTemplate() {
	        return super.getEmailTemplate();
	    }

	    @Override
	    public String getSubtipo() {
	        return "arrendatario-nuevo";
	    }

	    @Override
	    public String getLink() {
	        //TODO: enviar al calendario en el lugar en donde está el evento
	      /*  if(this.getArrendatario() != null && this.getArrendatario().getIdArrendador()!= null) {
	            if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
	                return ("/administrador/instalaciones/reservar/" + this.getArrendatario().getDepartamento().getId());
	            } else {
	                return ("/contacto/mis-reservaciones/reservar/" +
	                        this.getArrendatario().getDepartamento().getId());
	            }
	        }*/
	        return super.getLink();

	    }

	    @Override
	    public String getFormattedHtmlAlert(){
	        StringBuffer notificacionStr = new StringBuffer();
	        if(this.getArrendatario() != null && this.getArrendatario().getIdArrendador()!= null) {
	        notificacionStr.append("Su Arrendatario registrada para el departamento <strong>" 
	        + arrendatario.getDepartamento().getNombre() +
	        		"</strong> se encuentra en espera de ser aprobada por parte de la administración. ");
	                
	             
	        }
	        return notificacionStr.toString();
	    }

}
