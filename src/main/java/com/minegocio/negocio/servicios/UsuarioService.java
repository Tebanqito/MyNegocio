package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Usuario;
import com.minegocio.negocio.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> registrar(String username, String password) {
        if (usuarioRepository.count() > 0) {
            throw new IllegalStateException("Ya existe un usuario registrado.");
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password));
        usuarioRepository.save(usuario);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado con éxito.");
        return response;
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean hayUsuarioRegistrado() {
        return usuarioRepository.count() > 0;
    }
}