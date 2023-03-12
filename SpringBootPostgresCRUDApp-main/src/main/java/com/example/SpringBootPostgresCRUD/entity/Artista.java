package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;

@DiscriminatorValue("Artista")
@Entity
@Table(name = "artistas")
public class Artista extends User {

    private String nombre_artistico;
    private String categoria_artistica;

    public Artista() {
    }

    public Artista(Long id, String password, String firstName, String lastName, String email, String dob, String gender,
            String nombre_artistico, String categoria_artistica) {
        super(id, password, firstName, lastName, email, dob, gender);
        this.nombre_artistico = nombre_artistico;
        this.categoria_artistica = categoria_artistica;
    }

    public String getNombre_artistico() {
        return nombre_artistico;
    }

    public void setNombre_artistico(String nombre_artistico) {
        this.nombre_artistico = nombre_artistico;
    }

    public String getCategoria_artistica() {
        return categoria_artistica;
    }

    public void setCategoria_artistica(String categoria_artistica) {
        this.categoria_artistica = categoria_artistica;
    }
}