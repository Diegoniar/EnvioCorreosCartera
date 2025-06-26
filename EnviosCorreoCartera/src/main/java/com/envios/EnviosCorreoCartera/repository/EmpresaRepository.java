package com.envios.EnviosCorreoCartera.repository;

import com.envios.EnviosCorreoCartera.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
 public interface EmpresaRepository extends JpaRepository<Empresa, String> {
  @Query("SELECT e FROM Empresa e WHERE trim(e.nit) = :nit")
  Optional<Empresa> findByNitTrimmed(@Param("nit") String nit);
 }
