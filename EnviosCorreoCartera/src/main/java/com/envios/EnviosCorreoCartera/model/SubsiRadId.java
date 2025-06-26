package com.envios.EnviosCorreoCartera.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class SubsiRadId implements Serializable {
    private String nit;
    private LocalDate fecha;

    public SubsiRadId() {
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SubsiRadId that = (SubsiRadId) o;
        return Objects.equals(nit, that.nit) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nit, fecha);
    }
}
