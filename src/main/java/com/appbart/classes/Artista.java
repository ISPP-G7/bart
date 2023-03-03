package com.appbart.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "artistas")
public class Artista extends Usuario {
    private String nombreArtistico;
    // otros campos específicos de Artista

    public Artista(String nombreUsuario, String contrasena, String nombreArtistico) {
        super(nombreUsuario, contrasena);
        this.nombreArtistico = nombreArtistico;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    // otros getters y setters específicos de Artista
}
