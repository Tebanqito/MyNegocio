package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Cliente;
import com.minegocio.negocio.repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
}

