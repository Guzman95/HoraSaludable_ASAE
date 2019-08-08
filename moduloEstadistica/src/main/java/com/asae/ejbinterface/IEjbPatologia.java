package com.asae.ejbinterface;

import java.util.List;

import com.asae.dto.DTOPatologia;;

public interface IEjbPatologia {
	public List<DTOPatologia> findAll();
	
	public List<DTOPatologia> findByGender(String gender);
	public List<DTOPatologia> findByPosition(String position);
	public List<DTOPatologia> findCount();
	public List<DTOPatologia> findOneGarder(String gender,String position);		
}





