package com.minegocio.negocio.controladores;

import com.minegocio.negocio.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class RecoveryController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/recovery")
    public String showRecoveryForm() {
        return "recovery";
    }

    @PostMapping("/recovery")
    public String updateUserPassword(@RequestParam String username,
                                       @RequestParam String newPassword) throws IOException {
        try {
            usuarioService.updateUserPCredentias(username, newPassword);
        } catch (IllegalArgumentException e) {
            return "redirect:/recovery?error";
        }

        Files.deleteIfExists(Paths.get(System.getProperty("user.dir"), "recovery.txt"));
        return "redirect:/login?recuperado";
    }
}