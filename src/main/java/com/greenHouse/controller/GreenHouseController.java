package com.greenHouse.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greenHouse.entity.Admin;
import com.greenHouse.entity.Ciudad;
import com.greenHouse.entity.Cliente;
import com.greenHouse.entity.Factura;
import com.greenHouse.entity.Ingrediente;
import com.greenHouse.entity.Plato;
import com.greenHouse.entity.Proveedor;
import com.greenHouse.entity.TipoDocumento;
import com.greenHouse.entity.TipoProducto;
import com.greenHouse.persistence.JsonManager;
import com.greenHouse.repository.AdminRepository;
import com.greenHouse.repository.CiudadRepository;
import com.greenHouse.repository.ClientRepository;
import com.greenHouse.repository.DocumentosRepository;
import com.greenHouse.repository.FacturaRepository;
import com.greenHouse.repository.IngredienteRepository;
import com.greenHouse.repository.PlatoRepository;
import com.greenHouse.repository.TiposProductosRepository;
import com.greenHouse.repository.ProveedorRepository;

@RestController
public class GreenHouseController {
	@Autowired
	private CiudadRepository ciudadRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private DocumentosRepository documentosRepository;
	@Autowired
	private TiposProductosRepository tiposProductosRepository;
	@Autowired
	private IngredienteRepository ingredienteRepository;
	@Autowired
	private PlatoRepository platoRepository;
	@Autowired
	private FacturaRepository facturaRepository;
	private Logger log = Logger.getLogger("Logger Green House");
	private static final int ERR_NAME_EXIST = 1;
	private static final int ERR_DNI_EXIST = 2;
	
