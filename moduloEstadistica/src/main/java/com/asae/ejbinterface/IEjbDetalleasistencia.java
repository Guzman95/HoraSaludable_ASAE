package com.asae.ejbinterface;

import java.util.List;

import com.asae.dto.DTOAsistencia;

public interface IEjbDetalleasistencia {

	public List<DTOAsistencia> findListByHalfYear(int identificacion, String anio, String semestre);

	public List<DTOAsistencia> findListByMonth(int identificacion, String anio, String mes);

	public List<DTOAsistencia> findListBetWeenMonths(int identificacion, String starDate, String endDate);

	public List<DTOAsistencia> findListBetWeensDays(int identificacion, String starDate, String endDate);

	public List<DTOAsistencia> findListBetWeensYear(int identificacion, String starDate, String endDate);

	public void finalizarEJB();
}
