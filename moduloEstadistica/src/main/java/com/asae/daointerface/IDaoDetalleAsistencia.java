package com.asae.daointerface;

import java.util.List;

import javax.persistence.EntityManager;

import com.asae.dto.DTOAsistencia;


public interface IDaoDetalleAsistencia {
	public List<Object[]> findListByHalfYear(EntityManager em, int identificacion, String anio, String semestre);
	public List<DTOAsistencia> findListByMonth(EntityManager em, int identificacion, String anio, String mes);
	public List<Object[]> findListBetWeenMonths(EntityManager em, int identificacion, String starDate, String endDate);
	public List<DTOAsistencia> findListBetWeensDays(EntityManager em, int identificacion, String starDate, String endDate);
	public List<Object[]> findListBetWeenYear(EntityManager em, int identificacion, String starDate, String endDate);
	
}



