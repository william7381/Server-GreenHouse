package com.greenHouse.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "ciudades")
public class Ciudad {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column( length = 30 )
	private String nombre;
	
	public Ciudad() {
		super();
	}
	
	public Ciudad(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}