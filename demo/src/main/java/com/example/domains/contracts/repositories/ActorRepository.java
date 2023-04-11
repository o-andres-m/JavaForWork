package com.example.domains.contracts.repositories;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.ActorShort;


@RestResource(exported = false) //Esto es para el HATEOAS, auto Rest, que NO NOS PONGA ESTE ENDPOINT
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections{
	
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastNameDesc(String str);

	List<Actor> findTop10ByFirstNameStartingWith(String str, Sort sort);

	@Query("SELECT a FROM Actor a WHERE a.actorId < :id")
	List<Actor> findJPQL(@Param("id") int actorId);
	
	@Query(name="Actor.findAll")
	List<Actor> findAllConJPQL();
	
	@Query(value="SELECT * FROM actor WHERE actor_id < ?1" ,nativeQuery = true)
	List<Actor> findActorConNativeSQL(int actorId);
	
	// Proyecciones, se hacen para devolver solo lo que necesitamos
	// ActorShort solo tiene ID NOMBRE APELLIDO, no obtiene mas datos sin sentido
	
	List<ActorShort> findByActorIdNotNull();
	
	// Le mandamos QUE CLASE necesitamos que nos devuelva, y nos devuelve una lista de esa clase
	// Entonces, podemos pedirle que nos devuelva una lista de Actor, o ActorDto o ActorShort
	// Esto se hace para que a la DB no le pida informacion que no necesitamos.
	
	//<T> List<T> findAllBy(Class <T> type);
	
	
	
	
}
