
package com.veterinaria.veterinaria.repository;

import com.veterinaria.veterinaria.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

}

