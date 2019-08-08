package com.asae.dto;


import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "objdetalleasistencia", eager = true)
@SessionScoped
public class DTOAsistencia {
	private int numAsistencia;
	private String fecAsisencia;
	private String datanio;
	private String periodoAcademico;
	private String cedula;
	private String mesConsulta;
	private String fecInicio;
	private String fecFin;
	private String radio;
	

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}

	public String getFecFin() {
		return fecFin;
	}

	public void setFecFin(String fecFin) {
		this.fecFin = fecFin;
	}

	public String getMesConsulta() {
		return mesConsulta;
	}

	public void setMesConsulta(String mesConsulta) {
		this.mesConsulta = mesConsulta;
	}

	public DTOAsistencia() {
		super();
	}

	public int getNumAsistencia() {
		return numAsistencia;
	}

	public String getdatanio() {
		return this.datanio;
	}

	public String getPeriodoAcademico() {
		return this.periodoAcademico;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setNumAsistencia(int numAsistencia) {
		this.numAsistencia = numAsistencia;
	}

	public String getFecAsisencia() {
		return fecAsisencia;
	}

	public void setFecAsisencia(String fecAsisencia) {
		this.fecAsisencia = fecAsisencia;
	}

	public String getDatanio() {
		return datanio;
	}

	public void setDatanio(String datanio) {
		this.datanio = datanio;
	}

	public void setPeriodoAcademico(String periodoAcademico) {
		this.periodoAcademico = periodoAcademico;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

}
