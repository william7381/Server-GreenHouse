package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.TipoProducto;

public interface TiposProductosRepository extends CrudRepository<TipoProducto, Long>{

	@Query(value = "SELECT * FROM tipo_productos n WHERE upper(n.descripcion) = upper((:descripcion))", nativeQuery = true)
	Collection<TipoProducto> findByNameIgnoreCase(String descripcion);
}
