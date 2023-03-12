package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("Artista")
@Entity
@Table(name = "artistas")
@Getter
@Setter
public class Artista extends User {

    private String nombre_artistico;
    private String categoria_artistica;
    private String urlImagen;

    public Artista() {
    }

    public Artista(Long id, String password, String firstName, String lastName, String email, String dob, String gender,
            String nombre_artistico, String categoria_artistica, String urlImagen) {
        super(id, password, firstName, lastName, email, dob, gender);
        this.nombre_artistico = nombre_artistico;
        this.categoria_artistica = categoria_artistica;
        this.urlImagen=urlImagen;
    }

  
}