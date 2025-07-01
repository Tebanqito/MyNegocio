package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Usuario;
import com.minegocio.negocio.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registrarUsuario(String username, String password) {
        if (usuarioRepository.existsByUsername(username)) {
            return false;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
        return true;
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean hayUsuarioRegistrado() {
        return usuarioRepository.count() > 0;
    }
}