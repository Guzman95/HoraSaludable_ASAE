package com.asae.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.asae.dto.DTOAsistencia;
import com.asae.ejbinterface.IEjbDetalleasistencia;

@ManagedBean(name = "detalleasistenciaController")
@SessionScoped
public class detalleasistenciaController {

	@ManagedProperty(value = "#{objdetalleasistencia}")
	private DTOAsistencia objdetalleasistencia;
	ArrayList<DTOAsistencia> listaDetalle;
	private static final String EJBGestionDetalleAsistencia_SESSION_KEY = "EJBDetalleAsistencia";
	private IEjbDetalleasistencia iEjbDetalleAsistencia;

	private BarChartModel asistenciaPersonal;

	@PostConstruct
	public void init() {
		System.out.println("creando ejb");
		consultarReferenciaEJB();
		System.out.println("guardo en objeto el anio?" + this.objdetalleasistencia);
		listaDetalle = new ArrayList<DTOAsistencia>();
		asistenciaPersonal = new BarChartModel();
	}

	public DTOAsistencia getObjdetalleasistencia() {
		return objdetalleasistencia;
	}

	public void setObjdetalleasistencia(DTOAsistencia objdetalleasistencia) {
		this.objdetalleasistencia = objdetalleasistencia;
	}

	public List<DTOAsistencia> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(ArrayList<DTOAsistencia> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public IEjbDetalleasistencia getiEjbDetalleAsistencia() {
		return iEjbDetalleAsistencia;
	}

	public void setiEjbDetalleAsistencia(IEjbDetalleasistencia iEjbDetalleAsistencia) {
		this.iEjbDetalleAsistencia = iEjbDetalleAsistencia;
	}

	public static String getEjbgestiondetalleasistenciaSessionKey() {
		return EJBGestionDetalleAsistencia_SESSION_KEY;
	}

	public String findListByHalfYearEnviarVista() {
		TimeZone timezone = TimeZone.getDefault();
		Calendar calendarIni = new GregorianCalendar(timezone);
		Calendar calendarFin = new GregorianCalendar(timezone);
		if(this.objdetalleasistencia.getRadio().equals("Rango")) {
			
			String[] splitFechaInicio = this.objdetalleasistencia.getFecInicio().split("/");
			String[] splitFechaFin = this.objdetalleasistencia.getFecFin().split("/");
			
			calendarIni.set(Integer.parseInt(splitFechaInicio[0]), Integer.parseInt(splitFechaInicio[1]) - 1,
					Integer.parseInt(splitFechaInicio[2]));

			calendarFin.set(Integer.parseInt(splitFechaFin[0]), Integer.parseInt(splitFechaFin[1]) - 1,
					Integer.parseInt(splitFechaFin[2]));
			
			int anio = 0;
			int mes = 0;
			anio = calendarFin.get(Calendar.YEAR) - calendarIni.get(Calendar.YEAR);
			mes = calendarFin.get(Calendar.MONTH) - calendarIni.get(Calendar.MONTH);

			if (anio > 1) {
				// CONSULTAR POR AÑO
				ArrayList<DTOAsistencia> listaAsistencia = this.findListBetWeenYear();
				createBarModelAnio();
				//return "home";
			} else if (mes == 0 && (calendarFin.get(Calendar.YEAR) == calendarIni.get(Calendar.YEAR))) {
				/// CONSULTA POR SEMANA
				ArrayList<DTOAsistencia> listaAsistencia = this.findListBetWeenDays();
				cambiarSemana();
				createBarModelSemana();
				//return "home";
			} else if (mes > 1 && (calendarFin.get(Calendar.YEAR) == calendarIni.get(Calendar.YEAR))) {
				// CONSULTA POR MES
				ArrayList<DTOAsistencia> listaAsistencia = this.findListBetWeenMonths();
				createBarModelRango();
				//return "home";

			
		}



		} else if(this.objdetalleasistencia.getRadio().equals("Fecha")) {
			if(!this.objdetalleasistencia.getMesConsulta().equals("00")){
				ArrayList<DTOAsistencia> listaAsistencia = this.findListByMonth();
				cambiarSemana();
				createBarModelRango();
			
				
			}
			else {
				ArrayList<DTOAsistencia> listaAsistencia = this.findListByHalfYear();
				cambiarFechas();
				createBarModelMeses();
				//return "home";
				
			}
			
		}
		return "home";

	}

	// ESTE METODO YA ESTA LISTO PARA PINTAR LA GRAFICA
	// METODO PARA HACER LA BUSQUEDA POR ASISTENCIA POR SEMETRE
	private ArrayList<DTOAsistencia> findListByHalfYear() {
		System.out.println("Consultando las asistencia de los usuario por semestre");
		this.listaDetalle = (ArrayList<DTOAsistencia>) iEjbDetalleAsistencia.findListByHalfYear(
				Integer.parseInt(this.objdetalleasistencia.getCedula()), this.objdetalleasistencia.getdatanio(),
				this.objdetalleasistencia.getPeriodoAcademico());
		System.out.println("El tamaño de la lista de asistencias es: " + listaDetalle.size());
		return listaDetalle;
	}

	// ESTE METODO AUN NO ESTA LISTO
	// METODO PARA HACER LA BUSQUEDA POR ASISTENCIA EN UN MES EN ESPECIFICO
	public ArrayList<DTOAsistencia>  findListByMonth() {
		System.out.println("Consultando las asistencia de los usuario por Mes");
		this.listaDetalle = (ArrayList<DTOAsistencia>) iEjbDetalleAsistencia.findListByMonth(
				Integer.parseInt(this.objdetalleasistencia.getCedula()), this.objdetalleasistencia.getdatanio(),
				this.objdetalleasistencia.getMesConsulta());
		System.out.println("El tamaño de la lista de asistencias es: " + listaDetalle.size());
		return this.listaDetalle;
	}

	public ArrayList<DTOAsistencia> findListBetWeenYear() {
		System.out.println("Consultando las asistencia de los usuario por Rango de Año");
		this.listaDetalle = (ArrayList<DTOAsistencia>) iEjbDetalleAsistencia.findListBetWeensYear(
				Integer.parseInt(this.objdetalleasistencia.getCedula()), this.objdetalleasistencia.getFecInicio(),
				this.objdetalleasistencia.getFecFin());
		System.out.println("El tamaño de la lista de asistencias es: " + listaDetalle.size());
		return this.listaDetalle;
	}

	public ArrayList<DTOAsistencia> findListBetWeenMonths() {
		System.out.println("Consultando las asistencia de los usuario por Rango de Meses");
		this.listaDetalle = (ArrayList<DTOAsistencia>) iEjbDetalleAsistencia.findListBetWeenMonths(
				Integer.parseInt(this.objdetalleasistencia.getCedula()), this.objdetalleasistencia.getFecInicio(),
				this.objdetalleasistencia.getFecFin());
		System.out.println("El tamaño de la lista de asistencias es: " + listaDetalle.size());
		return this.listaDetalle;
	}

	public ArrayList<DTOAsistencia> findListBetWeenDays() {
		System.out.println("Consultando las asistencia de los usuario por Rango de dia");
		this.listaDetalle = (ArrayList<DTOAsistencia>) iEjbDetalleAsistencia.findListBetWeensDays(
				Integer.parseInt(this.objdetalleasistencia.getCedula()), this.objdetalleasistencia.getFecInicio(),
				this.objdetalleasistencia.getFecFin());
		System.out.println("El tamaño de la lista de asistencias es: " + listaDetalle.size());
		return this.listaDetalle;
	}

	private void consultarReferenciaEJB() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		this.iEjbDetalleAsistencia = (IEjbDetalleasistencia) requestMap.get("EJBGestionDetalleAsistencia_SESSION_KEY");

		if (this.iEjbDetalleAsistencia == null) {

			try {
				InitialContext ic = new InitialContext();
				this.iEjbDetalleAsistencia = (IEjbDetalleasistencia) ic.lookup(
						"java:global/moduloEstadistica-1.0-SNAPSHOT/EjbDetalleasistencia!com.asae.ejbinterface.IEjbDetalleasistencia");

				requestMap.putIfAbsent(EJBGestionDetalleAsistencia_SESSION_KEY, this.iEjbDetalleAsistencia);

				System.out.println("ejb para la gestión del DETALLE DE ASISTENCIA creado");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void cambiarSemana() {
		for (int i = 0; i < listaDetalle.size(); i++) {
			this.listaDetalle.get(i).setFecAsisencia("Semana " + listaDetalle.get(i).getFecAsisencia());
		}
	}

	private void cambiarFechas() {
		for (int i = 0; i < this.listaDetalle.size(); i++) {
			switch (this.listaDetalle.get(i).getFecAsisencia()) {
			case "01":
				this.listaDetalle.get(i).setFecAsisencia("Enero");
				break;
			case "02":
				this.listaDetalle.get(i).setFecAsisencia("Febrero");
				break;
			case "03":
				this.listaDetalle.get(i).setFecAsisencia("Marzo");
				break;
			case "04":
				this.listaDetalle.get(i).setFecAsisencia("Abril");
				break;
			case "05":
				this.listaDetalle.get(i).setFecAsisencia("Mayo");
				break;
			case "06":
				this.listaDetalle.get(i).setFecAsisencia("Junio");
				break;
			case "07":
				this.listaDetalle.get(i).setFecAsisencia("Julio");
				break;
			case "08":
				this.listaDetalle.get(i).setFecAsisencia("Agosto");
				break;
			case "09":
				this.listaDetalle.get(i).setFecAsisencia("Septiembre");
				break;
			case "10":
				this.listaDetalle.get(i).setFecAsisencia("Octubre");
				break;
			case "11":
				this.listaDetalle.get(i).setFecAsisencia("Noviembre");
				break;
			case "12":
				this.listaDetalle.get(i).setFecAsisencia("Diciembre");
				break;
			}
		}
	}

	public BarChartModel initBarModel_Semana() {
		// BarChartModel anio_meses = new BarChartModel();
		BarChartModel anio_meses = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Grafica Asistencia Personal");

		List<Number> values = new ArrayList<Number>();
		for (int i = 0; i < this.listaDetalle.size(); i++) {
			// datos.set(this.listaDetalle.get(i).getFecAsisencia(),
			// this.listaDetalle.get(i).getNumAsistencia());
			values.add(listaDetalle.get(i).getNumAsistencia());
		}

		barDataSet.setData(values);

		List<String> bgColor = new ArrayList<String>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(255, 205, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");

		barDataSet.setBackgroundColor(bgColor);

		List<String> borderColor = new ArrayList<String>();
		borderColor.add("rgb(255, 99, 132)");
		borderColor.add("rgb(255, 159, 64)");
		borderColor.add("rgb(255, 205, 86)");
		borderColor.add("rgb(75, 192, 192)");
		borderColor.add("rgb(54, 162, 235)");
		borderColor.add("rgb(153, 102, 255)");

		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<String>();
		for (int i = 0; i < listaDetalle.size(); i++) {
			labels.add(listaDetalle.get(i).getFecAsisencia());
		}

		data.setLabels(labels);
		anio_meses.setData(data);

		// options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();

		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Asistencia Personal");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");

		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		anio_meses.setOptions(options);
		// anio_meses.setOptions(options);
		// System.out.println("tamanio " + listaDetalle.size());
		// ChartSeries datos = new ChartSeries();
		// datos.setLabel("Meses");

		// anio_meses.addSeries(datos);
		return anio_meses;
	}

	public BarChartModel initBarModel_Meses() {
		// BarChartModel anio_meses = new BarChartModel();
		BarChartModel anio_meses = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Grafica Asistencia Mensual Personal");

		List<Number> values = new ArrayList<Number>();
		for (int i = 0; i < this.listaDetalle.size(); i++) {
			// datos.set(this.listaDetalle.get(i).getFecAsisencia(),
			// this.listaDetalle.get(i).getNumAsistencia());
			values.add(listaDetalle.get(i).getNumAsistencia());
		}

		barDataSet.setData(values);

		List<String> bgColor = new ArrayList<String>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(255, 205, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");

		barDataSet.setBackgroundColor(bgColor);

		List<String> borderColor = new ArrayList<String>();
		borderColor.add("rgb(255, 99, 132)");
		borderColor.add("rgb(255, 159, 64)");
		borderColor.add("rgb(255, 205, 86)");
		borderColor.add("rgb(75, 192, 192)");
		borderColor.add("rgb(54, 162, 235)");
		borderColor.add("rgb(153, 102, 255)");

		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<String>();
		for (int i = 0; i < listaDetalle.size(); i++) {
			labels.add(listaDetalle.get(i).getFecAsisencia());
		}

		data.setLabels(labels);
		anio_meses.setData(data);

		// options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();

		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Asistencia Personal");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");

		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		anio_meses.setOptions(options);
		// anio_meses.setOptions(options);
		// System.out.println("tamanio " + listaDetalle.size());
		// ChartSeries datos = new ChartSeries();
		// datos.setLabel("Meses");

		// anio_meses.addSeries(datos);
		return anio_meses;
	}

	public BarChartModel initBarModel_Anios() {
		// BarChartModel anio_meses = new BarChartModel();
		BarChartModel anio_meses = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Grafica Asistencia Mensual Personal");

		List<Number> values = new ArrayList<Number>();
		for (int i = 0; i < this.listaDetalle.size(); i++) {
			// datos.set(this.listaDetalle.get(i).getFecAsisencia(),
			// this.listaDetalle.get(i).getNumAsistencia());
			values.add(listaDetalle.get(i).getNumAsistencia());
		}

		barDataSet.setData(values);

		List<String> bgColor = new ArrayList<String>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(255, 205, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");

		barDataSet.setBackgroundColor(bgColor);

		List<String> borderColor = new ArrayList<String>();
		borderColor.add("rgb(255, 99, 132)");
		borderColor.add("rgb(255, 159, 64)");
		borderColor.add("rgb(255, 205, 86)");
		borderColor.add("rgb(75, 192, 192)");
		borderColor.add("rgb(54, 162, 235)");
		borderColor.add("rgb(153, 102, 255)");

		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<String>();
		for (int i = 0; i < listaDetalle.size(); i++) {
			labels.add(listaDetalle.get(i).getFecAsisencia());
		}

		data.setLabels(labels);
		anio_meses.setData(data);

		// options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();

		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Asistencia Personal");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");

		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		anio_meses.setOptions(options);
		// anio_meses.setOptions(options);
		// System.out.println("tamanio " + listaDetalle.size());
		// ChartSeries datos = new ChartSeries();
		// datos.setLabel("Meses");

		// anio_meses.addSeries(datos);
		return anio_meses;
	}

	public BarChartModel initBarModelRango() {
		// BarChartModel anio_meses = new BarChartModel();
		BarChartModel anio_meses = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Grafica Asistencia Personal");
		barDataSet.setBackgroundColor("rgba(255,99,132,0.2)");
		barDataSet.setBorderColor("rgb(255,99,132");
		barDataSet.setBorderWidth(1);

		List<Number> values = new ArrayList<Number>();
		for (int i = 0; i < this.listaDetalle.size(); i++) {
			// datos.set(this.listaDetalle.get(i).getFecAsisencia(),
			// this.listaDetalle.get(i).getNumAsistencia());
			values.add(listaDetalle.get(i).getNumAsistencia());
		}

		barDataSet.setData(values);

		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<String>();
		for (int i = 0; i < listaDetalle.size(); i++) {
			labels.add(listaDetalle.get(i).getFecAsisencia());
		}

		data.setLabels(labels);
		anio_meses.setData(data);

		// options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();

		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Asistencia Personal");
		options.setTitle(title);

		/*
		 * Legend legend = new Legend(); legend.setDisplay(true);
		 * legend.setPosition("top");
		 * 
		 * LegendLabel legendLabels = new LegendLabel();
		 * legendLabels.setFontStyle("bold"); legendLabels.setFontColor("#2980B9");
		 * legendLabels.setFontSize(24); legend.setLabels(legendLabels);
		 * options.setLegend(legend);
		 */

		anio_meses.setOptions(options);
		// anio_meses.setOptions(options);
		// System.out.println("tamanio " + listaDetalle.size());
		// ChartSeries datos = new ChartSeries();
		// datos.setLabel("Meses");

		// anio_meses.addSeries(datos);
		return anio_meses;
	}

	public void createBarModelRango() {
		asistenciaPersonal = initBarModelRango();
		// asistenciaPersonal.setTitle("Asistencia Personal");
		// asistenciaPersonal.setLegendPosition("e");
		// asistenciaPersonal.setShowPointLabels(true);
		// asistenciaPersonal.setSeriesColors("4D94FF, 1975FF");

		// Axis xAxis = asistenciaPersonal.getAxis(AxisType.X);
		// xAxis.setLabel("Meses");

		// Axis yAxis = asistenciaPersonal.getAxis(AxisType.Y);
		// yAxis.setLabel("# Asistencias");
		// yAxis.setMin(0);
		// yAxis.setMax(30);
	}

	public void createBarModelSemana() {
		asistenciaPersonal = initBarModel_Semana();
	}

	public void createBarModelAnio() {
		asistenciaPersonal = initBarModel_Anios();
	}

	public void createBarModelMeses() {
		asistenciaPersonal = initBarModel_Meses();
	}

	public BarChartModel getAsistenciaPersonal() {
		return asistenciaPersonal;
	}

	public void setAsistenciaPersonal(BarChartModel asistenciaPersonal) {
		this.asistenciaPersonal = asistenciaPersonal;
	}
}
