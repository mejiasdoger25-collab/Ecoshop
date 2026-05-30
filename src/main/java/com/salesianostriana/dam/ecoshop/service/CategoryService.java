package com.salesianostriana.dam.ecoshop.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Category;
import com.salesianostriana.dam.ecoshop.repository.CategoryRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

@Service
public class CategoryService extends BaseServiceImp<Category,Long,CategoryRepository> {

	private final CategoryRepository repository;
	
	public CategoryService(CategoryRepository repository) {
		super(repository);
		this.repository = repository;
	}

}