package com.example.SpringBootPostgresCRUD.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.Getter;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Getter
@Setter
@Entity

@Table(name = "anuncios")
public class Anuncio {

    @Id
    private Long id;

    @NotBlank
    @Min(10)
    @Max(50)
    private String titulo;

    @NotBlank
    @Min(5)
    @Max(60)
    private String ubicacion;

    @NotNull
    @Positive
    private Float precio;

    @NotBlank
    @Min(15)
    @Max(300)
    private String especificaciones;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estilo estilo;

    // falta subir imagenes y poner bien lo del precio fijo/negociable

    public Anuncio(Long id, String titulo, String ubicacion,
            Float precio, String especificaciones, Estilo estilo) {
        this.id = id;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.especificaciones = especificaciones;
        this.estilo = estilo;
    }

    public Anuncio() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    // otros getters y setters específicos de Artista
}
