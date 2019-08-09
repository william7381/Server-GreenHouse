package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Long>{

	@Query(value = "SELECT * FROM proveedores n WHERE upper(n.nombre_comercial) = upper((:nombreComercial))", nativeQuery = true)
	Collection<Proveedor> findByNameIgnoreCase(String nombreComercial);
	
	@Query(value = "SELECT * FROM proveedores n WHERE upper(n.dni) = upper((:dni))", nativeQuery = true)
	Collection<Proveedor> findByDniIgnoreCase(String dni);
}
