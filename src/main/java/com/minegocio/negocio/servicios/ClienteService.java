package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Cliente;
import com.minegocio.negocio.repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Cliente existente = clienteRepository.findById(id).orElseThrow();
        existente.setNombre(cliente.getNombre());
        existente.setEmail(cliente.getEmail());
        existente.setTelefono(cliente.getTelefono());
        return clienteRepository.save(existente);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> obtenerPorNombre(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }

    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> obtenerPorTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }


    public List<Cliente> obtenerClientesFrecuentes(int minCompras) {
        return clienteRepository.findByComprasMinimas(minCompras);
    }
}

