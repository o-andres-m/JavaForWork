package com.films.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.films.domains.contracts.repository.ActorRepository;
import com.films.domains.contracts.services.ActorService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Actor;

@Service
public class ActorServiceImpl implements ActorService {
	
	@Autowired
	ActorRepository dao;

	
	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Actor> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Actor> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if(item==null) throw new InvalidDataException("Actor cannot be null.");
		if(item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		if(dao.existsById(item.getActorId())) throw new DuplicateKeyException(item.getErrorsMessage());
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if(item==null) throw new InvalidDataException("Actor cannot be null.");
		if(item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		if(!dao.existsById(item.getActorId())) throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if (item==null) throw new InvalidDataException("Actor cannot be null.");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Actor> news(Timestamp timestamp) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(timestamp);
	}


}
