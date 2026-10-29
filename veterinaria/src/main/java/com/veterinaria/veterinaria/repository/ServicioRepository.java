
package com.veterinaria.veterinaria.repository;

import com.veterinaria.veterinaria.domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository
        extends JpaRepository<Servicio, Integer> {

}
