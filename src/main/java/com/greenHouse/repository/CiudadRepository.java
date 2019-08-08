package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Ciudad;

public interface CiudadRepository extends CrudRepository<Ciudad, Long> {

	@Query(value = "SELECT * FROM ciudades s WHERE s.nombre = (:nombre)", nativeQuery = true)
	Collection<Ciudad> findAllServicesByName(String nombre);

	@Query(value = "SELECT * FROM ciudades n WHERE upper(n.nombre) = upper((:nombre))", nativeQuery = true)
	Collection<Ciudad> findByNameIgnoreCase(String nombre);
	
	@Query(value = "SELECT * FROM ciudades n WHERE n.nombre = (:nombre)", nativeQuery = true)
	Collection<Ciudad> findByName(String nombre);
}