package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("Artista")
@Entity
@Table(name = "artistas")
@Getter
@Setter
public class Artista extends User {
    @NotBlank
    private String nombre_artistico;
    @NotBlank
    private String categoria_artistica;

    private String links;
    @URL
    private String urlImagen;

    public Artista() {
    }

    public Artista(Long id, String password, String firstName, String lastName, String email, String dob, String gender,
            String nombre_artistico, String categoria_artistica, String urlImagen, String links) {
        super(id, password, firstName, lastName, email, dob, gender);
        this.nombre_artistico = nombre_artistico;
        this.categoria_artistica = categoria_artistica;
        this.urlImagen = urlImagen;
        this.links = links;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

}