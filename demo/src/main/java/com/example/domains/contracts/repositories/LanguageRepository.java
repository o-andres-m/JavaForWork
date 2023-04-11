package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Language;

import jakarta.annotation.Resource;

@RepositoryRestResource(path = "idiomas", itemResourceRel = "idioma", collectionResourceRel = "idiomas")
//Es para llamarlo. Todo dentro del SPRING REST
public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>,RepositoryWithProjections{
	
	@RestResource(path = "novedades")
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp time);
	
	@RestResource(path = "todos", exported = false)
	@Override
	//Esto es para hacerle un "Overrride" y que lueo si queremos que NO SE GENERE
	//o que NO APAREZCA, una vez que esta en OVERRIDE, le sacamos que no aparezca con el exported false 
	List<Language> findAll();
}
