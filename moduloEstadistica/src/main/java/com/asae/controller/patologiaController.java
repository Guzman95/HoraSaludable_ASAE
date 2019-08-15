package com.asae.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
	private ArrayList<DTOPatologia> listaPatologiaAux;
	private static final String EJBGestionPatologia_SESSION_KEY = "EJBSesionPatologia";
	private IEjbPatologia iEjbPatologia;

	private PieChartModel graficoPatologia;

	@PostConstruct
	public void init() {
		System.out.println("creando ejb Patologia");
		consultarReferenciaEJB();
		listaPatologia = new ArrayList<DTOPatologia>();
		listaPatologiaAux = new ArrayList<DTOPatologia>();
		graficoPatologia = new PieChartModel();
	}

	
	
	public ArrayList<DTOPatologia> getListaPatologiaAux() {
		return listaPatologiaAux;
	}



	public void setListaPatologiaAux(ArrayList<DTOPatologia> listaPatologiaAux) {
		this.listaPatologiaAux = listaPatologiaAux;
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

		if (this.objpatologia.getGenero().equals("A")) {

			if (this.objpatologia.getCargo().equals("1")  || this.objpatologia.getCargo().equals("2") || this.objpatologia.getCargo().equals("3")) {
				this.listaPatologia = findByPosition(this.objpatologia.getCargo());
			} else if (this.objpatologia.getCargo().equals("1-2")  || this.objpatologia.getCargo().equals("1-3") || this.objpatologia.getCargo().equals("3-2")){
				String[] cargoId=this.objpatologia.getCargo().split("-");
				this.listaPatologia = findByPosition(cargoId[0]);
				this.listaPatologiaAux = findByPosition(cargoId[1]);
			}else {
				this.listaPatologia = findCount();
			}

		} else {
			if (this.objpatologia.getCargo().equals("1")  || this.objpatologia.getCargo().equals("2") || this.objpatologia.getCargo().equals("3")) {
				this.listaPatologia = findOneGarder(this.objpatologia.getCargo());
			} else if (this.objpatologia.getCargo().equals("1-2")  || this.objpatologia.getCargo().equals("1-3") || this.objpatologia.getCargo().equals("3-2")){
				String[] cargoId=this.objpatologia.getCargo().split("-");
				this.listaPatologia = findOneGarder(cargoId[0]);
				this.listaPatologiaAux = findOneGarder(cargoId[1]);
			}else {
				this.listaPatologia = findByGender();
			}
		}
		initPieModel();
		return "graficaPatologias";
	}

	public ArrayList<DTOPatologia> findByGender() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findByGender(this.objpatologia.getGenero());
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public ArrayList<DTOPatologia> findByPosition(String Cargo) {
		ArrayList<DTOPatologia> listarRetornada=new ArrayList<DTOPatologia>();
		System.out.println("Consultando Patologias por Cargo y Genero");
		listarRetornada = (ArrayList<DTOPatologia>) iEjbPatologia.findByPosition(Cargo);
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return listarRetornada;
	}

	public ArrayList<DTOPatologia> findCount() {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findCount();
		System.out.println("El tamaño de la lista de asistencias es: " + listaPatologia.size());
		return this.listaPatologia;
	}

	public ArrayList<DTOPatologia> findOneGarder(String Cargo) {
		System.out.println("Consultando Patologias por Cargo y Genero");
		this.listaPatologia = (ArrayList<DTOPatologia>) iEjbPatologia.findOneGarder(this.objpatologia.getGenero(),
				Cargo);
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
