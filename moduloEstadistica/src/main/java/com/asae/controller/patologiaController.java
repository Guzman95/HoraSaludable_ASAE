package com.asae.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import com.asae.dto.DTOPatologia;

import com.asae.ejbinterface.IEjbPatologia;

@ManagedBean(name = "patologiaController")
@RequestScoped
public class patologiaController {

	@ManagedProperty(value = "#{objpatologia}")
	private DTOPatologia objpatologia;
	private ArrayList<DTOPatologia> listaPatologia;
	private static final String EJBGestionPatologia_SESSION_KEY = "EJBSesionPatologia";
	private IEjbPatologia iEjbPatologia;

	private PieChartModel graficoPatologia;

	@PostConstruct
	public void init() {
		System.out.println("creando ejb Patologia");
		consultarReferenciaEJB();
		listaPatologia = new ArrayList<DTOPatologia>();
		graficoPatologia = new PieChartModel();
	}

	public DTOPatologia getObjpatologia() {
		return objpatologia;
	}

	public void setObjpatologia(DTOPatologia objpatologia) {
		this.objpatologia = objpatologia;
	}

	public ArrayList<DTOPatologia> getListaPatologia() {
		return listaPatologia;
	}

	public void setListaPatologia(ArrayList<DTOPatologia> listaPatologia) {
		this.listaPatologia = listaPatologia;
	}

	public String ConsultarPatologia() {

		if (this.objpatologia.getGenero() != null) {

			if (this.objpatologia.getCargo() == 1 || this.objpatologia.getCargo() == 2
					|| this.objpatologia.getCargo() == 3) {
				this.listaPatologia = findByPosition();
			} else if (this.objpatologia.getCargo() == 4) {
				this.listaPatologia = findByPosition();
				ArrayList<DTOPatologia> listaDocente = findByPosition();
			} else if (this.objpatologia.getCargo() == 5) {
				this.listaPatologia = findByPosition();
			} else if (this.objpatologia.getCargo() == 6) {
				this.listaPatologia = findByPosition();
			} else if (this.objpatologia.getCargo() == 7) {
				this.listaPatologia = findByPosition();
			}

		} else {
			this.listaPatologia = findByGender();
		}
		return "graficaPatologias";
	}

	public ArrayList<DTOPatologia> findByGender() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findByGender(this.objpatologia.getGenero());
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public ArrayList<DTOPatologia> findByPosition() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findByPosition(this.objpatologia.getCargo() + "");
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public ArrayList<DTOPatologia> findCount() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findCount();
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public ArrayList<DTOPatologia> findOneGarder() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findOneGarder(this.objpatologia.getGenero(),
				this.objpatologia.getCargo() + "");
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public void consultarReferenciaEJB() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		this.iEjbPatologia = (IEjbPatologia) requestMap.get("EJBGestionPatologia_SESSION_KEY");

		if (this.iEjbPatologia == null) {

			try {
				InitialContext ic = new InitialContext();
				this.iEjbPatologia = (IEjbPatologia) ic.lookup(
						"java:global/moduloEstadistica-1.0-SNAPSHOT/EjbPatologia!com.asae.ejbinterface.IEjbPatologia");

				requestMap.putIfAbsent(EJBGestionPatologia_SESSION_KEY, this.iEjbPatologia);

				System.out.println("ejb para la gestión de usuarios creado");

			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public PieChartModel initPieModel() {
		PieChartModel patologias = new PieChartModel();

		ChartData data = new ChartData();
		PieChartDataSet dataset = new PieChartDataSet();

		List<Number> values = new ArrayList<>();

		for (int i = 0; i < listaPatologia.size(); i++) {
			values.add(listaPatologia.get(i).getContador());
		}
		dataset.setData(values);
		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(255, 99, 132)");
		bgColors.add("rgb(54, 162, 235)");
		bgColors.add("rgb(255, 205, 86)");
		bgColors.add("rgb(54, 162, 235)");
		bgColors.add("rgb(255, 205, 86)");
		bgColors.add("rgb(54, 162, 235)");
		bgColors.add("rgb(255, 128, 0)");
		bgColors.add("rgb(255, 51, 153)");
		dataset.setBackgroundColor(bgColors);

		data.addChartDataSet(dataset);

		List<String> labels = new ArrayList<>();
		for (int i = 0; i < listaPatologia.size(); i++) {
			labels.add(listaPatologia.get(i).getNombre());
		}

		data.setLabels(labels);

		patologias.setData(data);

		return patologias;

	}

	public void createPieModel() {
		graficoPatologia = initPieModel();
	}

	public PieChartModel getGraficoPatologia() {
		return graficoPatologia;
	}

	public void setGraficoPatologia(PieChartModel graficoPatologia) {
		this.graficoPatologia = graficoPatologia;
	}

}
