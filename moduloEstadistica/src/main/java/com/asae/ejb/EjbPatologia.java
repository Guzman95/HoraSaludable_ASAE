package com.asae.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import com.asae.dao.DaoPatologia;

import com.asae.daointerface.IDaoPatologia;

import com.asae.dto.DTOPatologia;
import com.asae.ejbinterface.IEjbPatologia;
import com.asae.entity.Patologia;
import com.mysql.jdbc.exceptions.MySQLDataException;


//@Transactional(dontRollbackOn=MyException.class, noRollbackFor=MyException2.class)
@Stateful
public class EjbPatologia implements IEjbPatologia {
	private EntityManagerFactory emf=null;
	private EntityManager em=null;
	private EntityTransaction et=null;
	
	Patologia patologia;
	
	@PostConstruct	
	public void inicializarEJB() 
	{
		System.out.println("En EjbPatologia: Entrando de inicializarEJB()...");
		emf=Persistence.createEntityManagerFactory("moduloEstadistica");
		em=emf.createEntityManager();
		System.out.println("En EjbPatologia: Saliendo de inicializarEJB()...");
	}

	@Override
	public List<DTOPatologia> findAll() {
		return null;
		
	}

	@Override
	public List<DTOPatologia> findByGender(String gender) {
		List<Object[]> listaRetornada = null;
		List<DTOPatologia> listaDetalle = new ArrayList<DTOPatologia>();
		try {

			IDaoPatologia iDaoPatologia = new DaoPatologia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoPatologia.findByGender(em, gender);

			for (Object[] obj : listaRetornada) {

				DTOPatologia objDtoAsistencia = new DTOPatologia();
				objDtoAsistencia.setNombre(obj[0].toString());
				objDtoAsistencia.setContador(Integer.parseInt(obj[1].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}

	@Override
	@Transactional(dontRollbackOn = Exception.class)
	public List<DTOPatologia> findByPosition(String position) {
		List<Object[]> listaRetornada = null;
		List<DTOPatologia> listaDetalle = new ArrayList<DTOPatologia>();
		try {

			IDaoPatologia iDaoPatologia = new DaoPatologia();
			et = em.getTransaction();
			et.begin();
			listaRetornada = iDaoPatologia.findByPosition(em, position);

			for (Object[] obj : listaRetornada) {

				DTOPatologia objDtoAsistencia = new DTOPatologia();
				objDtoAsistencia.setNombre(obj[0].toString());
				objDtoAsistencia.setContador(Integer.parseInt(obj[1].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}

	@Override
	public List<DTOPatologia> findCount() {
		List<Object[]> listaRetornada = null;
		List<DTOPatologia> listaDetalle = new ArrayList<DTOPatologia>();
		try {

			IDaoPatologia iDaoPatologia = new DaoPatologia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoPatologia.findByCount(em);

			for (Object[] obj : listaRetornada) {

				DTOPatologia objDtoAsistencia = new DTOPatologia();
				objDtoAsistencia.setNombre(obj[0].toString());
				objDtoAsistencia.setContador(Integer.parseInt(obj[1].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}

	@Override
	public List<DTOPatologia> findOneGarder(String gender, String position) {
		List<Object[]> listaRetornada = null;
		List<DTOPatologia> listaDetalle = new ArrayList<DTOPatologia>();
		try {

			IDaoPatologia iDaoPatologia = new DaoPatologia();
			et = em.getTransaction();
			et.begin();

			listaRetornada = iDaoPatologia.findOneGarder(em,gender,position);

			for (Object[] obj : listaRetornada) {

				DTOPatologia objDtoAsistencia = new DTOPatologia();
				objDtoAsistencia.setNombre(obj[0].toString());
				objDtoAsistencia.setContador(Integer.parseInt(obj[1].toString()));
				listaDetalle.add(objDtoAsistencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		et.commit();

		return listaDetalle;
	}
	

}
