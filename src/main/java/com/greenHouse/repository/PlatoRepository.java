package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Plato;

public interface PlatoRepository extends CrudRepository<Plato, Long>{

	@Query(value = "SELECT * FROM platos n WHERE upper(n.nombre) = upper((:nombre))", nativeQuery = true)
	Collection<Plato> findByNameIgnoreCase(String nombre);
}
