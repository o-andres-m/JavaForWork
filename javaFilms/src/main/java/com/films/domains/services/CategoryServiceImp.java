package com.films.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.films.domains.contracts.repository.CategoryRepository;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Category;

@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	CategoryRepository dao;

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
	public Iterable<Category> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Category> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if(item==null) throw new InvalidDataException("Category cannot be null");
		if(dao.existsById(item.getCategoryId())) throw new DuplicateKeyException(item.getErrorsMessage());
		if(item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		return dao.save(item);
	}

	@Override
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if(item==null) throw new InvalidDataException("Category cannot be null");
		if(item.isInvalid()) throw new InvalidDataException(item.getErrorsMessage());
		if(!dao.existsById(item.getCategoryId())) throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		if(item==null) throw new InvalidDataException("Category cannot be null");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Category> news(Timestamp time) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(time);
	}


}
