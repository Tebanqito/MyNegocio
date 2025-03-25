package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Proveedor;
import com.minegocio.negocio.repositorios.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor agregarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizarProveedor(Long id, Proveedor proveedor) {
        Proveedor existente = proveedorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Proveedor no encontrado con id: " + id));
        existente.setNombre(proveedor.getNombre());
        existente.setContacto(proveedor.getContacto());
        existente.setTelefono(proveedor.getTelefono());
        return proveedorRepository.save(existente);
    }

    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new IllegalArgumentException("Proveedor no encontrado con id: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        return proveedorRepository.findByNombreIgnoreCase(nombre);
    }

    public List<Proveedor> buscarPorContacto(String contacto) {
        return proveedorRepository.findByContactoIgnoreCase(contacto);
    }

    public List<Proveedor> buscarPorTelefono(String telefono) {
        return proveedorRepository.findByTelefono(telefono);
    }

    public List<Proveedor> buscarPorTelefonoParcial(String telefono) {
        return proveedorRepository.findByTelefonoContaining(telefono);
    }

    public List<Proveedor> buscarPorPrefijoNombre(String prefijo) {
        return proveedorRepository.findByNombreStartingWithIgnoreCase(prefijo);
    }

    public List<Proveedor> buscarPorSufijoNombre(String sufijo) {
        return proveedorRepository.findByNombreEndingWithIgnoreCase(sufijo);
    }

    public List<Proveedor> buscarProveedoresConContacto() {
        return proveedorRepository.findByContactoIsNotNull();
    }

    public List<Proveedor> buscarProveedoresSinTelefono() {
        return proveedorRepository.findByTelefonoIsNull();
    }
}

