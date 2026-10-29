
package com.veterinaria.veterinaria.service;

import com.veterinaria.veterinaria.domain.TipoServicio;
import com.veterinaria.veterinaria.repository.TipoServicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoServicioService {

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioService(
            TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoServicio> getTiposServicio() {
        return tipoServicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TipoServicio> getTipoServicio(Integer id) {
        return tipoServicioRepository.findById(id);
    }

    @Transactional
    public void save(TipoServicio tipoServicio) {
        tipoServicioRepository.save(tipoServicio);
    }

    @Transactional
    public void delete(Integer id) {
        if (!tipoServicioRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "El tipo de servicio no existe.");
        }

        try {
            tipoServicioRepository.deleteById(id);
            tipoServicioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar el tipo porque tiene servicios asociados.",
                    e);
        }
    }
}
