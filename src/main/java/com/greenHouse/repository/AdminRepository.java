package com.greenHouse.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenHouse.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long>{

	@Query(value = "SELECT * FROM admin a WHERE a.usuario = (:usuario) AND a.contrasenia = (:contrasenia)", nativeQuery = true)
	Collection<Admin> authenticate(String usuario, String contrasenia);
	
}
