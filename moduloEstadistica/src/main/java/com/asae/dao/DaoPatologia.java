package com.asae.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asae.daointerface.IDaoPatologia;


public class DaoPatologia implements IDaoPatologia {

	@Override
	public List<Object[]> findByCount(EntityManager em) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("Patologia.findByCount", Object[].class);

		listaRetornada = query.getResultList();
		return listaRetornada;
		
	}

	@Override
	public List<Object[]> findByGender(EntityManager em, String gender) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("Patologia.findByGender", Object[].class);

		query.setParameter("gender", gender);

		listaRetornada = query.getResultList();
		return listaRetornada;
	}

	@Override
	public List<Object[]> findByPosition(EntityManager em, String position) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("Patologia.findByPosition", Object[].class);

		query.setParameter("position", position);

		listaRetornada = query.getResultList();
		return listaRetornada;
	}

	@Override
	public List<Object[]> findOneGarder(EntityManager em, String gender, String position) {
		List<Object[]> listaRetornada = new ArrayList<Object[]>();
		TypedQuery<Object[]> query = null;
		query = em.createNamedQuery("patologiaController.findOneGarder", Object[].class);

		query.setParameter("gender", gender);
		query.setParameter("position", position);

		listaRetornada = query.getResultList();
		return listaRetornada;
	}

}
