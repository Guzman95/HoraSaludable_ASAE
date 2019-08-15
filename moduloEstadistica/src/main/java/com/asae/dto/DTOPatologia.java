package com.asae.dto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "objpatologia", eager = true) 
@RequestScoped
public class DTOPatologia {
	//Atributos
	private int patid;
	private String descripcion;
	private String nombre;
	private int usuid;
	private long contador;
	private String genero;
	private String cargo;
	
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getPatid() {
		return patid;
	}

	public void setPatid(int patid) {
		this.patid = patid;
	}

	public int getUsuid() {
		return usuid;
	}

	public void setUsuid(int usuid) {
		this.usuid = usuid;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DTOPatologia(){
		
	}
	
	public int getpatid() {
		return this.patid;
	}
	public int getusuid() {
		return this.usuid;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public long getContador() {
		return this.contador;
	}
	public void setContador(long varCont) {
		this.contador = varCont;
	}
}
