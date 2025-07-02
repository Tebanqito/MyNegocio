package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Usuario;
import com.minegocio.negocio.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class RecoveryController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/recovery")
    public String mostrarFormularioRecuperacion() {
        return "recovery";
    }

    @PostMapping("/recovery")
    public String procesarRecuperacion(@RequestParam("nuevaPassword") String nuevaPassword) throws IOException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (!usuarios.isEmpty()) {
            Usuario usuario = usuarios.get(0); // Asumimos que hay solo un usuario
            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
            usuarioRepository.save(usuario);
        }

        // Eliminar el archivo recovery.txt
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir"), "recovery.txt"));
        return "redirect:/login?recuperado";
    }
}