
package com.veterinaria.veterinaria.repository;

import com.veterinaria.veterinaria.domain.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServicioRepository
        extends JpaRepository<TipoServicio, Integer> {

}
