package com.salesianostriana.dam.ecoshop.service.base;

import java.util.Optional;
import java.util.List;

public interface BaseServiceI <T, ID> {

	//create
	T save (T t);
	
	//read
	List <T> findAll();
	Optional <T> findById (ID id);
	
	//update
	T edit (T t);
	//T editById (ID id);//para testear y no hacerlo por la web
	
	//delete
	//T delete (T t);
	void delete (T t);
	void deleteById (ID id);
}
