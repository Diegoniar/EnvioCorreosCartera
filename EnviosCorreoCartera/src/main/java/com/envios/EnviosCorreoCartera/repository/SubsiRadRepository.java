package com.envios.EnviosCorreoCartera.repository;

import com.envios.EnviosCorreoCartera.model.SubsiRad;
import com.envios.EnviosCorreoCartera.model.SubsiRadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubsiRadRepository extends JpaRepository<SubsiRad, SubsiRadId> {
    List<SubsiRad> findByFechaAndEnviado(LocalDate fecha, String enviado);
}