	public GreenHouseController() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (documentosRepository == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (documentosRepository != null) {
					documentosRepository.save(new TipoDocumento("Cedula de Ciudadania"));
					documentosRepository.save(new TipoDocumento("Documento de Identidad"));
					documentosRepository.save(new TipoDocumento("Pasaporte"));
					ciudadRepository.save(new Ciudad("Tunja"));
					ciudadRepository.save(new Ciudad("Duitama"));
					ciudadRepository.save(new Ciudad("Sogamoso"));
					ciudadRepository.save(new Ciudad("Villa de Leyva"));
					adminRepository.save(new Admin("a", "a123"));
					tiposProductosRepository.save(new TipoProducto("Carnes"));
					tiposProductosRepository.save(new TipoProducto("Lacteos"));
					tiposProductosRepository.save(new TipoProducto("Frutas"));
					tiposProductosRepository.save(new TipoProducto("Vegetales"));
				}
			}
		}).start();
	}
	
	@RequestMapping(value = "/login/admin/{usuario}/{password}", method = RequestMethod.GET)
	public String loginAdmin(@PathVariable("usuario") String usuario, @PathVariable("password") String password) {
		Collection<Admin> authenticate = adminRepository.authenticate(usuario, password);
		if (authenticate.size() == 0) {
			log.warn("Intento de Logueo de Administrador con credenciales incorrectas");
			return JsonManager.objectToJson(false);
		}
		log.info("Intento de Logueo de Administrador Correcto");
		return JsonManager.objectToJson(true);
	}
	
	@RequestMapping(value = "/login/client/{usuario}/{password}", method = RequestMethod.GET)
	public String loginClient(@PathVariable("usuario") String usuario, @PathVariable("password") String password) {
		Collection<Admin> authenticate = clientRepository.authenticate(usuario, password);
		if (authenticate.size() == 0) {
			log.warn("Intento de Logueo de Cliente con credenciales incorrectas");
			return JsonManager.objectToJson(false);
		}
		log.info("Intento de Logueo de Cliente Correcto");
		return JsonManager.objectToJson(true);
	}
	
	// ----------Ciudades---------------------------------------//

	@RequestMapping(value = "/ciudades", method = RequestMethod.GET)
	public String getCiudades() {
		log.info("Peticion para obtener ciduades");
		return JsonManager.toJson(ciudadRepository.findAll());
	}
	
	@RequestMapping(value = "/ciudad/{id}", method = RequestMethod.DELETE)
	public String deleteCiudades(@PathVariable Long id) {
		if (ciudadRepository.existsById(id)) {
			ciudadRepository.deleteById(id);
			log.info("Ciudad eliminada");
			return "eliminado";
		}
		log.info("No se puede eliminar ciudad");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/ciudad", method = RequestMethod.PUT)
	public String updateCiudades(@Valid @RequestBody Ciudad p) {
		if (ciudadRepository.findByName(p.getNombre()).size() != 0) {
			log.warn("Intento de editar Ciudad con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Ciudad Editada");
		return JsonManager.toJson(ciudadRepository.save(p));
	}
	
	@RequestMapping(value = "/ciudad", method = RequestMethod.POST)
	public String createCiudades(@Valid @RequestBody Ciudad p) {
		if (ciudadRepository.findByNameIgnoreCase(p.getNombre()).size() != 0) {
			log.warn("Intento de agregar Ciudad con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Ciudad Agregada");
		return JsonManager.toJson(ciudadRepository.save(p));
	}
	
	// ----------Tipos de Documento---------------------------------------//
	
	@RequestMapping(value = "/documentos", method = RequestMethod.GET)
	public String getTiposDocumento() {
		log.info("Peticion para obtener Tipos de Documento");
		return JsonManager.toJson(documentosRepository.findAll());
	}
	
	@RequestMapping(value = "/documento/{id}", method = RequestMethod.DELETE)
	public String deleteTiposDocumento(@PathVariable Long id) {
		if (documentosRepository.existsById(id)) {
			documentosRepository.deleteById(id);
			log.info("Ciudad eliminada");
			return "eliminado";
		}
		log.info("No se puede eliminar ciudad");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/documento", method = RequestMethod.PUT)
	public String updateTiposDocumento(@Valid @RequestBody TipoDocumento p) {
		if (documentosRepository.findByName(p.getDescripcion()).size() != 0) {
			log.warn("Intento de editar Tipo de Documento con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Ciudad Editada");
		return JsonManager.toJson(documentosRepository.save(p));
	}
	
	@RequestMapping(value = "/documento", method = RequestMethod.POST)
	public String createTiposDocumento(@Valid @RequestBody TipoDocumento p) {
		if (documentosRepository.findByNameIgnoreCase(p.getDescripcion()).size() != 0) {
			log.warn("Intento de agregar Tipo de Documento con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Tipo de Documento Agregado");
		return JsonManager.toJson(documentosRepository.save(p));
	}
	
	// ----------Tipos de Productos---------------------------------------//
	
		@RequestMapping(value = "/tiposProducto", method = RequestMethod.GET)
		public String getTiposProductos() {
			log.info("Peticion para obtener Tipos de Producto");
			return JsonManager.toJson(tiposProductosRepository.findAll());
		}
		
		@RequestMapping(value = "/tipoProducto/{id}", method = RequestMethod.DELETE)
		public String deleteTiposProductos(@PathVariable Long id) {
			if (tiposProductosRepository.existsById(id)) {
				tiposProductosRepository.deleteById(id);
				log.info("Tipo de Producto Eliminada");
				return "eliminado";
			}
			log.info("No se puede Eliminar Tipo de Producto");
			return "No se encontro";
		}
		
		@RequestMapping(value = "/tipoProducto", method = RequestMethod.PUT)
		public String updateTiposProductos(@Valid @RequestBody TipoProducto p) {
			if (tiposProductosRepository.findByName(p.getDescripcion()).size() != 0) {
				log.warn("Intento de editar Tipo de Producto con el mismo nombre de una ya registrada");
				return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
			}
			log.info("Tipo de Producto Editado");
			return JsonManager.toJson(tiposProductosRepository.save(p));
		}
		
		@RequestMapping(value = "/tipoProducto", method = RequestMethod.POST)
		public String createTiposProductos(@Valid @RequestBody TipoProducto p) {
			if (tiposProductosRepository.findByNameIgnoreCase(p.getDescripcion()).size() != 0) {
				log.warn("Intento de agregar Tipo de Producto con el mismo nombre de una ya registrada");
				return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
			}
			log.info("Tipo de Producto Agregado");
			return JsonManager.toJson(tiposProductosRepository.save(p));
		}

//	@RequestMapping(value = "/servicio/{id}", method = RequestMethod.GET)
//	public String getServicio(@PathVariable Long id) {
//		return JsonManager.toJson(estudianteRepository.findById(id));
//	}
//
//	@RequestMapping(value = "/servicio/{id}", method = RequestMethod.DELETE)
//	public String deletServicio(@PathVariable Long id) {
//		servicioRepository.deleteById(id);
//		return "Borrado";
//	}
//
//	@RequestMapping(value = "/servicio", method = RequestMethod.PUT)
//	public String updateServicio(@Valid @RequestBody Ciudad p) {
//		return JsonManager.toJson(servicioRepository.save(p));
//	}
//
//	@RequestMapping(value = "/ciudad", method = RequestMethod.POST)
//	public String createServicio(@Valid @RequestBody Ciudad p) {
//		if (ciudadRepository.findAllServicesByName(p.getNombre()).size() == 0) {
//			return JsonManager.toJson(ciudadRepository.save(p));
//		}
//		System.out.println(ciudadRepository.findAllServicesByName(p.getNombre()).size());
//		return "Error";
//	}
	
	// ----------Proveedores---------------------------------------//
	
	@RequestMapping(value = "/proveedores", method = RequestMethod.GET)
	public String getProveedor() {
		log.info("Peticion para obtener Proveedores");
		return JsonManager.toJson(proveedorRepository.findAll());
	}

	@RequestMapping(value = "/proveedor/{id}", method = RequestMethod.DELETE)
	public String deletProveedor(@PathVariable Long id) {
		if (proveedorRepository.existsById(id)) {
			proveedorRepository.deleteById(id);
			log.info("Proveedor Eliminada");
			return "eliminado";
		}
		log.info("No se puede Eliminar Proveedor");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/proveedor", method = RequestMethod.PUT)
	public String updateProveedor(@Valid @RequestBody Proveedor p) {
		if (proveedorRepository.findByDni(p.getDni()).size() != 0) {
			log.warn("Intento de editar Plato con el mismo dni de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_DNI_EXIST);
		}
		if (proveedorRepository.findByName(p.getNombreComercial()).size() != 0) {
			log.warn("Intento de editar Proveedor con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Proveedor Editado");
		return JsonManager.toJson(proveedorRepository.save(p));
	}

	@RequestMapping(value = "/proveedor", method = RequestMethod.POST)
	public String createProveedor(@Valid @RequestBody Proveedor p) {
		if (proveedorRepository.findByDni(p.getDni()).size() != 0) {
			log.warn("Intento de agregar Plato con el mismo dni de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_DNI_EXIST);
		}
		if (proveedorRepository.findByNameIgnoreCase(p.getNombreComercial()).size() != 0) {
			log.warn("Intento de agregar Proveedor con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Proveedor Agregado");
		return JsonManager.toJson(proveedorRepository.save(p));
	}
	
	// ----------Ingredienetes---------------------------------------//
	
	@RequestMapping(value = "/ingredientes", method = RequestMethod.GET)
	public String getIn() {
		log.info("Peticion para obtener Ingredientes");
		return JsonManager.toJson(ingredienteRepository.findAll());
	}
	
	@RequestMapping(value = "/ingrediente/{id}", method = RequestMethod.DELETE)
	public String deleteIn(@PathVariable Long id) {
		if (ingredienteRepository.existsById(id)) {
			ingredienteRepository.deleteById(id);
			log.info("Ingrediente Eliminado");
			return "eliminado";
		}
		log.info("No se puede Eliminar Ingrediente");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/ingrediente", method = RequestMethod.PUT)
	public String updateIn(@Valid @RequestBody Ingrediente p) {
		if (ingredienteRepository.findByName(p.getNombre()).size() != 0) {
			log.warn("Intento de editar Ingrediente con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		if (ingredienteRepository.existsById(p.getId())) {
			log.info("Asignando Fecha existente de Creacion de Ingrediente");
			p.setFechaIngreso(ingredienteRepository.findById(p.getId()).get().getFechaIngreso());
		}
		log.info("Ingrediente Editado");
		return JsonManager.toJson(ingredienteRepository.save(p));
	}
	
	@RequestMapping(value = "/ingrediente", method = RequestMethod.POST)
	public String createIn(@Valid @RequestBody Ingrediente p) {
		if (ingredienteRepository.findByNameIgnoreCase(p.getNombre()).size() != 0) {
			log.warn("Intento de agregar Ingrediente con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Asignando nueva Fecha de Creacion de Ingrediente");
		p.setFechaIngreso(new Date(System.currentTimeMillis()));
		log.info("Ingrediente Agregado");
		return JsonManager.toJson(ingredienteRepository.save(p));
	}
	
	// ----------Platos---------------------------------------//
	
	@RequestMapping(value = "/platos", method = RequestMethod.GET)
	public String getPl() {
		log.info("Peticion para obtener Platos");
		return JsonManager.toJson(platoRepository.findAll());
	}
	
	@RequestMapping(value = "/plato/{id}", method = RequestMethod.DELETE)
	public String deletePl(@PathVariable Long id) {
		if (platoRepository.existsById(id)) {
			platoRepository.deleteById(id);
			log.info("Plato Eliminado");
			return "eliminado";
		}
		log.info("No se puede Eliminar Plato");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/plato", method = RequestMethod.PUT)
	public String updatePl(@Valid @RequestBody Plato p) {
		if (platoRepository.findByName(p.getNombre()).size() != 0) {
			log.warn("Intento de editar Plato con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		if (platoRepository.existsById(p.getId())) {
			log.info("Asignando Fecha existente de Creacion de Plato");
			p.setFechaIngreso(platoRepository.findById(p.getId()).get().getFechaIngreso());
		}
		log.info("Ingrediente Editado");
		return JsonManager.toJson(platoRepository.save(p));
	}
	
	@RequestMapping(value = "/plato", method = RequestMethod.POST)
	public String createPl(@Valid @RequestBody Plato p) {
		if (platoRepository.findByNameIgnoreCase(p.getNombre()).size() != 0) {
			log.warn("Intento de agregar Plato con el mismo nombre de una ya registrada");
			return JsonManager.objectToJsonErr(ERR_NAME_EXIST);
		}
		log.info("Asignando nueva Fecha de Creacion de Plato");
		p.setFechaIngreso(new Date(System.currentTimeMillis()));
		log.info("Plato Agregado");
		return JsonManager.toJson(platoRepository.save(p));
	}
	
	// ----------Facturas---------------------------------------//
	
	@RequestMapping(value = "/facturas", method = RequestMethod.GET)
	public String getFa() {
		log.info("Peticion para obtener Facturas");
		return JsonManager.toJson(facturaRepository.findAll());
	}
	
	@RequestMapping(value = "/factura/{id}", method = RequestMethod.DELETE)
	public String deletFa(@PathVariable Long id) {
		if (facturaRepository.existsById(id)) {
			facturaRepository.deleteById(id);
			log.info("Factura Eliminada");
			return "eliminado";
		}
		log.info("No se puede Eliminar Factura");
		return "No se encontro";
	}
	
	@RequestMapping(value = "/factura", method = RequestMethod.PUT)
	public String updateFa(@Valid @RequestBody Factura p) {
		if (facturaRepository.existsById(p.getId())) {
			log.info("Asignando Fecha existente de Creacion de Factura");
			p.setFechaFacturacion(facturaRepository.findById(p.getId()).get().getFechaFacturacion());
		}
		log.info("Factura Editada");
		return JsonManager.toJson(facturaRepository.save(p));
	}
	
	@RequestMapping(value = "/factura", method = RequestMethod.POST)
	public String createFa(@Valid @RequestBody Factura p) {
		log.info("Asignando nueva Fecha de Creacion de Factura");
		p.setFechaFacturacion(new Date(System.currentTimeMillis()));
		log.info("Factura Agregada");
		return JsonManager.toJson(facturaRepository.save(p));
	}
	
	// ----------Clientes---------------------------------------//
	
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public String getCl() {
		log.info("Peticion para obtener Clientes");
		return JsonManager.toJson(clientRepository.findAll());
	}

	//		@RequestMapping(value = "/ingrediente/{id}", method = RequestMethod.DELETE)
	//		public String deleteIn(@PathVariable Long id) {
	//			if (ingredienteRepository.existsById(id)) {
	//				ingredienteRepository.deleteById(id);
	//				return "eliminado";
	//			}
	//			return "No se encontro";
	//		}
	//		
	//		@RequestMapping(value = "/ingrediente", method = RequestMethod.PUT)
	//		public String updateIn(@Valid @RequestBody Ingrediente p) {
	//			if (ingredienteRepository.existsById(p.getId())) {
	//				p.setFechaIngreso(ingredienteRepository.findById(p.getId()).get().getFechaIngreso());
	//			}
	//			return JsonManager.toJson(ingredienteRepository.save(p));
	//		}

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public String createCl(@Valid @RequestBody Cliente p) {
		log.info("Cliente Agregado");
		return JsonManager.toJson(clientRepository.save(p));
	}
}