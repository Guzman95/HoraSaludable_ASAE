package com.asae.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.asae.daointerface.IDaoDetalleAsistencia;
import com.asae.dto.DTOAsistencia;

public class DaoDetalleAsistencia implements IDaoDetalleAsistencia {

	@Override
	public List<Object[]> findListByHalfYear(EntityManager em, int identificacion, String anio, String semestre) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		if (semestre.equals("1")) {
			query = em.createNamedQuery("detalleasistencia.findByHalfYearI", Object[].class);
		} else {
			query = em.createNamedQuery("detalleasistencia.findByHalfYearII", Object[].class);
		}

		query.setParameter("identificacion", identificacion);
		query.setParameter("anio", anio);
		listaRetornada = query.getResultList();

		return listaRetornada;
	}

	@Override
	public List<DTOAsistencia> findListByMonth(EntityManager em, int identificacion, String anio, String mes) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("detalleasistencia.findListByMonth", Object[].class);

		query.setParameter("identificacion", identificacion);
		query.setParameter("mes", mes);
		query.setParameter("anio", anio);
		listaRetornada = query.getResultList();
		return tratamientoDeDatosMes(listaRetornada);
	}

	public List<DTOAsistencia> tratamientoDeDatosMes(List<Object[]> lista) {
		int semanaAntes = 0, semanaActual = 0, contDias = 0;
		DTOAsistencia objAsistencia;
		
		List<DTOAsistencia> listAux = new ArrayList<DTOAsistencia>();
		for (int i = 0; i < lista.size(); i++) {
			TimeZone timezone = TimeZone.getDefault();
			Calendar calendar = new GregorianCalendar(timezone);
			String[] splitFecha = lista.get(i)[0].toString().split("-");
			splitFecha[2] = splitFecha[2].substring(0, 2);

			calendar.set(Integer.parseInt(splitFecha[0]), Integer.parseInt(splitFecha[1]) - 1,
			Integer.parseInt(splitFecha[2]));
			
			semanaActual = calendar.get(Calendar.WEEK_OF_MONTH);
			if(Integer.parseInt(lista.get(i)[1].toString())==1) {

				if(contDias==0) {
					contDias++;
					semanaAntes=semanaActual;
				}else if(semanaActual==semanaAntes){
					contDias++;
					semanaAntes=semanaActual;
				}else {
					objAsistencia=new DTOAsistencia();
					objAsistencia.setFecAsisencia(semanaAntes+"");
					objAsistencia.setNumAsistencia(contDias);
					contDias=0;
					contDias++;
					listAux.add(objAsistencia);
					semanaAntes=semanaActual;
				}
				
				
				if((i+1)==lista.size()) {
					objAsistencia=new DTOAsistencia();
					objAsistencia.setFecAsisencia(semanaActual+"");
					objAsistencia.setNumAsistencia(contDias);
					listAux.add(objAsistencia);
				}
			}
		}
		return listAux;
	}

	@Override
	public List<Object[]> findListBetWeenMonths(EntityManager em, int identificacion, String starDate, String endDate) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		
		query = em.createNamedQuery("detalleasistencia.findListBetWeenMonths", Object[].class);

		query.setParameter("identificacion", identificacion);
		query.setParameter("startDate", starDate);
		query.setParameter("endDate", endDate);
		listaRetornada = query.getResultList();

		return listaRetornada;
	}

	@Override
	public List<DTOAsistencia> findListBetWeensDays(EntityManager em, int identificacion, String starDate, String endDate) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("detalleasistencia.findListBetWeenDays", Object[].class);

		query.setParameter("identificacion", identificacion);
		query.setParameter("startDate", starDate);
		query.setParameter("endDate", endDate);
		listaRetornada = query.getResultList();
		return tratamientoDeDatosMes(listaRetornada);

	}

	@Override
	public List<Object[]> findListBetWeenYear(EntityManager em, int identificacion, String starDate, String endDate) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		
		query = em.createNamedQuery("detalleasistencia.findListBetWeenYears", Object[].class);

		query.setParameter("identificacion", identificacion);
		query.setParameter("startDate", starDate);
		query.setParameter("endDate", endDate);
		listaRetornada = query.getResultList();

		return listaRetornada;
	}

}
