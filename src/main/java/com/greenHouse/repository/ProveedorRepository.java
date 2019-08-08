package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Proveedor;

public interface ProveedorRepository extends CrudRepository<Proveedor, Long>{

	@Query(value = "SELECT * FROM proveedores n WHERE upper(n.nombreComercial) = upper((:nombreComercial))", nativeQuery = true)
	Collection<Proveedor> findByNameIgnoreCase(String nombre);
	
	@Query(value = "SELECT * FROM proveedores n WHERE n.nombreComercial = (:nombreComercial)", nativeQuery = true)
	Collection<Proveedor> findByName(String nombre);
	
	@Query(value = "SELECT * FROM proveedores n WHERE upper(n.dni) = upper((:dni))", nativeQuery = true)
	Collection<Proveedor> findByDniIgnoreCase(String dni);
	
	@Query(value = "SELECT * FROM proveedores n WHERE n.dni = (:dni)", nativeQuery = true)
	Collection<Proveedor> findByDni(String dni);
}
