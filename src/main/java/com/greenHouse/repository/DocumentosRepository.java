package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.TipoDocumento;

public interface DocumentosRepository extends CrudRepository<TipoDocumento, Long>{

	@Query(value = "SELECT * FROM tipoDocumentos n WHERE upper(n.descripcion) = upper((:descripcion))", nativeQuery = true)
	Collection<TipoDocumento> findByNameIgnoreCase(String descripcion);
	
	@Query(value = "SELECT * FROM tipoDocumentos n WHERE n.descripcion = (:descripcion)", nativeQuery = true)
	Collection<TipoDocumento> findByName(String descripcion);
}
