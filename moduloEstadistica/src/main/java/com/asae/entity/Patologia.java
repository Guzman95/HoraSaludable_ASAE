package com.asae.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the patologias database table.
 * 
 */
@Entity
@NamedQueries({ 
@NamedQuery(name= "Patologia.findByGender", query= "SELECT p.nombre,COUNT(p.nombre) FROM Patologia p JOIN p.usuario pe WHERE pe.usugenero = :gender GROUP BY p.nombre"),
@NamedQuery(name = "Patologia.findByPosition",query = "SELECT p.nombre, COUNT(p.nombre) as Cantidad FROM Patologia p JOIN p.usuario pe WHERE pe.cargo.carid = :position GROUP BY p.nombre"),
@NamedQuery(name="Patologia.findByCount", query="SELECT p.nombre, COUNT(p.nombre) FROM Patologia p GROUP BY p.nombre"),
@NamedQuery(name="Patologia.findOneGarder", query="SELECT p.nombre, COUNT(p.nombre) FROM Patologia p JOIN p.usuario pe WHERE pe.usuario.usugenero = :gender AND pe.cargo.carid = :positios GROUP BY p.nombre"),
@NamedQuery(name="Patologia.findAll", query="SELECT p FROM Patologia p")
})
public class Patologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pat_id")
	private int patId;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuid")
	private Usuario usuario;

	public Patologia() {
	}

	public int getPatId() {
		return this.patId;
	}

	public void setPatId(int patId) {
		this.patId = patId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}