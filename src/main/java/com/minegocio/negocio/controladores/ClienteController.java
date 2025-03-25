package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Cliente;
import com.minegocio.negocio.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public Cliente agregarCliente(@RequestBody Cliente cliente) {
        return clienteService.agregarCliente(cliente);
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }

    @GetMapping("/nombre")
    public List<Cliente> obtenerPorNombre(@RequestParam String nombre) {
        return clienteService.obtenerPorNombre(nombre);
    }

    @GetMapping("/email")
    public Optional<Cliente> obtenerPorEmail(@RequestParam String email) {
        return clienteService.obtenerPorEmail(email);
    }

    @GetMapping("/telefono")
    public List<Cliente> obtenerPorTelefono(@RequestParam String telefono) {
        return clienteService.obtenerPorTelefono(telefono);
    }

    @GetMapping("/frecuentes")
    public List<Cliente> obtenerClientesFrecuentes(@RequestParam int minCompras) {
        return clienteService.obtenerClientesFrecuentes(minCompras);
    }
}