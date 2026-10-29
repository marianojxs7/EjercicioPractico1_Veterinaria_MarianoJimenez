
package com.veterinaria.veterinaria.controllers;

import com.veterinaria.veterinaria.domain.Servicio;
import com.veterinaria.veterinaria.domain.TipoServicio;
import com.veterinaria.veterinaria.service.ServicioService;
import com.veterinaria.veterinaria.service.TipoServicioService;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    private final ServicioService servicioService;
    private final TipoServicioService tipoServicioService;

    public ServicioController(
            ServicioService servicioService,
            TipoServicioService tipoServicioService) {
        this.servicioService = servicioService;
        this.tipoServicioService = tipoServicioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var servicios = servicioService.getServicios();
        var tipos = tipoServicioService.getTiposServicio();

        model.addAttribute("servicios", servicios);
        model.addAttribute("tiposServicio", tipos);
        model.addAttribute("totalServicios", servicios.size());

        return "servicio/listado";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(required = false) Integer id,
            @RequestParam String nombre,
            @RequestParam BigDecimal precio,
            @RequestParam Integer duracionMinutos,
            @RequestParam Integer tipoServicioId,
            RedirectAttributes redirectAttributes) {

        try {
            Optional<TipoServicio> resultado =
                    tipoServicioService.getTipoServicio(tipoServicioId);

            if (resultado.isEmpty()) {
                redirectAttributes.addFlashAttribute(
                        "mensajeError",
                        "El tipo de servicio seleccionado no existe.");
                return "redirect:/servicio/listado";
            }

            Servicio servicio;

            if (id != null) {
                Optional<Servicio> existente = servicioService.getServicio(id);

                if (existente.isEmpty()) {
                    redirectAttributes.addFlashAttribute(
                            "mensajeError",
                            "El servicio que intentas editar no existe.");
                    return "redirect:/servicio/listado";
                }

                servicio = existente.get();
            } else {
                servicio = new Servicio();
            }

            servicio.setNombre(nombre);
            servicio.setPrecio(precio);
            servicio.setDuracionMinutos(duracionMinutos);
            servicio.setTipoServicio(resultado.get());

            servicioService.save(servicio);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    id != null
                    ? "El servicio se actualizó correctamente."
                    : "El servicio se registró correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No fue posible guardar el servicio. Verifica los datos.");
        }

        return "redirect:/servicio/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            servicioService.delete(id);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "El servicio se eliminó correctamente.");

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No se puede eliminar el servicio porque tiene citas asociadas.");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "El servicio no existe.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    "No fue posible eliminar el servicio.");
        }

        return "redirect:/servicio/listado";
    }
}
