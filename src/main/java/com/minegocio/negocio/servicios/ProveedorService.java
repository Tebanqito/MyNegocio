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
}

