package com.films.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.films.domains.contracts.repository.FilmRepository;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Film;

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
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if (item == null) throw new InvalidDataException("Item cannot be null.");
		if (item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		if (dao.existsById(item.getFilmId())) throw new DuplicateKeyException(item.getErrorsMessage());
		return dao.save(item);
	}

	@Override
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if (item == null) throw new InvalidDataException("Item cannot be null.");
		if (item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		if (!dao.existsById(item.getFilmId())) throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if (item == null) throw new InvalidDataException("Item cannot be null.");
		dao.delete(item);
		
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

}
