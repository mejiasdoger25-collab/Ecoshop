package com.salesianostriana.dam.ecoshop.service.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class BaseServiceImp<T, ID, R extends JpaRepository<T, ID>> implements BaseServiceI<T, ID> {

	private final R repository;

	@Override
	public T save(T t) {
		return repository.save(t);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	@Override
	public T edit(T t) {
		return repository.save(t);
	}

	/*
	@Override
	public T editById(ID id) {
		return repository.save(id);
	}
	*/

	@Override
	public void delete(T t) {
		repository.delete(t);
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
		
	}
}
