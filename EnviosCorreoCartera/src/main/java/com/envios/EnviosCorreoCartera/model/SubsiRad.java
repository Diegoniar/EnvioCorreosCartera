package com.envios.EnviosCorreoCartera.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "subsi60_ugpp_1702_rad")
@IdClass(SubsiRadId.class)
public class SubsiRad {
    private String prefijo;
    private String nrorad;
    private String nomarchivo;
    private String nomarchivo2;

    @Id
    private String nit;

    private String accion_cobro;

    @Id
    private LocalDate fecha;

    private String enviado;

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getNrorad() {
        return nrorad;
    }

    public void setNrorad(String nrorad) {
        this.nrorad = nrorad;
    }

    public String getNomarchivo() {
        return nomarchivo;
    }

    public void setNomarchivo(String nomarchivo) {
        this.nomarchivo = nomarchivo;
    }

    public String getNomarchivo2() {
        return nomarchivo2;
    }

    public void setNomarchivo2(String nomarchivo2) {
        this.nomarchivo2 = nomarchivo2;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAccion_cobro() {
        return accion_cobro;
    }

    public void setAccion_cobro(String accion_cobro) {
        this.accion_cobro = accion_cobro;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }
}
