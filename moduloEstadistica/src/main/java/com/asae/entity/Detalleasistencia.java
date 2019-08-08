package com.asae.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detalleasistencia database table.
 * 
 */
@Entity
@NamedQueries({

	@NamedQuery(name = "detalleasistencia.findListById", query = "SELECT asi.asifecha, dt.detasistio FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE asi.asiid = dt.id.asiid AND dt.usuario.usuid = :identificacion"),
	@NamedQuery(name = "detalleasistencia.findByHalfYearI", query = "SELECT SUM(dt.detasistio), SUBSTRING(asi.asifecha,6,2) AS MES FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE SUBSTRING(asi.asifecha,6,2) <= '06' AND SUBSTRING(asi.asifecha,1,4) = :anio AND dt.usuario.usuidentificacion = :identificacion GROUP BY MES"),
	@NamedQuery(name = "detalleasistencia.findByHalfYearII", query = "SELECT SUM(dt.detasistio), SUBSTRING(asi.asifecha,6,2) AS MES FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE SUBSTRING(asi.asifecha,6,2) > '06' AND SUBSTRING(asi.asifecha,1,4) = :anio AND dt.usuario.usuidentificacion = :identificacion GROUP BY MES"),
	@NamedQuery(name = "detalleasistencia.findListByMonth", query = "SELECT asi.asifecha as fecha, dt.detasistio as numAsistencia FROM Detalleasistencia dt JOIN dt.asistencia asi JOIN dt.usuario us WHERE SUBSTRING(asi.asifecha,1,4)=:anio AND  SUBSTRING(asi.asifecha,6,2)=:mes AND us.usuidentificacion = :identificacion AND dt.detasistio=1 order by asi.asifecha"),
	@NamedQuery(name = "detalleasistencia.findListBetWeenMonths", query = "SELECT SUM(dt.detasistio), SUBSTRING(asi.asifecha,6,2) AS MES FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE dt.usuario.usuidentificacion = :identificacion AND asi.asifecha BETWEEN :startDate AND :endDate  GROUP BY MES"),
	@NamedQuery(name = "detalleasistencia.findListBetWeenDays", query = "SELECT asi.asifecha as fecha, dt.detasistio as numAsistencia FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE dt.usuario.usuidentificacion = :identificacion AND asi.asifecha BETWEEN :startDate AND :endDate AND dt.detasistio=1 ORDER BY fecha"),
	@NamedQuery(name = "detalleasistencia.findListBetWeenYears", query = "SELECT SUM(dt.detasistio) as asistio, SUBSTRING(asi.asifecha,1,4) as ANIO FROM Detalleasistencia dt JOIN dt.asistencia asi WHERE dt.usuario.usuidentificacion = :identificacion AND asi.asifecha BETWEEN :startDate AND :endDate GROUP BY ANIO"),
	@NamedQuery(name = "detalleasistencia.findByReason", query = "SELECT count(dt.detnoasistencia) fROM Detalleasistencia dt GROUP BY dt.detnoasistencia")

	
	
	
})
public class Detalleasistencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetalleasistenciaPK id;

	private int detasistio;

	private String detnoasistencia;

	//bi-directional many-to-one association to Asistencia
	@ManyToOne
	@JoinColumn(name="ASIID")
	private Asistencia asistencia;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USUID")
	private Usuario usuario;

	public Detalleasistencia() {
	}

	public DetalleasistenciaPK getId() {
		return this.id;
	}

	public void setId(DetalleasistenciaPK id) {
		this.id = id;
	}

	public int getDetasistio() {
		return this.detasistio;
	}

	public void setDetasistio(int detasistio) {
		this.detasistio = detasistio;
	}

	public String getDetnoasistencia() {
		return this.detnoasistencia;
	}

	public void setDetnoasistencia(String detnoasistencia) {
		this.detnoasistencia = detnoasistencia;
	}

	public Asistencia getAsistencia() {
		return this.asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}