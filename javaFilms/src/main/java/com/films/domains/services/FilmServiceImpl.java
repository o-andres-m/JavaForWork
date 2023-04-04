package com.films.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.films.domains.contracts.repository.FilmRepository;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Film;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {


	@Autowired
	FilmRepository dao;
	

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
	public Iterable<Film> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("Film cannot be null.");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(dao.existsById(item.getFilmId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		
		var actors = item.getActors();
		var categories = item.getCategories();
		item.clearActors();
		item.clearCategories();
		
		var newItem = dao.save(item);
		
		newItem.setActors(actors);
		newItem.setCategories(categories);
		return dao.save(newItem);
	}

	@Override
	@Transactional
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("Film cannot be null.");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		var leido = dao.findById(item.getFilmId());
		if(leido.isEmpty())
			throw new NotFoundException();
		return dao.save(item.merge(leido.get()));
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if (item == null) throw new InvalidDataException("Film cannot be null.");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Film> news(@NonNull Timestamp time) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(time);
	}

}
