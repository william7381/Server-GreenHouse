package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	@Query(value = "SELECT * FROM ingredientes n WHERE upper(n.nombre) = upper((:nombre))", nativeQuery = true)
	Collection<Ingrediente> findByNameIgnoreCase(String nombre);
	
	@Query(value = "SELECT * FROM ingredientes n WHERE n.nombre = (:nombre)", nativeQuery = true)
	Collection<Ingrediente> findByName(String nombre);
}
