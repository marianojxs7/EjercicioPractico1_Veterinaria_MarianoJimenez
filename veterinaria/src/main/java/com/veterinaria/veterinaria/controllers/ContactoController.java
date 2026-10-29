
package com.veterinaria.veterinaria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactoController {

    @PostMapping("/contacto")
    public String enviarContacto(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String mascota,
            @RequestParam String mensaje,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "mensajeContacto",
                "¡Gracias, " + nombre
                + "! Recibimos tu consulta sobre " + mascota
                + ". Te contactaremos al correo " + correo + "."
        );

        return "redirect:/#contacto";
    }
}
