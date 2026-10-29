
package com.veterinaria.veterinaria.service;

import com.veterinaria.veterinaria.domain.Servicio;
import com.veterinaria.veterinaria.repository.ServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public List<Servicio> getServicios() {
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Servicio> getServicio(Integer id) {
        return servicioRepository.findById(id);
    }

    @Transactional
    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Transactional
    public void delete(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "El servicio no existe.");
        }

        try {
            servicioRepository.deleteById(id);
            servicioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el servicio porque tiene citas asociadas.",
                    e);
        }
    }
}
