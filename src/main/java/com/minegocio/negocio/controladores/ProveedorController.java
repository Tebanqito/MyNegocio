package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Proveedor;
import com.minegocio.negocio.servicios.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> listarProveedores() {
        return proveedorService.listarProveedores();
    }

    @GetMapping("/nombre/{nombre}")
    public List<Proveedor> buscarPorNombre(@PathVariable String nombre) {
        return proveedorService.buscarPorNombre(nombre);
    }

    @GetMapping("/contacto/{contacto}")
    public List<Proveedor> buscarPorContacto(@PathVariable String contacto) {
        return proveedorService.buscarPorContacto(contacto);
    }

    @GetMapping("/telefono/{telefono}")
    public List<Proveedor> buscarPorTelefono(@PathVariable String telefono) {
        return proveedorService.buscarPorTelefono(telefono);
    }

    @GetMapping("/telefono/contiene/{telefono}")
    public List<Proveedor> buscarPorTelefonoParcial(@PathVariable String telefono) {
        return proveedorService.buscarPorTelefonoParcial(telefono);
    }

    @GetMapping("/nombre/prefijo/{prefijo}")
    public List<Proveedor> buscarPorPrefijoNombre(@PathVariable String prefijo) {
        return proveedorService.buscarPorPrefijoNombre(prefijo);
    }

    @GetMapping("/nombre/sufijo/{sufijo}")
    public List<Proveedor> buscarPorSufijoNombre(@PathVariable String sufijo) {
        return proveedorService.buscarPorSufijoNombre(sufijo);
    }

    @GetMapping("/con-contacto")
    public List<Proveedor> buscarProveedoresConContacto() {
        return proveedorService.buscarProveedoresConContacto();
    }

    @GetMapping("/sin-telefono")
    public List<Proveedor> buscarProveedoresSinTelefono() {
        return proveedorService.buscarProveedoresSinTelefono();
    }

    @PostMapping
    public Proveedor agregarProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.agregarProveedor(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizarProveedor(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public void eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
    }
}

