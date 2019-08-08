package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Admin;
import com.greenHouse.entity.Cliente;

public interface ClientRepository extends CrudRepository<Cliente, Long>{

	@Query(value = "SELECT * FROM clientes a WHERE a.usuario = (:usuario) AND a.contrasenia = (:contrasenia)", nativeQuery = true)
	Collection<Admin> authenticate(String usuario, String contrasenia);
	
}
