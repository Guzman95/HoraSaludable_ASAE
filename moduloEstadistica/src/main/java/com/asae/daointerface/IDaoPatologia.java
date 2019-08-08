package com.asae.daointerface;

import java.util.List;

import javax.persistence.EntityManager;



public interface IDaoPatologia {
	public List<Object[]> findByCount(EntityManager em);
	public List<Object[]> findByGender(EntityManager em, String gender);
	public List<Object[]> findByPosition(EntityManager em, String position);
	public List<Object[]> findOneGarder(EntityManager em, String gender,String position);
}
