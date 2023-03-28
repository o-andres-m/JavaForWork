package com.example.domains.contracts.repositories;

import java.util.List;import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.domains.entities.Actor;


public interface ActorRepository extends JpaRepository<Actor, Integer>{
	
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String str);

	List<Actor> findTop10ByFirstNameStartingWith(String str, Sort sort);

	@Query("SELECT a FROM Actor a WHERE a.actorId < :id")
	List<Actor> findJPQL(@Param("id") int actorId);
	
	@Query(name="Actor.findAll")
	List<Actor> findAllConJPQL();
	
	@Query(value="SELECT * FROM actor WHERE actor_id < ?1" ,nativeQuery = true)
	List<Actor> findActorConNativeSQL(int actorId);
	
	
	/*	Consultas JPQL:
		@Query("from Profesor p where p.edad > 67")
		List<Profesor> findJubilados();
	 * 
	 * 
	 * --------
	 * 
	 * @Query(value="select * from Profesores p where p.edad between ?1 and ?2", nativeQuery=true)
		List<Profesor> findActivos(int inicial, int final);
	 * 
	 * --------
		@Query("from Profesor p where p.edad > :edad")
		List<Profesor> findJubilados(@Param("edad") int edad);	 * 
	 * 
	 * --------
	 * 
	 * 
	 * 
	 */
	
}
