package com.envios.EnviosCorreoCartera.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subsi02")
public class Empresa {
    @Id
    private String nit; // La llave primaria de esta tabla

    private String email;

    // --- Getters y Setters ---
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
