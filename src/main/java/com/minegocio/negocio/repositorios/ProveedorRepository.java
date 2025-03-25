package com.minegocio.negocio.repositorios;

import com.minegocio.negocio.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    List<Proveedor> findByNombreIgnoreCase(String nombre);
    List<Proveedor> findByContactoIgnoreCase(String contacto);
    List<Proveedor> findByTelefono(String telefono);
    List<Proveedor> findByTelefonoContaining(String telefono);
    List<Proveedor> findByNombreStartingWithIgnoreCase(String prefijo);
    List<Proveedor> findByNombreEndingWithIgnoreCase(String sufijo);
    List<Proveedor> findByContactoIsNotNull();
    List<Proveedor> findByTelefonoIsNull();
}
