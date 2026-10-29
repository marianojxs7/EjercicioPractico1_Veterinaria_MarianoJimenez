
package com.veterinaria.veterinaria.service;

import com.veterinaria.veterinaria.domain.Cita;
import com.veterinaria.veterinaria.repository.CitaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitas() {
        return citaRepository.findAll();
    }
}
