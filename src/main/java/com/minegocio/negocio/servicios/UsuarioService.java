package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Usuario;
import com.minegocio.negocio.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> registrar(String username, String password) {
        if (userRepository.count() > 0) {
            throw new IllegalStateException("Ya existe un usuario registrado.");
        }

        Usuario usuario = new Usuario(username, passwordEncoder.encode(password));
        userRepository.save(usuario);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado con éxito.");
        return response;
    }

    public boolean hayUsuarioRegistrado() {
        return userRepository.count() > 0;
    }

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void save(Usuario usuario) {
        userRepository.save(usuario);
    }

    private Usuario getOnlyUser() {
        return userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No user found"));
    }

    public void updateUserPassword(String newPassword) {
        Usuario user = getOnlyUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateUsername(String newUsername) {
        if (userRepository.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username already taken");
        }
        Usuario user = getOnlyUser();
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public void updateUserPCredentias(String username, String newPassword) {
        Usuario user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("username has no coincidence.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}