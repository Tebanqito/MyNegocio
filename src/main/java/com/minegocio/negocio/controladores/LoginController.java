package com.minegocio.negocio.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        Path recoveryPath = Paths.get(System.getProperty("user.dir"), "recovery.txt");
        System.out.println("Ruta completa: " + recoveryPath.toAbsolutePath());

        if (Files.exists(recoveryPath)) {
            System.out.println("recovery.txt detectado, redirigiendo...");
            return "redirect:/recovery";
        }

        System.out.println("recovery.txt NO existe, mostrando login.");
        return "login";
    }
}