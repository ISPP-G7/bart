package com.appbart.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "arrendadores")
public class Arrendador extends Usuario {
    private String nombreEmpresa;
    // otros campos específicos de Arrendador

    public Arrendador(String nombreUsuario, String contrasena, String nombreEmpresa) {
        super(nombreUsuario, contrasena);
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    // otros getters y setters específicos de Arrendador
}
 
    

