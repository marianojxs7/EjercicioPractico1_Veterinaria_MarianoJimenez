
package com.veterinaria.veterinaria.controllers;

import com.veterinaria.veterinaria.domain.TipoServicio;
import com.veterinaria.veterinaria.service.TipoServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipo-servicio")
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;

    public TipoServicioController(
            TipoServicioService tipoServicioService) {
        this.tipoServicioService = tipoServicioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var tipos = tipoServicioService.getTiposServicio();

        model.addAttribute("tiposServicio", tipos);
        model.addAttribute("totalTipos", tipos.size());

        return "tipoServicio/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute TipoServicio tipoServicio,
            RedirectAttributes redirectAttributes) {

        boolean esEdicion = tipoServicio.getId() != null;

        try {
            if (esEdicion
                    && tipoServicioService
                            .getTipoServicio(tipoServicio.getId())
                            .isEmpty()) {

                redirectAttributes.addFlashAttribute(
                        "mensajeError",
                        "El tipo de servicio que intentas editar no existe.");

                return "redirect:/tipo-servicio/listado";
            }

            tipoServicioService.save(tipoServicio);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    esEdicion
                    ? "El tipo de servicio se actualizó correctamente."
                    : "El tipo de servicio se registró correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No fue posible guardar los cambios. Verifica los datos.");
        }

        return "redirect:/tipo-servicio/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            tipoServicioService.delete(id);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "El tipo de servicio se eliminó correctamente.");

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No se puede eliminar: el tipo tiene servicios asociados.");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "El tipo de servicio no existe.");
        }

        return "redirect:/tipo-servicio/listado";
    }
}
