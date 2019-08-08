package com.asae.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.asae.dao.DaoDetalleAsistencia;
import com.asae.daointerface.IDaoDetalleAsistencia;
import com.asae.dto.DTOAsistencia;
import com.asae.ejbinterface.IEjbDetalleasistencia;
import com.asae.entity.Detalleasistencia;


@Stateful
public class EjbDetalleasistencia implements IEjbDetalleasistencia {

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction et = null;

	Detalleasistencia detAsistencia;

	@PostConstruct
	public void inicializarEJB() {
		System.out.println("En EjbUsuario: Entrando de inicializarEJB()...");
		emf = Persistence.createEntityManagerFactory("moduloEstadistica");
		em = emf.createEntityManager();

		/*
		 * emf_date = Persistence.createEntityManagerFactory("$objectdb/db/test.odb");
		 * em_date = emf_date.createEntityManager();
		 */

		System.out.println("En EjbUsuario: Saliendo de inicializarEJB()...");
	}

	@Override
	public List<DTOAsistencia> findListByHalfYear(int identificacion, String anio, String semestre) {
		List<Object[]> listaRetornada = null;
		List<DTOAsistencia> listaDetalle = new ArrayList<DTOAsistencia>();
		try {

			IDaoDetalleAsistencia iDaoDetalle = new DaoDetalleAsistencia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoDetalle.findListByHalfYear(em, identificacion, anio, semestre);

			for (Object[] obj : listaRetornada) {

				DTOAsistencia objDtoAsistencia = new DTOAsistencia();
				objDtoAsistencia.setFecAsisencia(obj[1].toString());
				objDtoAsistencia.setNumAsistencia(Integer.parseInt(obj[0].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}

	@Override
	public void finalizarEJB() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DTOAsistencia> findListByMonth(int identificacion, String anio, String mes) {
		List<DTOAsistencia> listaRetornada = null;
		try {

			IDaoDetalleAsistencia iDaoDetalle = new DaoDetalleAsistencia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoDetalle.findListByMonth(em, identificacion, anio, mes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaRetornada;
	}

	@Override
	public List<DTOAsistencia> findListBetWeenMonths(int identificacion, String starDate, String endDate) {
		List<Object[]> listaRetornada = null;
		List<DTOAsistencia> listaDetalle = new ArrayList<DTOAsistencia>();
		try {

			IDaoDetalleAsistencia iDaoDetalle = new DaoDetalleAsistencia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoDetalle.findListBetWeenMonths(em, identificacion, starDate, endDate);

			for (Object[] obj : listaRetornada) {

				DTOAsistencia objDtoAsistencia = new DTOAsistencia();
				objDtoAsistencia.setFecAsisencia(obj[1].toString());
				objDtoAsistencia.setNumAsistencia(Integer.parseInt(obj[0].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();
		return listaDetalle;

	}

	@Override
	public List<DTOAsistencia> findListBetWeensDays(int identificacion, String starDate, String endDate) {
		List<DTOAsistencia> listaRetornada = null;
		try {

			IDaoDetalleAsistencia iDaoDetalle = new DaoDetalleAsistencia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoDetalle.findListBetWeensDays(em, identificacion, starDate, endDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaRetornada;
	}

	@Override
	public List<DTOAsistencia> findListBetWeensYear(int identificacion, String starDate, String endDate) {
		List<Object[]> listaRetornada = null;
		List<DTOAsistencia> listaDetalle = new ArrayList<DTOAsistencia>();
		try {

			IDaoDetalleAsistencia iDaoDetalle = new DaoDetalleAsistencia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoDetalle.findListBetWeenYear(em, identificacion, starDate, endDate);

			for (Object[] obj : listaRetornada) {

				DTOAsistencia objDtoAsistencia = new DTOAsistencia();
				objDtoAsistencia.setFecAsisencia(obj[1].toString());
				objDtoAsistencia.setNumAsistencia(Integer.parseInt(obj[0].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}

}
