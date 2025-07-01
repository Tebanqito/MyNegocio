package com.minegocio.negocio.controladores;

import com.minegocio.negocio.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public Map<String, String> registrar(@RequestParam String username, @RequestParam String password) {
        System.out.println("Se recibió solicitud para registrar usuario: " + username);

        Map<String, String> response = new HashMap<>();
        if (usuarioService.hayUsuarioRegistrado()) {
            response.put("mensaje", "Ya existe un usuario registrado. No se pueden crear más cuentas.");
        } else if (usuarioService.registrarUsuario(username, password)) {
            response.put("mensaje", "Usuario registrado con éxito.");
        } else {
            response.put("mensaje", "Error al registrar usuario.");
        }
        return response;
    }
}